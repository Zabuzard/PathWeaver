package de.zabuza.pathweaver.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Random;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.metric.LandmarkMetric;
import de.zabuza.pathweaver.network.algorithm.shortestpath.AStarShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.IShortestPathComputation;
import de.zabuza.pathweaver.network.road.RoadNetwork;

/**
 * Class which demonstrates the usage of reading an OSM-File into a road
 * network.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoadNetworkExample {
	/**
	 * Demonstrates the usage of reading an OSM-File into a road network.
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
		long startTimestamp = System.currentTimeMillis();
		RoadNetwork network = RoadNetwork.createFromOsmFile(osmFile);
		long endTimestamp = System.currentTimeMillis();
		float durationSeconds = (endTimestamp - startTimestamp + 0.0f) / 1000;
		System.out.println("\tNodes: " + network.getSize() + ", Edges: " + network.getAmountOfEdges());
		System.out.println("\tTime needed: " + durationSeconds + " seconds");

		// Reducing to largest SCC
		System.out.println("Reducing to largest SCC...");
		startTimestamp = System.currentTimeMillis();
		network.reduceToLargestScc();
		endTimestamp = System.currentTimeMillis();
		durationSeconds = (endTimestamp - startTimestamp + 0.0f) / 1000;
		System.out.println("\tNodes: " + network.getSize() + ", Edges: " + network.getAmountOfEdges());
		System.out.println("\tTime needed: " + durationSeconds + " seconds");

		// Reducing to largest SCC
		System.out.println("Reversing...");
		startTimestamp = System.currentTimeMillis();
		network.reverse();
		endTimestamp = System.currentTimeMillis();
		durationSeconds = (endTimestamp - startTimestamp + 0.0f) / 1000;
		System.out.println("\tNodes: " + network.getSize() + ", Edges: " + network.getAmountOfEdges());
		System.out.println("\tTime needed: " + durationSeconds + " seconds");
		System.out.println("Reversing again...");
		startTimestamp = System.currentTimeMillis();
		network.reverse();
		endTimestamp = System.currentTimeMillis();
		durationSeconds = (endTimestamp - startTimestamp + 0.0f) / 1000;
		System.out.println("\tNodes: " + network.getSize() + ", Edges: " + network.getAmountOfEdges());
		System.out.println("\tTime needed: " + durationSeconds + " seconds");

		// Preparing random queries
		System.out.println("Preparing random queries...");
		IShortestPathComputation computation = new AStarShortestPathComputation(network,
				new LandmarkMetric(42, network));
		Object[] nodes = network.getNodes().toArray();
		int amountOfNodes = nodes.length;
		Random rnd = new Random();
		int queryAmount = 100;
		int logEvery = 10;
		long totalRunningTime = 0;
		double totalCost = 0.0;

		// Starting random queries
		System.out.println("Starting random queries...");
		for (int i = 1; i <= queryAmount; i++) {
			int sourceIndex = rnd.nextInt(amountOfNodes);
			int destinationIndex = rnd.nextInt(amountOfNodes);
			Node source = (Node) nodes[sourceIndex];
			Node destination = (Node) nodes[destinationIndex];

			startTimestamp = System.currentTimeMillis();
			Optional<Float> result = computation.computeShortestPathCost(source, destination);
			// Ignore queries where source can not reach destination
			if (!result.isPresent()) {
				i--;
				continue;
			}
			totalCost += result.get();
			endTimestamp = System.currentTimeMillis();
			totalRunningTime += (endTimestamp - startTimestamp);

			if (i % logEvery == 0) {
				System.out.println("\t\tProcessed " + i + " queries.");
			}
		}
		double averageCost = totalCost / queryAmount;
		float averageTimeInSeconds = (totalRunningTime / queryAmount + 0.0f) / 1000;
		System.out.println("\tAverage cost: " + averageCost);
		System.out.println("\tAverage time: " + averageTimeInSeconds);
	}
}
