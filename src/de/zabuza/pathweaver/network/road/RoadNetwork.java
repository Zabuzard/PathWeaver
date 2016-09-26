package de.zabuza.pathweaver.network.road;

import java.io.File;
import java.util.HashMap;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * A path network which consists of roads and road crossings. The cost of a road
 * is measured in seconds.
 * 
 * @author Zabuza
 *
 */
public final class RoadNetwork extends PathNetwork {

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
	 * 
	 * @param osmFile
	 * @return
	 */
	public static RoadNetwork createFromOsmFile(final File osmFile) {
		RoadNetwork network = new RoadNetwork();

		// TODO Implement

		return network;
	}

	/**
	 * Maps the id of added road nodes to the corresponding nodes.
	 */
	private final HashMap<Integer, RoadNode> mIdToRoadNodes;

	/**
	 * Creates an empty road network.
	 */
	public RoadNetwork() {
		super();
		mIdToRoadNodes = new HashMap<>();
	}

	/**
	 * This method is not supported by {@link RoadNetwork}. Use
	 * {@link #addRoad(RoadNode, RoadNode)} instead.
	 */
	@Override
	public void addEdge(final Node source, final Node destination, final int cost)
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
		float speed = RoadUtil.getAverageSpeedOfRoadType(type);
		int timeToTravel = RoadUtil.getTravelTime(distance, speed);
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
		RoadNode previousElement = mIdToRoadNodes.get(node.getId());
		if (previousElement != null) {
			return false;
		} else {
			boolean added = super.addNode(node);
			assert added;
			mIdToRoadNodes.put(node.getId(), node);

			return added;
		}
	}

	/**
	 * Returns the road node with the given id, if it is contained in the
	 * network.
	 * 
	 * @param id
	 *            The id of the node to get
	 * @return The road node with the given id, if it is contained in the
	 *         network or <tt>null</tt> if that is not the case.
	 */
	public RoadNode getRoadNodeById(final int id) {
		return mIdToRoadNodes.get(id);
	}
}
