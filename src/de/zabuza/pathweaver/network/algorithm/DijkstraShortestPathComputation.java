package de.zabuza.pathweaver.network.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.OutgoingEdge;
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
	 * computeShortestPathCost(de.zabuza.pathweaver.network.Node,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public float computeShortestPathCost(Node source, Node destination) {
		Map<Node, Float> mapping = computeShortestPathCostHelper(source, Optional.of(destination));
		assert (mapping.containsKey(destination));
		return mapping.get(destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCostsReachable(de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public Map<Node, Float> computeShortestPathCostsReachable(Node source) {
		return computeShortestPathCostHelper(source, Optional.empty());
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
	 *         costs of the shortest paths from the source to the given
	 *         destinations. If a destination is given, then it will also be
	 *         contained in this mapping.
	 */
	private Map<Node, Float> computeShortestPathCostHelper(final Node source, final Optional<Node> destination) {
		HashMap<Node, TentativeCostNodeContainer> nodeToContainer = new HashMap<>();
		PriorityQueue<TentativeCostNodeContainer> activeNodes = new PriorityQueue<TentativeCostNodeContainer>();
		HashMap<Node, Float> nodeToSettledCost = new HashMap<>();

		// Start with the source as initial node
		TentativeCostNodeContainer sourceContainer = new TentativeCostNodeContainer(source, 0);
		nodeToContainer.put(source, sourceContainer);
		activeNodes.add(sourceContainer);

		while (!activeNodes.isEmpty()) {
			// Poll the node with the lowest cost
			TentativeCostNodeContainer currentNodeContainer = activeNodes.poll();
			assert (currentNodeContainer != null);
			float currentTentativeCost = currentNodeContainer.getTentativeCost();
			Node currentNode = currentNodeContainer.getNode();

			// If the node was already settled before, this container was
			// previously abandoned while updating the tentative costs for this
			// node.
			if (nodeToSettledCost.containsKey(currentNode)) {
				continue;
			}

			// Settle the current node
			Float previousValue = nodeToSettledCost.put(currentNode, currentTentativeCost);
			assert (previousValue == null);

			// End if destination was settled
			if (destination.isPresent() && currentNode.equals(destination.get())) {
				break;
			}

			// Relax all outgoing edges
			Set<OutgoingEdge> outgoingEdges = mNetwork.getOutgoingEdges(currentNode);
			if (outgoingEdges == null || outgoingEdges.isEmpty()) {
				continue;
			}
			for (OutgoingEdge outgoingEdge : outgoingEdges) {
				Node edgeDestination = outgoingEdge.getDestination();
				float tentativeEdgeCost = currentTentativeCost + outgoingEdge.getCost();

				// Check if there is already a container, if not create one
				TentativeCostNodeContainer edgeDestinationContainer = nodeToContainer.get(edgeDestination);
				if (edgeDestinationContainer == null) {
					// Edge destination is visited for the first time
					edgeDestinationContainer = new TentativeCostNodeContainer(edgeDestination, tentativeEdgeCost);
					nodeToContainer.put(edgeDestination, edgeDestinationContainer);
					activeNodes.add(edgeDestinationContainer);
				} else {
					if (nodeToSettledCost.containsKey(edgeDestination)) {
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
						TentativeCostNodeContainer betterEdgeDestinationContainer = new TentativeCostNodeContainer(
								edgeDestination, tentativeEdgeCost);
						nodeToContainer.put(edgeDestination, betterEdgeDestinationContainer);
						activeNodes.add(betterEdgeDestinationContainer);
					}
				}
			}
		}
		return nodeToSettledCost;
	}

}
