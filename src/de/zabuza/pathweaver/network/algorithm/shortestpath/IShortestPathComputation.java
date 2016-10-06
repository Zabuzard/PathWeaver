package de.zabuza.pathweaver.network.algorithm.shortestpath;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
	 * Computes the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given set of sources and a
	 * destination.
	 * 
	 * @param sources
	 *            Set of source nodes to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The shortest path between the given set of sources and
	 *         destination if present, if not present there is no such path.
	 */
	public Optional<Path> computeShortestPath(final Set<Node> sources, final Node destination);

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
	 * {@link #getPathNetwork()} between the given set of sources and a
	 * destination.
	 * 
	 * @param sources
	 *            Set of source nodes to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The cost of the shortest path if present, if not present there is
	 *         no such path.
	 */
	public Optional<Float> computeShortestPathCost(final Set<Node> sources, final Node destination);

	/**
	 * Computes the costs of the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and every reachable
	 * destination.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @return A mapping of all, from the source, reachable destination nodes to
	 *         the costs of the shortest paths from the source to the given
	 *         destinations
	 */
	public Map<Node, Float> computeShortestPathCostsReachable(final Node source);

	/**
	 * Computes the costs of the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given set of sources and every
	 * reachable destination.
	 * 
	 * @param sources
	 *            Set of source nodes to compute the shortest path from
	 * @return A mapping of all, from the set of sources, reachable destination
	 *         nodes to the costs of the shortest paths from the sources to the
	 *         given destinations
	 */
	public Map<Node, Float> computeShortestPathCostsReachable(final Set<Node> sources);

	/**
	 * Computes the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given source and
	 * destination and returns the search space. The search space consists of
	 * all nodes that where visited by the implementing algorithm. Therefore the
	 * main use of this method is for debugging and algorithm comparison.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The search space, i.e. the set of nodes that where visited by the
	 *         implementing algorithm
	 */
	public Set<Node> computeShortestPathSearchSpace(final Node source, final Node destination);

	/**
	 * Computes the shortest path in the network specified by
	 * {@link #getPathNetwork()} between the given set of sources and
	 * destination and returns the search space. The search space consists of
	 * all nodes that where visited by the implementing algorithm. Therefore the
	 * main use of this method is for debugging and algorithm comparison.
	 * 
	 * @param sources
	 *            Set of source nodes to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to
	 * @return The search space, i.e. the set of nodes that where visited by the
	 *         implementing algorithm
	 */
	public Set<Node> computeShortestPathSearchSpace(final Set<Node> sources, final Node destination);

	/**
	 * Gets the path network this object works on.
	 * 
	 * @return The path network this object works on
	 */
	public IPathNetwork getPathNetwork();
}
