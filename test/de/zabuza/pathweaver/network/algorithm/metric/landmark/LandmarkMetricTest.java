package de.zabuza.pathweaver.network.algorithm.metric.landmark;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.network.algorithm.metric.landmark.LandmarkMetric;

/**
 * Test for {@link LandmarkMetric}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class LandmarkMetricTest {

	/**
	 * Test method for {@link LandmarkMetric#distance(Node, Node)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDistance() {
		final IPathNetwork network = new PathNetwork();
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);
		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(fourthNode, firstNode, 1);

		final LandmarkMetric metric = new LandmarkMetric(2, network);

		final float firstDistance = metric.distance(firstNode, firstNode);
		Assert.assertEquals(0, firstDistance, 0);

		final float secondDistance = metric.distance(firstNode, fourthNode);
		Assert.assertTrue(secondDistance == 0 || secondDistance == 1 || secondDistance == 3);
	}

	/**
	 * Test method for
	 * {@link LandmarkMetric#LandmarkMetric(int, IPathNetwork, ILandmarkProvider)}
	 * .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testLandmarkMetricIntIPathNetworkILandmarkProviderOfNode() {
		final IPathNetwork network = new PathNetwork();
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);
		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(fourthNode, firstNode, 1);

		final LandmarkMetric metric = new LandmarkMetric(2, network);

		final float firstDistance = metric.distance(firstNode, firstNode);
		Assert.assertEquals(0, firstDistance, 0);

		final float secondDistance = metric.distance(firstNode, fourthNode);
		Assert.assertTrue(secondDistance == 0 || secondDistance == 1 || secondDistance == 3);
	}

}
