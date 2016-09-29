package de.zabuza.pathweaver.network.algorithm;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Test for {@link DijkstraShortestPathComputation}.
 * 
 * @author Zabuza
 *
 */
public class DijkstraShortestPathComputationTest {

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPathCost(Node, Node)}
	 * .
	 */
	@Test
	public void testComputeShortestPathCost() {
		PathNetwork network = new PathNetwork();
		DijkstraShortestPathComputation computation = new DijkstraShortestPathComputation(network);

		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		Node fifthNode = new Node(4);
		Node sixthNode = new Node(5);

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addNode(fifthNode);
		network.addNode(sixthNode);

		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(firstNode, thirdNode, 3);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(firstNode, fourthNode, 10);
		network.addEdge(firstNode, fifthNode, 4);
		network.addEdge(fifthNode, secondNode, 5);
		network.addEdge(fifthNode, sixthNode, 3);
		network.addEdge(sixthNode, fourthNode, 1);

		network.addEdge(secondNode, firstNode, 1);
		network.addEdge(thirdNode, secondNode, 1);
		network.addEdge(thirdNode, firstNode, 3);
		network.addEdge(fourthNode, thirdNode, 1);
		network.addEdge(fourthNode, firstNode, 10);
		network.addEdge(fifthNode, firstNode, 4);
		network.addEdge(secondNode, fifthNode, 5);
		network.addEdge(sixthNode, fifthNode, 3);
		network.addEdge(fourthNode, sixthNode, 1);

		Assert.assertEquals(3, computation.computeShortestPathCost(firstNode, fourthNode), 0);
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPathCostsReachable(Node)}
	 * .
	 */
	@Test
	public void testComputeShortestPathCostsReachable() {
		PathNetwork network = new PathNetwork();
		DijkstraShortestPathComputation computation = new DijkstraShortestPathComputation(network);

		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		Node fifthNode = new Node(4);
		Node sixthNode = new Node(5);

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addNode(fifthNode);
		network.addNode(sixthNode);

		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(firstNode, thirdNode, 3);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(firstNode, fourthNode, 10);
		network.addEdge(firstNode, fifthNode, 4);
		network.addEdge(fifthNode, secondNode, 5);
		network.addEdge(fifthNode, sixthNode, 3);
		network.addEdge(sixthNode, fourthNode, 1);

		network.addEdge(secondNode, firstNode, 1);
		network.addEdge(thirdNode, secondNode, 1);
		network.addEdge(thirdNode, firstNode, 3);
		network.addEdge(fourthNode, thirdNode, 1);
		network.addEdge(fourthNode, firstNode, 10);
		network.addEdge(fifthNode, firstNode, 4);
		network.addEdge(secondNode, fifthNode, 5);
		network.addEdge(sixthNode, fifthNode, 3);
		network.addEdge(fourthNode, sixthNode, 1);

		Map<Node, Float> costMapping = computation.computeShortestPathCostsReachable(firstNode);

		Assert.assertEquals(6, costMapping.size());
		Assert.assertEquals(0, costMapping.get(firstNode), 0);
		Assert.assertEquals(1, costMapping.get(secondNode), 0);
		Assert.assertEquals(2, costMapping.get(thirdNode), 0);
		Assert.assertEquals(3, costMapping.get(fourthNode), 0);
		Assert.assertEquals(4, costMapping.get(fifthNode), 0);
		Assert.assertEquals(4, costMapping.get(sixthNode), 0);
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#DijkstraShortestPathComputation(IPathNetwork)}
	 * .
	 */
	@Test
	public void testDijkstraShortestPathComputation() {
		PathNetwork network = new PathNetwork();
		DijkstraShortestPathComputation computation = new DijkstraShortestPathComputation(network);
		PathNetwork anotherNetwork = new PathNetwork();
		DijkstraShortestPathComputation anotherComputation = new DijkstraShortestPathComputation(anotherNetwork);

		Assert.assertEquals(network, computation.getPathNetwork());
		Assert.assertEquals(anotherNetwork, anotherComputation.getPathNetwork());
	}

	/**
	 * Test method for {@link DijkstraShortestPathComputation#getPathNetwork()}.
	 */
	@Test
	public void testGetPathNetwork() {
		PathNetwork network = new PathNetwork();
		DijkstraShortestPathComputation computation = new DijkstraShortestPathComputation(network);
		PathNetwork anotherNetwork = new PathNetwork();
		DijkstraShortestPathComputation anotherComputation = new DijkstraShortestPathComputation(anotherNetwork);

		Assert.assertEquals(network, computation.getPathNetwork());
		Assert.assertEquals(anotherNetwork, anotherComputation.getPathNetwork());
	}

}
