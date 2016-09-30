package de.zabuza.pathweaver.network;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link PathNetwork}.
 * 
 * @author Zabuza
 *
 */
public final class PathNetworkTest {

	/**
	 * Test method for {@link PathNetwork#addEdge(Node, Node, int)}.
	 */
	@Test
	public void testAddEdge() {
		PathNetwork network = new PathNetwork();
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		network.addNode(node);
		network.addNode(anotherNode);

		network.addEdge(node, anotherNode, 1);
		Assert.assertEquals(1, network.getAmountOfEdges());
		network.addEdge(anotherNode, node, 2);
		Assert.assertEquals(2, network.getAmountOfEdges());

		network.addEdge(node, anotherNode, 3);
		Assert.assertEquals(3, network.getAmountOfEdges());
		network.addEdge(node, anotherNode, 3);
		Assert.assertEquals(4, network.getAmountOfEdges());
	}

	/**
	 * Test method for {@link PathNetwork#addNode(Node)}.
	 */
	@Test
	public void testAddNode() {
		PathNetwork network = new PathNetwork();
		Node node = new Node(0);
		Node anotherNode = new Node(1);

		Assert.assertFalse(network.containsNodeId(0));
		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));

		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
	}

	/**
	 * Test method for {@link PathNetwork#containsNodeId(Node)}.
	 */
	@Test
	public void testContainsNode() {
		PathNetwork network = new PathNetwork();
		Node node = new Node(0);
		Node anotherNode = new Node(1);

		Assert.assertFalse(network.containsNodeId(0));
		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));
	}

	/**
	 * Test method for {@link PathNetwork#containsNodeId(int)}.
	 */
	@Test
	public void testContainsNodeId() {
		PathNetwork network = new PathNetwork();
		Node node = new Node(0);
		Node anotherNode = new Node(1);

		Assert.assertFalse(network.containsNodeId(0));
		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));

		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));
	}

	/**
	 * Test method for {@link PathNetwork#getAmountOfEdges()}.
	 */
	@Test
	public void testGetAmountOfEdges() {
		PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getAmountOfEdges());
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		network.addNode(node);
		network.addNode(anotherNode);

		network.addEdge(node, anotherNode, 1);
		Assert.assertEquals(1, network.getAmountOfEdges());
		network.addEdge(anotherNode, node, 2);
		Assert.assertEquals(2, network.getAmountOfEdges());

		network.addEdge(node, anotherNode, 3);
		Assert.assertEquals(3, network.getAmountOfEdges());
		network.addEdge(node, anotherNode, 3);
		Assert.assertEquals(4, network.getAmountOfEdges());
	}

	/**
	 * Test method for {@link PathNetwork#getAmountOfNodes()}.
	 */
	@Test
	public void testGetAmountOfNodes() {
		PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getAmountOfNodes());
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		network.addNode(node);
		Assert.assertEquals(1, network.getAmountOfNodes());
		network.addNode(anotherNode);
		Assert.assertEquals(2, network.getAmountOfNodes());

		network.addNode(node);
		Assert.assertEquals(2, network.getAmountOfNodes());
	}

	/**
	 * Test method for {@link PathNetwork#getIncomingEdges(Node)}.
	 */
	@Test
	public void testGetIncomingEdges() {
		PathNetwork network = new PathNetwork();
		Node source = new Node(0);
		Node destination = new Node(1);
		int cost = 1;
		int anotherCost = 2;
		Set<DirectedWeightedEdge> emptySet = Collections.emptySet();

		Assert.assertEquals(emptySet, network.getOutgoingEdges(destination));
		network.addNode(source);
		network.addNode(destination);
		Assert.assertEquals(emptySet, network.getOutgoingEdges(destination));

		network.addEdge(source, destination, cost);
		Set<DirectedWeightedEdge> firstEdgeSet = network.getIncomingEdges(destination);
		Assert.assertEquals(1, firstEdgeSet.size());
		DirectedWeightedEdge edge = firstEdgeSet.iterator().next();
		Assert.assertEquals(source, edge.getSource());
		Assert.assertEquals(cost, edge.getCost(), 0);

		network.addEdge(destination, source, anotherCost);
		Set<DirectedWeightedEdge> secondEdgeSet = network.getIncomingEdges(source);
		Assert.assertEquals(1, secondEdgeSet.size());
		DirectedWeightedEdge secondEdge = secondEdgeSet.iterator().next();
		Assert.assertEquals(destination, secondEdge.getSource());
		Assert.assertEquals(anotherCost, secondEdge.getCost(), 0);

		network.addEdge(source, destination, anotherCost);
		Set<DirectedWeightedEdge> thirdEdgeSet = network.getIncomingEdges(destination);
		Assert.assertEquals(2, thirdEdgeSet.size());
		Iterator<DirectedWeightedEdge> iter = thirdEdgeSet.iterator();
		DirectedWeightedEdge thirdEdge = iter.next();
		Assert.assertEquals(source, thirdEdge.getSource());
		Assert.assertEquals(cost, thirdEdge.getCost(), 0);
		DirectedWeightedEdge fourthEdge = iter.next();
		Assert.assertEquals(source, fourthEdge.getSource());
		Assert.assertEquals(anotherCost, fourthEdge.getCost(), 0);
	}

	/**
	 * Test method for {@link PathNetwork#getNodeById(int)}.
	 */
	@Test
	public void testGetNodeById() {
		PathNetwork network = new PathNetwork();
		Node node = new Node(0);
		Node anotherNode = new Node(1);

		Assert.assertNull(network.getNodeById(0));
		network.addNode(node);
		Assert.assertEquals(node, network.getNodeById(0));
		network.addNode(anotherNode);
		Assert.assertEquals(anotherNode, network.getNodeById(1));

		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));
	}

	/**
	 * Test method for {@link PathNetwork#getOutgoingEdges(Node)}.
	 */
	@Test
	public void testGetOutgoingEdges() {
		PathNetwork network = new PathNetwork();
		Node source = new Node(0);
		Node destination = new Node(1);
		int cost = 1;
		int anotherCost = 2;
		Set<DirectedWeightedEdge> emptySet = Collections.emptySet();

		Assert.assertEquals(emptySet, network.getOutgoingEdges(source));
		network.addNode(source);
		network.addNode(destination);
		Assert.assertEquals(emptySet, network.getOutgoingEdges(source));

		network.addEdge(source, destination, cost);
		Set<DirectedWeightedEdge> firstEdgeSet = network.getOutgoingEdges(source);
		Assert.assertEquals(1, firstEdgeSet.size());
		DirectedWeightedEdge edge = firstEdgeSet.iterator().next();
		Assert.assertEquals(destination, edge.getDestination());
		Assert.assertEquals(cost, edge.getCost(), 0);

		network.addEdge(destination, source, anotherCost);
		Set<DirectedWeightedEdge> secondEdgeSet = network.getOutgoingEdges(destination);
		Assert.assertEquals(1, secondEdgeSet.size());
		DirectedWeightedEdge secondEdge = secondEdgeSet.iterator().next();
		Assert.assertEquals(source, secondEdge.getDestination());
		Assert.assertEquals(anotherCost, secondEdge.getCost(), 0);

		network.addEdge(source, destination, anotherCost);
		Set<DirectedWeightedEdge> thirdEdgeSet = network.getOutgoingEdges(source);
		Assert.assertEquals(2, thirdEdgeSet.size());
		Iterator<DirectedWeightedEdge> iter = thirdEdgeSet.iterator();
		DirectedWeightedEdge thirdEdge = iter.next();
		Assert.assertEquals(destination, thirdEdge.getDestination());
		Assert.assertEquals(cost, thirdEdge.getCost(), 0);
		DirectedWeightedEdge fourthEdge = iter.next();
		Assert.assertEquals(destination, fourthEdge.getDestination());
		Assert.assertEquals(anotherCost, fourthEdge.getCost(), 0);
	}

	/**
	 * Test method for {@link PathNetwork#hasIncomingEdge(Node, IncomingEdge)}.
	 */
	@Test
	public void testHasIncomingEdge() {
		PathNetwork network = new PathNetwork();
		Node src = new Node(0);
		Node dest = new Node(1);
		Node anotherSrc = new Node(2);
		int cost = 1;
		int anotherCost = 2;

		Assert.assertFalse(network.hasIncomingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addNode(src);
		network.addNode(anotherSrc);
		Assert.assertFalse(network.hasIncomingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addEdge(src, anotherSrc, cost);
		network.addEdge(anotherSrc, src, anotherCost);

		DirectedWeightedEdge firstEdge = network.getIncomingEdges(anotherSrc).iterator().next();
		DirectedWeightedEdge secondEdge = network.getIncomingEdges(src).iterator().next();
		Assert.assertTrue(network.hasIncomingEdge(anotherSrc, firstEdge));
		Assert.assertTrue(network.hasIncomingEdge(src, secondEdge));
		Assert.assertFalse(network.hasIncomingEdge(anotherSrc, secondEdge));
		Assert.assertFalse(network.hasIncomingEdge(src, firstEdge));
	}

	/**
	 * Test method for {@link PathNetwork#hasOutgoingEdge(Node, OutgoingEdge)}.
	 */
	@Test
	public void testHasOutgoingEdge() {
		PathNetwork network = new PathNetwork();
		Node src = new Node(0);
		Node dest = new Node(1);
		Node anotherSrc = new Node(2);
		int cost = 1;
		int anotherCost = 2;

		Assert.assertFalse(network.hasOutgoingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addNode(src);
		network.addNode(anotherSrc);
		Assert.assertFalse(network.hasOutgoingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addEdge(src, anotherSrc, cost);
		network.addEdge(anotherSrc, src, anotherCost);

		DirectedWeightedEdge firstEdge = network.getOutgoingEdges(src).iterator().next();
		DirectedWeightedEdge secondEdge = network.getOutgoingEdges(anotherSrc).iterator().next();
		Assert.assertTrue(network.hasOutgoingEdge(src, firstEdge));
		Assert.assertTrue(network.hasOutgoingEdge(anotherSrc, secondEdge));
		Assert.assertFalse(network.hasOutgoingEdge(src, secondEdge));
		Assert.assertFalse(network.hasOutgoingEdge(anotherSrc, firstEdge));
	}

	/**
	 * Test method for {@link PathNetwork#PathNetwork()}.
	 */
	@Test
	public void testPathNetwork() {
		PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getAmountOfNodes());
		Assert.assertEquals(0, network.getAmountOfEdges());
	}

	/**
	 * Test method for {@link PathNetwork#reduceToLargestScc()}.
	 */
	@Test
	public void testReduceToLargestScc() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link PathNetwork#toString()}.
	 */
	@Test
	public void testToString() {
		PathNetwork network = new PathNetwork();
		Assert.assertEquals("Network[#nodes=0,#edges=0,edges={}]", network.toString());
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		int cost = 1;
		int anotherCost = 2;

		network.addNode(node);
		network.addNode(anotherNode);
		Assert.assertEquals("Network[#nodes=2,#edges=0,edges={}]", network.toString());

		network.addEdge(node, anotherNode, cost);
		network.addEdge(node, anotherNode, anotherCost);
		network.addEdge(anotherNode, node, cost);
		String networkAsText = network.toString();
		Assert.assertTrue(networkAsText.startsWith("Network[#nodes=2,#edges=3,edges={"));
		Assert.assertTrue(networkAsText.endsWith("}]"));
	}
}
