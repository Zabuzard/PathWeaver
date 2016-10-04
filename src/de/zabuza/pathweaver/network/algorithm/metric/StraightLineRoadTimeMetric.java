package de.zabuza.pathweaver.network.algorithm.metric;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.road.RoadNode;
import de.zabuza.pathweaver.network.road.RoadUtil;

/**
 * Metric which computes the time needed to travel the straight line distance
 * between two given {@link RoadNode}s with maximal road speed.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class StraightLineRoadTimeMetric implements IMetric<Node> {
	/**
	 * Message shown when trying to compute a distance between two nodes that
	 * are not of type {@link RoadNode} with this metric.
	 */
	private static final String ILLEGAL_NODE_MESSAGE = "This metric only supports elements of type RoadNode.";

	/**
	 * @throws IllegalArgumentException
	 *             If arguments are not type of {@link RoadNode}.
	 */
	@Override
	public float distance(final Node first, final Node second) throws IllegalArgumentException {
		if (!(first instanceof RoadNode) || !(second instanceof RoadNode)) {
			throw new IllegalArgumentException(ILLEGAL_NODE_MESSAGE);
		}

		float distance = RoadUtil.distanceEquiRect((RoadNode) first, (RoadNode) second);
		float maximalSpeed = RoadUtil.getAverageSpeedOfRoadType(RoadUtil.getFastestRoadType());
		return RoadUtil.getTravelTime(distance, maximalSpeed);
	}

}
