package de.zabuza.pathweaver.network.algorithm.metric;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;
import de.zabuza.pathweaver.util.NestedMap2;

/**
 * Metric which estimates the costs between two given {@link Node}s by computing
 * the cost of the path from source to destination over a pre-selected landmark.
 * The more landmarks are used the better is the estimate but the used space
 * will increase.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LandmarkMetric implements IMetric<Node> {

	/**
	 * The amount of landmarks to use.
	 */
	private final int mAmount;
	/**
	 * Dijkstra shortest path computation used to compute costs for paths.
	 */
	private final DijkstraShortestPathComputation mComputation;
	/**
	 * Object which provides landmarks.
	 */
	private final ILandmarkProvider<Node> mLandmarkProvider;
	/**
	 * The set of landmarks to use.
	 */
	private Set<Node> mLandmarks;
	/**
	 * Map which stores the cost needed for traveling from a landmark to a node.
	 */
	private final NestedMap2<Node, Node, Float> mLandmarkToNodeCost;
	/**
	 * The network at which this metric is defined.
	 */
	private final IPathNetwork mNetwork;
	/**
	 * Map which stores the cost needed for traveling from a node to a landmark.
	 */
	private final NestedMap2<Node, Node, Float> mNodeToLandmarkCost;

	/**
	 * Creates a new metric which estimates the costs between two given
	 * {@link Node}s by computing the cost of the path from source to
	 * destination over a pre-selected landmark. This constructor will use a
	 * default {@link ILandmarkProvider} for selecting landmarks.<br/>
	 * The constructor may need some time to compute cost values for all
	 * landmarks.
	 * 
	 * @param amount
	 *            The amount of landmarks to use. The more landmarks the better
	 *            is the estimate but the used space will increase.
	 * @param network
	 *            The network at which this metric is defined
	 */
	public LandmarkMetric(final int amount, final IPathNetwork network) {
		this(amount, network, new RandomLandmarkProvider(network));
		// TODO Exchange with greedy fast
	}

	/**
	 * Creates a new metric which estimates the costs between two given
	 * {@link Node}s by computing the cost of the path from source to
	 * destination over a pre-selected landmark. This constructor will use the
	 * given {@link ILandmarkProvider} for selecting landmarks.<br/>
	 * The constructor may need some time to compute cost values for all
	 * landmarks.
	 * 
	 * @param amount
	 *            The amount of landmarks to use. The more landmarks the better
	 *            is the estimate but the used space will increase.
	 * @param network
	 *            The network at which this metric is defined
	 * @param landmarkProvider
	 *            The provider used to select landmarks
	 */
	public LandmarkMetric(final int amount, final IPathNetwork network,
			final ILandmarkProvider<Node> landmarkProvider) {
		mNetwork = network;
		mLandmarkProvider = landmarkProvider;
		mAmount = amount;
		mLandmarkToNodeCost = new NestedMap2<>();
		mNodeToLandmarkCost = new NestedMap2<>();
		mComputation = new DijkstraShortestPathComputation(mNetwork);

		initialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.algorithm.shortestpath.IMetric#distance(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public float distance(final Node first, final Node second) {
		float greatestDistanceWithLandmark = -1;
		for (Node landmark : mLandmarks) {
			float landmarkBehindDestinationCost = mNodeToLandmarkCost.get(first, landmark)
					- mNodeToLandmarkCost.get(second, landmark);
			float landmarkBeforeSourceCost = mLandmarkToNodeCost.get(landmark, second)
					- mLandmarkToNodeCost.get(landmark, first);
			float distanceWithLandmark = Math.max(landmarkBehindDestinationCost, landmarkBeforeSourceCost);

			if (distanceWithLandmark > greatestDistanceWithLandmark) {
				greatestDistanceWithLandmark = distanceWithLandmark;
			}
		}

		return greatestDistanceWithLandmark;
	}

	private void initialize() {
		mLandmarks = mLandmarkProvider.getLandmarks(mAmount);

		// Computation of costs from landmarks to all other nodes
		for (Node landmark : mLandmarks) {
			Map<Node, Float> nodeToCost = mComputation.computeShortestPathCostsReachable(landmark);
			for (Entry<Node, Float> entry : nodeToCost.entrySet()) {
				mLandmarkToNodeCost.put(landmark, entry.getKey(), entry.getValue());
			}
		}

		// Computation of costs from all other nodes to landmarks
		mNetwork.reverse();
		for (Node landmark : mLandmarks) {
			Map<Node, Float> nodeToCost = mComputation.computeShortestPathCostsReachable(landmark);
			for (Entry<Node, Float> entry : nodeToCost.entrySet()) {
				mNodeToLandmarkCost.put(entry.getKey(), landmark, entry.getValue());
			}
		}

		mNetwork.reverse();
	}

}
