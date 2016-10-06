package de.zabuza.pathweaver.network.algorithm.shortestpath;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.Path;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Dijkstras shortest path algorithm which solves shortest path computation
 * tasks in {@link PathNetwork}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class DijkstraShortestPathComputation implements IShortestPathComputation {
	/**
	 * The path network this object works on.
	 */
	private final IPathNetwork mNetwork;

	/**
	 * Creates a new shortest path computation object.
	 * 
	 * @param network
	 *            The network to work on
	 */
	public DijkstraShortestPathComputation(final IPathNetwork network) {
		mNetwork = network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPath(de.zabuza.pathweaver.network.Node,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Optional<Path> computeShortestPath(final Node source, final Node destination) {
		return computeShortestPath(Collections.singleton(source), destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * IShortestPathComputation#computeShortestPath(java.util.List,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Optional<Path> computeShortestPath(final Set<Node> sources, final Node destination) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(sources, Optional.of(destination));
		if (!nodeToData.containsKey(destination)) {
			return Optional.empty();
		}

		LinkedList<DirectedWeightedEdge> edgesBackwards = new LinkedList<>();
		Node currentNode = destination;
		while (!sources.contains(currentNode)) {
			TentativeNodeContainer container = nodeToData.get(currentNode);
			DirectedWeightedEdge parentEdge = container.getParentEdge();
			edgesBackwards.add(parentEdge);

			currentNode = parentEdge.getSource();
		}

		Path path = new Path(currentNode);
		Collections.reverse(edgesBackwards);
		while (!edgesBackwards.isEmpty()) {
			path.addEdge(edgesBackwards.poll());
		}
		assert (path.getSource() == currentNode && path.getDestination() == destination);

		return Optional.of(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCost(de.zabuza.pathweaver.network.Node,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Optional<Float> computeShortestPathCost(final Node source, final Node destination) {
		return computeShortestPathCost(Collections.singleton(source), destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * IShortestPathComputation#computeShortestPathCost(java.util.List,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Optional<Float> computeShortestPathCost(final Set<Node> sources, final Node destination) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(sources, Optional.of(destination));
		if (nodeToData.containsKey(destination)) {
			return Optional.of(nodeToData.get(destination).getTentativeCost());
		} else {
			return Optional.empty();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCostsReachable(de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Map<Node, Float> computeShortestPathCostsReachable(final Node source) {
		return computeShortestPathCostsReachable(Collections.singleton(source));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * IShortestPathComputation#computeShortestPathCostsReachable(java.util.
	 * List)
	 */
	@Override
	public Map<Node, Float> computeShortestPathCostsReachable(final Set<Node> sources) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(sources, Optional.empty());
		Map<Node, Float> nodeToCost = new HashMap<Node, Float>();
		for (Entry<Node, TentativeNodeContainer> entry : nodeToData.entrySet()) {
			nodeToCost.put(entry.getKey(), entry.getValue().getTentativeCost());
		}
		return nodeToCost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * IShortestPathComputation#computeShortestPathSearchSpace(de.zabuza.
	 * pathweaver.network.Node, de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Set<Node> computeShortestPathSearchSpace(final Node source, final Node destination) {
		return computeShortestPathSearchSpace(Collections.singleton(source), destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * IShortestPathComputation#computeShortestPathSearchSpace(java.util.Set,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Set<Node> computeShortestPathSearchSpace(final Set<Node> sources, final Node destination) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(sources, Optional.empty());
		return nodeToData.keySet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * getPathNetwork()
	 */
	@Override
	public IPathNetwork getPathNetwork() {
		return mNetwork;
	}

	/**
	 * Helper method for computing shortest paths between the set of sources and
	 * a given destination. If the destination is not given, then all, from
	 * source, reachable nodes get considered as destinations.
	 * 
	 * @param sources
	 *            Set of source nodes to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to, if present.
	 *            If not present, then all, from the set of sources, reachable
	 *            nodes get considered as destinations.
	 * @return A mapping of all, from the set of sources, reachable destination
	 *         nodes to the data container of the shortest paths from the
	 *         sources to the given destinations. If a destination is given,
	 *         then it will also be contained in this mapping and the rest will
	 *         be nodes that are reachable in shorter time than this
	 *         destination.
	 */
	protected Map<Node, TentativeNodeContainer> computeShortestPathCostHelper(final Set<Node> sources,
			final Optional<Node> destination) {
		HashMap<Node, TentativeNodeContainer> nodeToContainer = new HashMap<>();
		PriorityQueue<TentativeNodeContainer> activeNodes = new PriorityQueue<TentativeNodeContainer>();
		HashMap<Node, TentativeNodeContainer> nodeToSettledContainer = new HashMap<>();

		// Start with the set of sources as initial node
		for (Node source : sources) {
			TentativeNodeContainer sourceContainer;
			if (destination.isPresent()) {
				sourceContainer = new TentativeNodeContainer(source, null, 0,
						getEstCostToDest(source, destination.get()));
			} else {
				sourceContainer = new TentativeNodeContainer(source, null, 0);
			}
			nodeToContainer.put(source, sourceContainer);
			activeNodes.add(sourceContainer);
		}

		while (!activeNodes.isEmpty()) {
			// Poll the node with the lowest cost
			TentativeNodeContainer currentNodeContainer = activeNodes.poll();
			assert (currentNodeContainer != null);
			float currentTentativeCost = currentNodeContainer.getTentativeCost();
			Node currentNode = currentNodeContainer.getNode();

			// If the node was already settled before, this container was
			// previously abandoned while updating the tentative costs for this
			// node.
			if (nodeToSettledContainer.containsKey(currentNode)) {
				continue;
			}

			// Settle the current node
			TentativeNodeContainer previousValue = nodeToSettledContainer.put(currentNode, currentNodeContainer);
			assert (previousValue == null);

			// End if destination was settled
			if (destination.isPresent() && currentNode.equals(destination.get())) {
				break;
			}

			// Relax all outgoing edges
			Set<DirectedWeightedEdge> outgoingEdges = getPathNetwork().getOutgoingEdges(currentNode);
			if (outgoingEdges == null || outgoingEdges.isEmpty()) {
				continue;
			}
			for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
				// Ignore the edge if it should not be considered
				if (!considerOutgoingEdgeForRelaxation(outgoingEdge, destination)) {
					continue;
				}

				Node edgeDestination = outgoingEdge.getDestination();
				float tentativeEdgeCost = currentTentativeCost + outgoingEdge.getCost();

				// Check if there is already a container, if not create one
				TentativeNodeContainer edgeDestinationContainer = nodeToContainer.get(edgeDestination);
				if (edgeDestinationContainer == null) {
					// Edge destination is visited for the first time
					if (destination.isPresent()) {
						edgeDestinationContainer = new TentativeNodeContainer(edgeDestination, outgoingEdge,
								tentativeEdgeCost, getEstCostToDest(edgeDestination, destination.get()));
					} else {
						edgeDestinationContainer = new TentativeNodeContainer(edgeDestination, outgoingEdge,
								tentativeEdgeCost);
					}
					nodeToContainer.put(edgeDestination, edgeDestinationContainer);
					activeNodes.add(edgeDestinationContainer);
				} else {
					if (nodeToSettledContainer.containsKey(edgeDestination)) {
						// Settled nodes can not be improved anymore
						continue;
					}
					// Check if this edge improves the tentative costs of the
					// edge destination
					float currentTentativeEdgeDestinationCost = edgeDestinationContainer.getTentativeCost();
					if (tentativeEdgeCost < currentTentativeEdgeDestinationCost) {
						// Improve the edge destination cost by abandoning the
						// old container, it holds an non optimal value, then
						// creating a new container with improved costs and add
						// it to the queue.
						TentativeNodeContainer betterEdgeDestinationContainer;
						if (destination.isPresent()) {
							betterEdgeDestinationContainer = new TentativeNodeContainer(edgeDestination, outgoingEdge,
									tentativeEdgeCost, getEstCostToDest(edgeDestination, destination.get()));
						} else {
							betterEdgeDestinationContainer = new TentativeNodeContainer(edgeDestination, outgoingEdge,
									tentativeEdgeCost);
						}

						nodeToContainer.put(edgeDestination, betterEdgeDestinationContainer);
						activeNodes.add(betterEdgeDestinationContainer);
					}
				}
			}
		}
		return nodeToSettledContainer;
	}

	/**
	 * Whether the outgoing edge should be considered for relaxation or not.
	 * 
	 * @param outgoingEdge
	 *            The edge in question
	 * @param destination
	 *            Destination node to compute the shortest path to, if present.
	 *            If not present, then all, from the set of sources, reachable
	 *            nodes get considered as destinations.
	 * @return <tt>True</tt> if the outgoing edge should be considered for
	 *         relaxation, <tt>false</tt> if not.
	 */
	protected boolean considerOutgoingEdgeForRelaxation(final DirectedWeightedEdge outgoingEdge,
			final Optional<Node> destination) {
		// Dijkstras algorithm does consider every outgoing edge.
		return true;
	}

	/**
	 * Gets the estimated cost needed to reach the given destination from the
	 * given node.
	 * 
	 * @param node
	 *            Node in question
	 * @param dest
	 *            Destination to estimate required costs for reaching it
	 * @return The estimated cost needed to reach the given destination from the
	 *         given node
	 */
	protected float getEstCostToDest(final Node node, final Node dest) {
		// Dijkstras algorithm does not use such estimations. Thus it is
		// extremely optimistic and guesses zero costs in any case.
		return 0;
	}

}
