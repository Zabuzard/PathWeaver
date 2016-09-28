package de.zabuza.pathweaver.network.algorithm;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Interface for algorithm which solve shortest path computation tasks in
 * {@link PathNetwork}s.
 * 
 * @author Zabuza
 *
 */
public interface IShortestPathComputation {
	/**
	 * Computes the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and destination. After
	 * that it returns the cost of this path.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The cost of the shortest path
	 */
	public float computeShortestPathCost(final Node source, final Node destination);

	/**
	 * Gets the path network this object works on.
	 * 
	 * @return The path network this object works on
	 */
	public IPathNetwork getPathNetwork();
}
