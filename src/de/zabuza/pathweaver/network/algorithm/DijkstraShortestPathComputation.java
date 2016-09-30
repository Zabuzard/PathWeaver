package de.zabuza.pathweaver.network.algorithm;

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
 * @author Zabuza
 *
 */
public class DijkstraShortestPathComputation implements IShortestPathComputation {
	/**
	 * The path network this object works on.
	 */
	private final IPathNetwork mNetwork;

	/**
	 * Creates a new shortest path computation object
	 * 
	 * @param network
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
	public Path computeShortestPath(final Node source, final Node destination) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(source, Optional.of(destination));
		assert (nodeToData.containsKey(destination));

		LinkedList<DirectedWeightedEdge> edgesBackwards = new LinkedList<>();
		Node currentNode = destination;
		while (currentNode != source) {
			TentativeNodeContainer container = nodeToData.get(currentNode);
			DirectedWeightedEdge parentEdge = container.getParentEdge();
			edgesBackwards.add(parentEdge);

			currentNode = parentEdge.getSource();
		}

		Path path = new Path(source);
		Collections.reverse(edgesBackwards);
		while (!edgesBackwards.isEmpty()) {
			path.addEdge(edgesBackwards.poll());
		}
		assert (path.getSource() == source && path.getDestination() == destination);

		return path;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCost(de.zabuza.pathweaver.network.Node,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public float computeShortestPathCost(Node source, Node destination) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(source, Optional.of(destination));
		assert (nodeToData.containsKey(destination));
		return nodeToData.get(destination).getTentativeCost();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCostsReachable(de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Map<Node, Float> computeShortestPathCostsReachable(Node source) {
		Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(source, Optional.empty());
		Map<Node, Float> nodeToCost = new HashMap<Node, Float>();
		for (Entry<Node, TentativeNodeContainer> entry : nodeToData.entrySet()) {
			nodeToCost.put(entry.getKey(), entry.getValue().getTentativeCost());
		}
		return nodeToCost;
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
	 * Helper method for computing shortest paths between the source and a given
	 * destination. If the destination is not given, then all, from source,
	 * reachable nodes get considered as destinations.
	 * 
	 * @param source
	 *            Source node to compute the shortest path from
	 * @param destination
	 *            Destination node to compute the shortest path to, if present.
	 *            If not present, then all, from source, reachable nodes get
	 *            considered as destinations.
	 * @return A mapping of all, from source, reachable destination nodes to the
	 *         data container of the shortest paths from the source to the given
	 *         destinations. If a destination is given, then it will also be
	 *         contained in this mapping and the rest will be nodes that are
	 *         reachable in shorter time than this destination.
	 */
	private Map<Node, TentativeNodeContainer> computeShortestPathCostHelper(final Node source,
			final Optional<Node> destination) {
		HashMap<Node, TentativeNodeContainer> nodeToContainer = new HashMap<>();
		PriorityQueue<TentativeNodeContainer> activeNodes = new PriorityQueue<TentativeNodeContainer>();
		HashMap<Node, TentativeNodeContainer> nodeToSettledContainer = new HashMap<>();

		// Start with the source as initial node
		TentativeNodeContainer sourceContainer;
		if (destination.isPresent()) {
			sourceContainer = new TentativeNodeContainer(source, null, 0, getEstCostToDest(source, destination.get()));
		} else {
			sourceContainer = new TentativeNodeContainer(source, null, 0);
		}
		nodeToContainer.put(source, sourceContainer);
		activeNodes.add(sourceContainer);

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
			Set<DirectedWeightedEdge> outgoingEdges = mNetwork.getOutgoingEdges(currentNode);
			if (outgoingEdges == null || outgoingEdges.isEmpty()) {
				continue;
			}
			for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
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
