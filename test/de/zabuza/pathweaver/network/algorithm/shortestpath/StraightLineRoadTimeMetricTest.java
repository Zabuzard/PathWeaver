package de.zabuza.pathweaver.network.algorithm.shortestpath;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.metric.StraightLineRoadTimeMetric;
import de.zabuza.pathweaver.network.road.RoadNode;
import de.zabuza.pathweaver.network.road.RoadUtil;

/**
 * Test for {@link StraightLineRoadTimeMetric}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class StraightLineRoadTimeMetricTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for
	 * {@link StraightLineRoadTimeMetric#distance(RoadNode, RoadNode)}.
	 */
	@Test
	public void testDistance() {
		StraightLineRoadTimeMetric metric = new StraightLineRoadTimeMetric();
		RoadNode firstNode = new RoadNode(0, 48.069587f, 7.882732f);
		RoadNode secondNode = new RoadNode(1, 48.991774f, 8.403815f);
		float expectedSpeed = 110f;
		float expectedDistance = 109486.33f;
		Assert.assertEquals(RoadUtil.getTravelTime(expectedDistance, expectedSpeed),
				metric.distance(firstNode, secondNode), 0);

		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		exception.expect(IllegalArgumentException.class);
		metric.distance(thirdNode, fourthNode);
	}
}
