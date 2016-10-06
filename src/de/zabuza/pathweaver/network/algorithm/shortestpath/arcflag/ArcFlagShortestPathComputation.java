package de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.TentativeNodeContainer;
import de.zabuza.pathweaver.util.NestedMap2;

/**
 * Arc flag shortest path algorithm which solves shortest path computation tasks
 * in {@link PathNetwork}s by dividing the network into regions and
 * pre-computing shortest paths from everywhere to the regions.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ArcFlagShortestPathComputation extends DijkstraShortestPathComputation {
	/**
	 * Data structure that maps edges and regions to their relevance. If the
	 * boolean is set to true, then the edge is relevant for reaching the region
	 * with a shortest path.
	 */
	private final NestedMap2<DirectedWeightedEdge, Integer, Boolean> mEdgeAndRegionIdToRelevance;
	/**
	 * Maps each region with an ID, accessible by the ID.
	 */
	private final HashMap<Integer, Set<Node>> mIdToRegion;
	/**
	 * Maps each node to the region ID it is contained in.
	 */
	private final HashMap<Node, Integer> mNodeToRegionId;
	/**
	 * Provider used for partitioning the network into regions.
	 */
	private final INetworkPartitioningProvider mPartitioningProvider;

	/**
	 * Creates a new shortest path computation object with the given
	 * partitioning provider.<br/>
	 * The constructor may need some time to compute the flags for all arcs and
	 * regions.
	 * 
	 * @param network
	 *            The network to work on
	 * @param partitioningProvider
	 *            The provider used for partitioning the network into regions
	 */
	public ArcFlagShortestPathComputation(final IPathNetwork network,
			final INetworkPartitioningProvider partitioningProvider) {
		super(network);
		mPartitioningProvider = partitioningProvider;
		mEdgeAndRegionIdToRelevance = new NestedMap2<>();
		mNodeToRegionId = new HashMap<>();
		mIdToRegion = new HashMap<>();

		initialize();
	}

	/**
	 * Initializes the computation object by computing the flags for each arc
	 * and region. This may take some time depending on the size of the network
	 * and the amount and size of regions.
	 */
	private void initialize() {
		// Compute the arc flags
		Collection<Set<Node>> regions = mPartitioningProvider.getPartitioning();
		IPathNetwork network = getPathNetwork();
		// Computation is done on the reversed graph
		network.reverse();

		int regionId = 0;
		// Iterate every node of every region
		for (Set<Node> region : regions) {
			mIdToRegion.put(regionId, region);

			for (Node node : region) {
				mNodeToRegionId.put(node, regionId);

				// A boundary node has an incoming edge from another reagion. In
				// the reversed graph this are outgoing egdes.
				boolean isBoundaryNode = false;
				for (DirectedWeightedEdge edge : network.getOutgoingEdges(node)) {
					// If source and destination are inside the region, the flag
					// will be set for the edge
					boolean isEdgeInside = region.contains(edge.getDestination());
					if (isEdgeInside) {
						mEdgeAndRegionIdToRelevance.put(edge, regionId, Boolean.TRUE);
					}

					// If the edge is not inside, the node is a boundary node
					if (!isEdgeInside && !isBoundaryNode) {
						isBoundaryNode = true;

						// Perform a Dijkstra search to compute all shortest
						// paths to this node
						Map<Node, TentativeNodeContainer> nodeToData = computeShortestPathCostHelper(
								Collections.singleton(node), Optional.empty());
						// Set the flag for every parent edge of a settled node,
						// as they are part of a shortest path from
						// the boundary node
						for (TentativeNodeContainer settledNodeContainer : nodeToData.values()) {
							DirectedWeightedEdge parentEdge = settledNodeContainer.getParentEdge();

							// Ignore the edge if the settled node is the source
							if (parentEdge == null) {
								continue;
							}

							// Settle the flag for the edge
							mEdgeAndRegionIdToRelevance.put(parentEdge, regionId, Boolean.TRUE);
						}
					}
				}
			}
			regionId++;
		}

		// Undo the network reversion
		network.reverse();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * DijkstraShortestPathComputation#considerOutgoingEdgeForRelaxation(de.
	 * zabuza.pathweaver.network.DirectedWeightedEdge, java.util.Optional)
	 */
	@Override
	protected boolean considerOutgoingEdgeForRelaxation(final DirectedWeightedEdge outgoingEdge,
			final Optional<Node> destination) {
		// If there is a specific destination and the flag of the edge for this
		// region is not set, then do not consider it
		if (destination.isPresent()) {
			Node destinationNode = destination.get();

			// Get the region of the node
			Integer regionId = mNodeToRegionId.get(destinationNode);
			Boolean isEdgeImportant = mEdgeAndRegionIdToRelevance.get(outgoingEdge, regionId);
			if (isEdgeImportant != null && isEdgeImportant) {
				// Flag is set, consider the edge
				return true;
			} else {
				// Flag is not set, do not consider the edge
				return false;
			}
		}

		return true;
	}
}
