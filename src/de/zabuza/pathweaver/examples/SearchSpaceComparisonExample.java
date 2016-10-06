package de.zabuza.pathweaver.examples;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import de.zabuza.pathweaver.network.algorithm.metric.StraightLineRoadTimeMetric;
import de.zabuza.pathweaver.network.algorithm.metric.landmark.GreedyFarthestLandmarkProvider;
import de.zabuza.pathweaver.network.algorithm.metric.landmark.LandmarkMetric;
import de.zabuza.pathweaver.network.algorithm.metric.landmark.RandomLandmarkProvider;
import de.zabuza.pathweaver.network.algorithm.shortestpath.AStarShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.IShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag.ArcFlagShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag.OneAxisRectanglePartitioningProvider;
import de.zabuza.pathweaver.network.road.RoadNetwork;
import de.zabuza.pathweaver.network.road.RoadNode;
import de.zabuza.pathweaver.network.road.RoadUtil;

/**
 * Class which demonstrates the usage of reading an OSM-File into a road network
 * and then applying several shortest path algorithms and comparing their search
 * space.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class SearchSpaceComparisonExample {
	/**
	 * Demonstrates the usage of reading an OSM-File into a road network and
	 * then applying several shortest path algorithms and comparing their search
	 * space.
	 * 
	 * @param args
	 *            Not supported
	 * @throws IOException
	 *             If an I/O-Exception occurred
	 * @throws FileNotFoundException
	 *             If the given file was not found
	 */
	public static void main(final String[] args) throws FileNotFoundException, IOException {
		// Loading file
		System.out.println("Loading file...");
		File osmFile = new File("res/examples/saarland.osm");

		// Creating road network
		System.out.println("Creating road network...");
		RoadNetwork network = RoadNetwork.createFromOsmFile(osmFile);

		// Reducing to largest SCC
		System.out.println("Reducing to largest SCC...");
		network.reduceToLargestScc();

		// Preparing algorithms
		System.out.println("Preparing ordinary Dijkstra...");
		DijkstraShortestPathComputation dijkstra = new DijkstraShortestPathComputation(network);
		System.out.println("Preparing A-Star (Straight-Line)...");
		AStarShortestPathComputation aStarStraight = new AStarShortestPathComputation(network,
				new StraightLineRoadTimeMetric());
		System.out.println("Preparing A-Star (Landmark, random)...");
		AStarShortestPathComputation aStarLandmarkRandom = new AStarShortestPathComputation(network,
				new LandmarkMetric(42, network, new RandomLandmarkProvider(network)));
		System.out.println("Preparing A-Star (Landmark, greedy-farthest)...");
		AStarShortestPathComputation aStarLandmarkGreedy = new AStarShortestPathComputation(network,
				new LandmarkMetric(42, network, new GreedyFarthestLandmarkProvider(network)));
		System.out.println("Preparing ArcFlag...");
		// Saarbruecken city
		float latitudeMin = 49.20f;
		float latitudeMax = 49.25f;
		float longitudeMin = 6.95f;
		float longitudeMax = 7.05f;
		ArcFlagShortestPathComputation arcFlag = new ArcFlagShortestPathComputation(network,
				new OneAxisRectanglePartitioningProvider(network, latitudeMin, latitudeMax, longitudeMin,
						longitudeMax));

		// Starting search space queries
		System.out.println("Starting search space queries...");
		RoadNode sourceLortzingStreet = network.getNearestRoadNode(49.402016f, 6.901051f);
		RoadNode destinationFeldmannStreet = network.getNearestRoadNode(49.22364f, 6.9926014f);

		System.out.println("Starting ordinary Dijkstra...");
		computeSearchSpaceAndSaveToFile(dijkstra, sourceLortzingStreet, destinationFeldmannStreet, "dijkstra.tsv");
		System.out.println("Starting A-Star (Straight-Line)...");
		computeSearchSpaceAndSaveToFile(aStarStraight, sourceLortzingStreet, destinationFeldmannStreet,
				"aStarStraight.tsv");
		System.out.println("Starting A-Star (Landmark, random)...");
		computeSearchSpaceAndSaveToFile(aStarLandmarkRandom, sourceLortzingStreet, destinationFeldmannStreet,
				"aStarLandmarkRandom.tsv");
		System.out.println("Starting A-Star (Landmark, greedy-farthest)...");
		computeSearchSpaceAndSaveToFile(aStarLandmarkGreedy, sourceLortzingStreet, destinationFeldmannStreet,
				"aStarLandmarkGreedy.tsv");
		System.out.println("Starting ArcFlag...");
		computeSearchSpaceAndSaveToFile(arcFlag, sourceLortzingStreet, destinationFeldmannStreet, "arcFlag.tsv");
	}

	/**
	 * Computes the search space used by the given algorithm on the given query
	 * and saves the results to a file with the given name.
	 * 
	 * @param computation
	 *            Algorithm to use
	 * @param source
	 *            Source of the query
	 * @param destination
	 *            Destination of the query
	 * @param fileName
	 *            Name of the file to save results in
	 * @throws IOException
	 *             If an I/O-Exception occurred
	 */
	private final static void computeSearchSpaceAndSaveToFile(final IShortestPathComputation computation,
			final RoadNode source, final RoadNode destination, final String fileName) throws IOException {
		@SuppressWarnings("unchecked")
		Set<RoadNode> searchSpace = (Set<RoadNode>) (Set<?>) computation.computeShortestPathSearchSpace(source,
				destination);
		// Save the search space to a file on the desktop
		String tsvData = RoadUtil.getPositionsTsv(searchSpace);
		File desktop = new File(System.getProperty("user.home"), "Desktop");
		File searchSpaceFile = new File(desktop, fileName);
		System.out.println("\tSaving to: " + searchSpaceFile);
		BufferedWriter bw = new BufferedWriter(new FileWriter(searchSpaceFile));
		String header = "Lat\tLng" + System.lineSeparator();
		bw.write(header + tsvData);
		bw.close();
	}
}
