package de.zabuza.pathweaver.examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
		System.out.println("Loading file...");
		File osmFile = new File("res/examples/saarland.osm");

		System.out.println("Creating road network...");
		long startTimestamp = System.currentTimeMillis();
		RoadNetwork network = RoadNetwork.createFromOsmFile(osmFile);
		long endTimestamp = System.currentTimeMillis();
		float durationSeconds = (endTimestamp - startTimestamp + 0.0f) / 1000;
		System.out.println("Nodes: " + network.getSize() + ", Edges: " + network.getAmountOfEdges());
		System.out.println("Time needed: " + durationSeconds + " seconds");
	}
}
