package de.zabuza.pathweaver.network.algorithm.metric.landmark;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.metric.IMetric;
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
	 * Map which stores the cost needed for traveling from a landmark to a node.
	 */
	private final NestedMap2<Integer, Integer, Float> mLandmarkIdAndNodeIdToCost;
	/**
	 * Object which provides landmarks.
	 */
	private final ILandmarkProvider<Node> mLandmarkProvider;
	/**
	 * The set of landmarks to use.
	 */
	private Set<Node> mLandmarks;
	/**
	 * The network at which this metric is defined.
	 */
	private final IPathNetwork mNetwork;
	/**
	 * Map which stores the cost needed for traveling from a node to a landmark.
	 */
	private final NestedMap2<Integer, Integer, Float> mNodeIdAndToLandmarkIdToCost;

	/**
	 * Creates a new metric which estimates the costs between two given
	 * {@link Node}s by computing the cost of the path from source to
	 * destination over a pre-selected landmark. This constructor will use a
	 * default {@link ILandmarkProvider} for selecting landmarks.<br>
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
		this(amount, network, new GreedyFarthestLandmarkProvider(network));
	}

	/**
	 * Creates a new metric which estimates the costs between two given
	 * {@link Node}s by computing the cost of the path from source to
	 * destination over a pre-selected landmark. This constructor will use the
	 * given {@link ILandmarkProvider} for selecting landmarks.<br>
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
		this.mNetwork = network;
		this.mLandmarkProvider = landmarkProvider;
		this.mAmount = amount;
		this.mLandmarkIdAndNodeIdToCost = new NestedMap2<>();
		this.mNodeIdAndToLandmarkIdToCost = new NestedMap2<>();
		this.mComputation = new DijkstraShortestPathComputation(this.mNetwork);

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
		float startingDistance = 0;
		float greatestDistanceWithLandmark = startingDistance;
		for (Node landmark : this.mLandmarks) {
			final Integer firstId = Integer.valueOf(first.getId());
			final Integer secondId = Integer.valueOf(second.getId());
			final Integer landmarkId = Integer.valueOf(landmark.getId());

			float landmarkBehindDestinationCost = this.mNodeIdAndToLandmarkIdToCost.get(firstId, landmarkId)
					.floatValue() - this.mNodeIdAndToLandmarkIdToCost.get(secondId, landmarkId).floatValue();
			float landmarkBeforeSourceCost = this.mLandmarkIdAndNodeIdToCost.get(landmarkId, secondId).floatValue()
					- this.mLandmarkIdAndNodeIdToCost.get(landmarkId, firstId).floatValue();
			float distanceWithLandmark = Math.max(landmarkBehindDestinationCost, landmarkBeforeSourceCost);

			if (distanceWithLandmark > greatestDistanceWithLandmark) {
				greatestDistanceWithLandmark = distanceWithLandmark;
			}
		}

		assert greatestDistanceWithLandmark >= 0;

		return greatestDistanceWithLandmark;
	}

	/**
	 * Initializes the metric by computing costs from and to all landmarks. This
	 * may take some time depending on the size of the network and the amount of
	 * landmarks.
	 */
	private void initialize() {
		this.mLandmarks = this.mLandmarkProvider.getLandmarks(this.mAmount);

		// Computation of costs from landmarks to all other nodes
		for (Node landmark : this.mLandmarks) {
			Map<Node, Float> nodeToCost = this.mComputation.computeShortestPathCostsReachable(landmark);
			for (Entry<Node, Float> entry : nodeToCost.entrySet()) {
				this.mLandmarkIdAndNodeIdToCost.put(Integer.valueOf(landmark.getId()),
						Integer.valueOf(entry.getKey().getId()), entry.getValue());
			}
		}

		// Computation of costs from all other nodes to landmarks
		this.mNetwork.reverse();
		for (Node landmark : this.mLandmarks) {
			Map<Node, Float> nodeToCost = this.mComputation.computeShortestPathCostsReachable(landmark);
			for (Entry<Node, Float> entry : nodeToCost.entrySet()) {
				this.mNodeIdAndToLandmarkIdToCost.put(Integer.valueOf(entry.getKey().getId()),
						Integer.valueOf(landmark.getId()), entry.getValue());
			}
		}

		this.mNetwork.reverse();
	}

}
