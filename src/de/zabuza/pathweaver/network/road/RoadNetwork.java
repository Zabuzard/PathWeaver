package de.zabuza.pathweaver.network.road;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * A path network which consists of roads and road crossings. The cost of a road
 * is measured in seconds.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoadNetwork extends PathNetwork {

	/**
	 * The exception message shown when trying to construct a road with less
	 * than 2 road nodes.
	 */
	private static final String ILLEGAL_AMOUNT_OF_ROAD_NODES = "A road must contain at least 2 road nodes to be constructed.";
	/**
	 * The regex needle used for matching nodes in lines of the OSM-format.
	 */
	private final static String OSM_NEEDLE_NODE = "\\A\\s*<node\\s*id\\s*=\\s*\"(-?\\d+)\"\\s*lat\\s*=\\s*\"(-?\\d+\\.?\\d*)\"\\s*lon\\s*=\\s*\"(-?\\d+\\.?\\d*)\".*\\/?>";
	/**
	 * The number of the capturing group for a node id. Used in the regex needle
	 * for matching nodes in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_NODE_GROUP_ID = 1;
	/**
	 * The number of the capturing group for a node latitude. Used in the regex
	 * needle for matching nodes in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_NODE_GROUP_LAT = 2;
	/**
	 * The number of the capturing group for a node longitude. Used in the regex
	 * needle for matching nodes in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_NODE_GROUP_LON = 3;
	/**
	 * The regex needle used for matching road ends in lines of the OSM-format.
	 */
	private final static String OSM_NEEDLE_ROAD_END = "\\A\\s*<\\s*\\/way>";
	/**
	 * The regex needle used for matching road entries in lines of the
	 * OSM-format.
	 */
	private final static String OSM_NEEDLE_ROAD_ENTRY = "\\A\\s*<nd\\s*ref\\s*=\\s*\"(-?\\d+)\".*\\/?>";
	/**
	 * The number of the capturing group for a road entry reference. Used in the
	 * regex needle for matching road entries in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_ROAD_ENTRY_GROUP_REF = 1;
	/**
	 * The regex needle used for matching road one-way property in lines of the
	 * OSM-format.
	 */
	private final static String OSM_NEEDLE_ROAD_ONEWAY = "\\A\\s*<tag\\s*k\\s*=\\s*\"oneway\"\\s*v\\s*=\\s*\"yes\".*\\/?>";
	/**
	 * The regex needle used for matching road starts in lines of the
	 * OSM-format.
	 */
	private final static String OSM_NEEDLE_ROAD_START = "\\A\\s*<way\\s*id\\s*=\\s*\"(-?\\d+)\".*>";
	/**
	 * The number of the capturing group for a road start id. Used in the regex
	 * needle for matching road starts in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_ROAD_START_GROUP_ID = 1;
	/**
	 * The regex needle used for matching road types in lines of the OSM-format.
	 */
	private final static String OSM_NEEDLE_ROAD_TYPE = "\\A\\s*<tag\\s*k\\s*=\\s*\"highway\"\\s*v\\s*=\\s*\"(.+)\".*\\/?>";
	/**
	 * The number of the capturing group for a road type. Used in the regex
	 * needle for matching road types in lines of the OSM-format.
	 */
	private final static int OSM_NEEDLE_ROAD_TYPE_GROUP_TYPE = 1;
	/**
	 * Exception message which is shown when the unsupported operation
	 * {@link #addEdge(Node, Node, int)} is called.
	 */
	private final static String UNSUPPORTED_ADD_EDGE = "Road networks only accept RoadNode as nodes. Use addRoad(RoadNode, RoadNode) instead.";
	/**
	 * Exception message which is shown when the unsupported operation
	 * {@link #addNode(Node)} is called.
	 */
	private final static String UNSUPPORTED_ADD_NODE = "Road networks only accept RoadNode as nodes. Use addRoadNode(RoadNode) instead.";

	/**
	 * Creates a road network from a given file in the OSM-format.
	 * 
	 * @param osmFile
	 *            File which contains the road network in the OSM-format
	 * @return The road network created from the file
	 * @throws FileNotFoundException
	 *             If the given file was not found
	 * @throws IOException
	 *             If an I/O-Exception occurred
	 */
	public static RoadNetwork createFromOsmFile(final File osmFile) throws FileNotFoundException, IOException {
		return createFromOsmReader(new FileReader(osmFile));
	}

	/**
	 * Creates a road network from a given reader stream whose content is in the
	 * OSM-format.
	 * 
	 * @param osmReader
	 *            The stream reader whose content is in the OSM-format
	 * @return The road network created from the reader
	 * @throws IOException
	 *             If an I/O-Exception occurred
	 */
	public static RoadNetwork createFromOsmReader(final Reader osmReader) throws IOException {
		RoadNetwork network = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(osmReader);
			network = new RoadNetwork();
			Pattern nodePattern = Pattern.compile(OSM_NEEDLE_NODE);
			Pattern roadStartPattern = Pattern.compile(OSM_NEEDLE_ROAD_START);
			Pattern roadEntryPattern = Pattern.compile(OSM_NEEDLE_ROAD_ENTRY);
			Pattern roadTypePattern = Pattern.compile(OSM_NEEDLE_ROAD_TYPE);
			Pattern roadOnewayPattern = Pattern.compile(OSM_NEEDLE_ROAD_ONEWAY);
			Pattern roadEndPattern = Pattern.compile(OSM_NEEDLE_ROAD_END);
			Matcher matcher;

			boolean insideRoadDefinition = false;
			Road currentRoad = null;
			boolean rejectTheCurrentRoadConstructionData = false;
			boolean matchedLine = false;
			while (br.ready()) {
				String line = br.readLine();
				if (line == null) {
					break;
				}

				matchedLine = false;

				// Match road related data if inside
				if (!matchedLine && insideRoadDefinition) {
					// Match an entry of the road
					if (!matchedLine) {
						matcher = roadEntryPattern.matcher(line);
						if (matcher.find()) {
							matchedLine = true;
							int ref = Integer.parseInt(matcher.group(OSM_NEEDLE_ROAD_ENTRY_GROUP_REF));
							currentRoad.addRoadNode(ref);
						}
					}
					// Match the type of the road
					if (!matchedLine) {
						matcher = roadTypePattern.matcher(line);
						if (matcher.find()) {
							matchedLine = true;
							String typeText = matcher.group(OSM_NEEDLE_ROAD_TYPE_GROUP_TYPE);
							ERoadType type;
							try {
								type = RoadUtil.getRoadTypeFromOsm(typeText);
								currentRoad.setRoadType(type);
							} catch (IllegalArgumentException e) {
								rejectTheCurrentRoadConstructionData = true;
							}
						}
					}
					// Match the one-way property of the road
					if (!matchedLine) {
						matcher = roadOnewayPattern.matcher(line);
						if (matcher.find()) {
							matchedLine = true;
							currentRoad.setIsOneway(true);
						}
					}
					// Match the end of the road
					if (!matchedLine) {
						matcher = roadEndPattern.matcher(line);
						if (matcher.find()) {
							matchedLine = true;
							insideRoadDefinition = false;
							if (!rejectTheCurrentRoadConstructionData) {
								// Construct the road and add it to the network
								network.addRoad(currentRoad);
							}
						}
					}
				}
				// Match a node line
				if (!matchedLine) {
					matcher = nodePattern.matcher(line);
					if (matcher.find()) {
						matchedLine = true;
						int id = Integer.parseInt(matcher.group(OSM_NEEDLE_NODE_GROUP_ID));
						float latitude = Float.parseFloat(matcher.group(OSM_NEEDLE_NODE_GROUP_LAT));
						float longitude = Float.parseFloat(matcher.group(OSM_NEEDLE_NODE_GROUP_LON));

						RoadNode node = new RoadNode(id, latitude, longitude);
						network.addRoadNode(node);
					}
				}
				// Match a road start line
				if (!matchedLine) {
					matchedLine = true;
					matcher = roadStartPattern.matcher(line);
					if (matcher.find()) {
						insideRoadDefinition = true;
						rejectTheCurrentRoadConstructionData = false;
						int id = Integer.parseInt(matcher.group(OSM_NEEDLE_ROAD_START_GROUP_ID));
						currentRoad = new Road(id);
					}
				}
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return network;
	}

	/**
	 * Creates an empty road network.
	 */
	public RoadNetwork() {
		super();
	}

	/**
	 * This method is not supported by {@link RoadNetwork}. Use
	 * {@link #addRoad(RoadNode, RoadNode)} instead.
	 */
	@Override
	public void addEdge(final Node source, final Node destination, final float cost)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException(UNSUPPORTED_ADD_EDGE);
	}

	/**
	 * This method is not supported by {@link RoadNetwork}. Use
	 * {@link #addRoadNode(RoadNode)} instead.
	 */
	@Override
	public boolean addNode(final Node node) throws UnsupportedOperationException {
		throw new UnsupportedOperationException(UNSUPPORTED_ADD_NODE);
	}

	/**
	 * Adds the given road to the network.
	 * 
	 * @param road
	 *            The road to add
	 */
	public void addRoad(final Road road) {
		// TODO Do something with the road id
		ERoadType type = road.getRoadType();
		boolean isOneWay = road.isOneway();

		int amountOfRoadNodes = road.getRoadNodesAmount();
		if (amountOfRoadNodes < 2) {
			System.err.println("Warning: " + ILLEGAL_AMOUNT_OF_ROAD_NODES);
			return;
		}

		// Forward direction
		Iterator<Integer> nodesIter = road.getRoadNodes();
		RoadNode lastNode = (RoadNode) getNodeById(nodesIter.next());
		while (nodesIter.hasNext()) {
			Integer nodeId = nodesIter.next();
			RoadNode nextNode = (RoadNode) getNodeById(nodeId);

			// Combine lastNode with nextNode
			addRoad(lastNode, nextNode, type);

			lastNode = nextNode;
		}

		if (!isOneWay) {
			// Backward direction
			Iterator<Integer> nodesIterReversed = road.getRoadNodesReversed();
			lastNode = (RoadNode) getNodeById(nodesIterReversed.next());
			while (nodesIterReversed.hasNext()) {
				Integer nodeId = nodesIterReversed.next();
				RoadNode nextNode = (RoadNode) getNodeById(nodeId);

				// Combine lastNode with nextNode
				addRoad(lastNode, nextNode, type);

				lastNode = nextNode;
			}
		}
	}

	/**
	 * Adds a road between the given road nodes. The cost of this road is
	 * measured in seconds and computed using the distances of the road nodes.
	 * 
	 * @param source
	 *            The source node of the road
	 * @param destination
	 *            The destination node of the road
	 * @param type
	 *            The type of the road to add
	 */
	public void addRoad(final RoadNode source, final RoadNode destination, final ERoadType type) {
		float distance = RoadUtil.distanceEquiRect(source, destination);
		if (distance == 0.0f) {
			System.err.println("Warning: The given road was not added because the distance was zero.");
			return;
		}
		float speed = RoadUtil.getAverageSpeedOfRoadType(type);
		float timeToTravel = RoadUtil.getTravelTime(distance, speed);
		super.addEdge(source, destination, timeToTravel);
	}

	/**
	 * Adds a given road node to the network.
	 * 
	 * @param node
	 *            The node to add
	 * @return <tt>True</tt> if the node was added, i.e. was not contained
	 *         before, <tt>false</tt> otherwise
	 */
	public boolean addRoadNode(final RoadNode node) {
		boolean wasAdded = super.addNode(node);
		return wasAdded;
	}
}
