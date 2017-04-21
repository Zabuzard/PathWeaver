package de.zabuza.pathweaver.network.algorithm.shortestpath;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.Path;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;

/**
 * Test for {@link DijkstraShortestPathComputation}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class DijkstraShortestPathComputationTest {

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPath(Node, Node)} .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testComputeShortestPath() {
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

		Path path = computation.computeShortestPath(firstNode, fourthNode).get();

		Assert.assertEquals(3, path.getCost(), 0);
		Assert.assertEquals(firstNode, path.getSource());
		Assert.assertEquals(fourthNode, path.getDestination());
		Assert.assertEquals(4, path.getLength());

		List<DirectedWeightedEdge> edges = path.getEdges();
		Iterator<DirectedWeightedEdge> edgeIter = edges.iterator();

		DirectedWeightedEdge firstEdge = edgeIter.next();
		Assert.assertEquals(1, firstEdge.getCost(), 0);
		Assert.assertEquals(firstNode, firstEdge.getSource());
		Assert.assertEquals(secondNode, firstEdge.getDestination());

		DirectedWeightedEdge secondEdge = edgeIter.next();
		Assert.assertEquals(1, secondEdge.getCost(), 0);
		Assert.assertEquals(secondNode, secondEdge.getSource());
		Assert.assertEquals(thirdNode, secondEdge.getDestination());

		DirectedWeightedEdge thirdEdge = edgeIter.next();
		Assert.assertEquals(1, thirdEdge.getCost(), 0);
		Assert.assertEquals(thirdNode, thirdEdge.getSource());
		Assert.assertEquals(fourthNode, thirdEdge.getDestination());

		Assert.assertFalse(edgeIter.hasNext());
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPathCost(Node, Node)}
	 * .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testComputeShortestPathCostNodeNode() {
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

		Assert.assertEquals(3, computation.computeShortestPathCost(firstNode, fourthNode).get().floatValue(), 0);
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPathCost(java.util.Set, Node)}
	 * .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testComputeShortestPathCostSetNode() {
		PathNetwork network = new PathNetwork();
		DijkstraShortestPathComputation computation = new DijkstraShortestPathComputation(network);

		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);

		network.addEdge(secondNode, firstNode, 1);
		network.addEdge(secondNode, thirdNode, 10);
		network.addEdge(thirdNode, secondNode, 10);
		network.addEdge(thirdNode, fourthNode, 2);

		Set<Node> sources = new HashSet<>();
		sources.add(secondNode);
		sources.add(thirdNode);
		Assert.assertEquals(1, computation.computeShortestPathCost(sources, firstNode).get().floatValue(), 0);
		Assert.assertEquals(2, computation.computeShortestPathCost(sources, fourthNode).get().floatValue(), 0);
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#computeShortestPathCostsReachable(Node)}
	 * .
	 */
	@SuppressWarnings("static-method")
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
		Assert.assertEquals(0, costMapping.get(firstNode).floatValue(), 0);
		Assert.assertEquals(1, costMapping.get(secondNode).floatValue(), 0);
		Assert.assertEquals(2, costMapping.get(thirdNode).floatValue(), 0);
		Assert.assertEquals(3, costMapping.get(fourthNode).floatValue(), 0);
		Assert.assertEquals(4, costMapping.get(fifthNode).floatValue(), 0);
		Assert.assertEquals(4, costMapping.get(sixthNode).floatValue(), 0);
	}

	/**
	 * Test method for
	 * {@link DijkstraShortestPathComputation#DijkstraShortestPathComputation(IPathNetwork)}
	 * .
	 */
	@SuppressWarnings("static-method")
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
	@SuppressWarnings("static-method")
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
