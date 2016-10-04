package de.zabuza.pathweaver.network.algorithm.metric;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;

/**
 * Landmark provider that selects landmarks from a given {@link IPathNetwork} by
 * choosing iteratively selecting landmarks that are farthest away from the
 * current set in a greedy fashion.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class GreedyFarthestLandmarkProvider implements ILandmarkProvider<Node> {
	/**
	 * Message which is shown when requesting an amount of landmarks that is not
	 * greater than zero.
	 */
	private static final String LANDMARK_AMOUNT_NEGATIVE = "The given amount must be greater than zero.";
	/**
	 * Message which is shown when requesting more landmarks than are available.
	 */
	private static final String LANDMARK_AMOUNT_UNAVAILABLE = "There must be as many unique landmarks available as asked for.";

	/**
	 * Dijkstra shortest path computation used to compute costs for paths.
	 */
	private final DijkstraShortestPathComputation mComputation;
	/**
	 * The network to select landmarks from.
	 */
	private final IPathNetwork mNetwork;
	/**
	 * The random number generator used for random landmark selection.
	 */
	private final Random mRandom;

	/**
	 * Creates a new landmark provided that selects landmarks from the given
	 * network.
	 * 
	 * @param network
	 *            The network to select landmarks from
	 */
	public GreedyFarthestLandmarkProvider(final IPathNetwork network) {
		mNetwork = network;
		mRandom = new Random();
		mComputation = new DijkstraShortestPathComputation(mNetwork);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.metric.ILandmarkProvider#
	 * getLandmarks(int)
	 */
	@Override
	public Set<Node> getLandmarks(final int amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException(LANDMARK_AMOUNT_NEGATIVE);
		}
		if (amount > mNetwork.getSize()) {
			throw new IllegalArgumentException(LANDMARK_AMOUNT_UNAVAILABLE);
		}

		// Choose the first element randomly
		Collection<Node> nodes = mNetwork.getNodes();
		int firstIndex = mRandom.nextInt(nodes.size());
		Node firstNode;
		if (nodes instanceof List<?>) {
			// Let the list choose the most efficient method for get(index)
			firstNode = ((List<Node>) nodes).get(firstIndex);
		} else {
			// Iterate until the element is found
			Iterator<Node> iter = nodes.iterator();

			// Skip all elements before the index
			for (int i = 0; i < firstIndex; i++) {
				iter.next();
			}

			firstNode = iter.next();
		}

		HashSet<Node> landmarks = new HashSet<>();
		landmarks.add(firstNode);

		// Iteratively select the node which is farthest away
		// from the current set
		while (landmarks.size() < amount) {
			Map<Node, Float> nodeToCost = mComputation.computeShortestPathCostsReachable(landmarks);
			// Search the entry with the highest cost
			float startingCost = -1;
			float highestKnownCost = startingCost;
			Node farthestKnownNode = null;
			for (Entry<Node, Float> entry : nodeToCost.entrySet()) {
				Node node = entry.getKey();
				assert !landmarks.contains(node);

				float cost = entry.getValue();
				if (cost > highestKnownCost) {
					highestKnownCost = cost;
					farthestKnownNode = node;
				}
			}

			// Select the farthest node as landmark
			assert farthestKnownNode != null;
			landmarks.add(farthestKnownNode);
		}

		return landmarks;
	}

}
