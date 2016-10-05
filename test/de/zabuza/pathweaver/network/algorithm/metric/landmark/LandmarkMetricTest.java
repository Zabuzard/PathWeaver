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
	@Test
	public void testDistance() {
		IPathNetwork network = new PathNetwork();
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(fourthNode, firstNode, 1);

		LandmarkMetric metric = new LandmarkMetric(2, network);

		float firstDistance = metric.distance(firstNode, firstNode);
		Assert.assertEquals(0, firstDistance, 0);

		float secondDistance = metric.distance(firstNode, fourthNode);
		Assert.assertTrue(secondDistance == 0 || secondDistance == 1 || secondDistance == 3);
	}

	/**
	 * Test method for
	 * {@link LandmarkMetric#LandmarkMetric(int, IPathNetwork, ILandmarkProvider)}
	 * .
	 */
	@Test
	public void testLandmarkMetricIntIPathNetworkILandmarkProviderOfNode() {
		IPathNetwork network = new PathNetwork();
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(fourthNode, firstNode, 1);

		LandmarkMetric metric = new LandmarkMetric(2, network);

		float firstDistance = metric.distance(firstNode, firstNode);
		Assert.assertEquals(0, firstDistance, 0);

		float secondDistance = metric.distance(firstNode, fourthNode);
		Assert.assertTrue(secondDistance == 0 || secondDistance == 1 || secondDistance == 3);
	}

}
