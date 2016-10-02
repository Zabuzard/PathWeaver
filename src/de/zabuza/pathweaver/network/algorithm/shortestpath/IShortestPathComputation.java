package de.zabuza.pathweaver.network.algorithm.shortestpath;

import java.util.Map;
import java.util.Optional;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.Path;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Interface for algorithm which solve shortest path computation tasks in
 * {@link PathNetwork}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public interface IShortestPathComputation {
	/**
	 * Computes the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and destination.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The shortest path between the given source and destination if
	 *         present, if not present there is no such path.
	 */
	public Optional<Path> computeShortestPath(final Node source, final Node destination);

	/**
	 * Computes the costs of the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and destination.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The cost of the shortest path if present, if not present there is
	 *         no such path.
	 */
	public Optional<Float> computeShortestPathCost(final Node source, final Node destination);

	/**
	 * Computes the costs of the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and every reachable
	 * destination.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @return A mapping of all, from source, reachable destination nodes to the
	 *         costs of the shortest paths from the source to the given
	 *         destinations
	 */
	public Map<Node, Float> computeShortestPathCostsReachable(final Node source);

	/**
	 * Gets the path network this object works on.
	 * 
	 * @return The path network this object works on
	 */
	public IPathNetwork getPathNetwork();
}
