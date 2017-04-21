package de.zabuza.pathweaver.network;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link PathNetwork}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class PathNetworkTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link PathNetwork#addEdge(Node, Node, float)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAddEdge() {
		final PathNetwork network = new PathNetwork();
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);
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
	@SuppressWarnings("static-method")
	@Test
	public void testAddNode() {
		final PathNetwork network = new PathNetwork();
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);

		Assert.assertFalse(network.containsNodeId(0));
		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));

		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
	}

	/**
	 * Test method for {@link PathNetwork#containsNodeId(int)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testContainsNode() {
		final PathNetwork network = new PathNetwork();
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);

		Assert.assertFalse(network.containsNodeId(0));
		network.addNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));
	}

	/**
	 * Test method for {@link PathNetwork#containsNodeId(int)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testContainsNodeId() {
		final PathNetwork network = new PathNetwork();
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);

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
	@SuppressWarnings("static-method")
	@Test
	public void testGetAmountOfEdges() {
		final PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getAmountOfEdges());
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);
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
	 * Test method for {@link PathNetwork#getIncomingEdges(Node)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetIncomingEdges() {
		final PathNetwork network = new PathNetwork();
		final Node source = new Node(0);
		final Node destination = new Node(1);
		final int cost = 1;
		final int anotherCost = 2;
		final Set<DirectedWeightedEdge> emptySet = Collections.emptySet();

		Assert.assertEquals(emptySet, network.getOutgoingEdges(destination));
		network.addNode(source);
		network.addNode(destination);
		Assert.assertEquals(emptySet, network.getOutgoingEdges(destination));

		network.addEdge(source, destination, cost);
		final Set<DirectedWeightedEdge> firstEdgeSet = network.getIncomingEdges(destination);
		Assert.assertEquals(1, firstEdgeSet.size());
		final DirectedWeightedEdge edge = firstEdgeSet.iterator().next();
		Assert.assertEquals(source, edge.getSource());
		Assert.assertEquals(cost, edge.getCost(), 0);

		network.addEdge(destination, source, anotherCost);
		final Set<DirectedWeightedEdge> secondEdgeSet = network.getIncomingEdges(source);
		Assert.assertEquals(1, secondEdgeSet.size());
		final DirectedWeightedEdge secondEdge = secondEdgeSet.iterator().next();
		Assert.assertEquals(destination, secondEdge.getSource());
		Assert.assertEquals(anotherCost, secondEdge.getCost(), 0);

		network.addEdge(source, destination, anotherCost);
		final Set<DirectedWeightedEdge> thirdEdgeSet = network.getIncomingEdges(destination);
		Assert.assertEquals(2, thirdEdgeSet.size());
		final Iterator<DirectedWeightedEdge> iter = thirdEdgeSet.iterator();
		final DirectedWeightedEdge thirdEdge = iter.next();
		Assert.assertEquals(source, thirdEdge.getSource());
		Assert.assertEquals(cost, thirdEdge.getCost(), 0);
		final DirectedWeightedEdge fourthEdge = iter.next();
		Assert.assertEquals(source, fourthEdge.getSource());
		Assert.assertEquals(anotherCost, fourthEdge.getCost(), 0);
	}

	/**
	 * Test method for {@link PathNetwork#getNodeById(int)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetNodeById() {
		final PathNetwork network = new PathNetwork();
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);

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
	@SuppressWarnings("static-method")
	@Test
	public void testGetOutgoingEdges() {
		final PathNetwork network = new PathNetwork();
		final Node source = new Node(0);
		final Node destination = new Node(1);
		final int cost = 1;
		final int anotherCost = 2;
		final Set<DirectedWeightedEdge> emptySet = Collections.emptySet();

		Assert.assertEquals(emptySet, network.getOutgoingEdges(source));
		network.addNode(source);
		network.addNode(destination);
		Assert.assertEquals(emptySet, network.getOutgoingEdges(source));

		network.addEdge(source, destination, cost);
		final Set<DirectedWeightedEdge> firstEdgeSet = network.getOutgoingEdges(source);
		Assert.assertEquals(1, firstEdgeSet.size());
		final DirectedWeightedEdge edge = firstEdgeSet.iterator().next();
		Assert.assertEquals(destination, edge.getDestination());
		Assert.assertEquals(cost, edge.getCost(), 0);

		network.addEdge(destination, source, anotherCost);
		final Set<DirectedWeightedEdge> secondEdgeSet = network.getOutgoingEdges(destination);
		Assert.assertEquals(1, secondEdgeSet.size());
		final DirectedWeightedEdge secondEdge = secondEdgeSet.iterator().next();
		Assert.assertEquals(source, secondEdge.getDestination());
		Assert.assertEquals(anotherCost, secondEdge.getCost(), 0);

		network.addEdge(source, destination, anotherCost);
		final Set<DirectedWeightedEdge> thirdEdgeSet = network.getOutgoingEdges(source);
		Assert.assertEquals(2, thirdEdgeSet.size());
		final Iterator<DirectedWeightedEdge> iter = thirdEdgeSet.iterator();
		final DirectedWeightedEdge thirdEdge = iter.next();
		Assert.assertEquals(destination, thirdEdge.getDestination());
		Assert.assertEquals(cost, thirdEdge.getCost(), 0);
		final DirectedWeightedEdge fourthEdge = iter.next();
		Assert.assertEquals(destination, fourthEdge.getDestination());
		Assert.assertEquals(anotherCost, fourthEdge.getCost(), 0);
	}

	/**
	 * Test method for {@link PathNetwork#getSize()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSize() {
		final PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getSize());
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);
		network.addNode(node);
		Assert.assertEquals(1, network.getSize());
		network.addNode(anotherNode);
		Assert.assertEquals(2, network.getSize());

		network.addNode(node);
		Assert.assertEquals(2, network.getSize());
	}

	/**
	 * Test method for
	 * {@link PathNetwork#hasIncomingEdge(Node, DirectedWeightedEdge)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHasIncomingEdge() {
		final PathNetwork network = new PathNetwork();
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final Node anotherSrc = new Node(2);
		final int cost = 1;
		final int anotherCost = 2;

		Assert.assertFalse(network.hasIncomingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addNode(src);
		network.addNode(anotherSrc);
		Assert.assertFalse(network.hasIncomingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addEdge(src, anotherSrc, cost);
		network.addEdge(anotherSrc, src, anotherCost);

		final DirectedWeightedEdge firstEdge = network.getIncomingEdges(anotherSrc).iterator().next();
		final DirectedWeightedEdge secondEdge = network.getIncomingEdges(src).iterator().next();
		Assert.assertTrue(network.hasIncomingEdge(anotherSrc, firstEdge));
		Assert.assertTrue(network.hasIncomingEdge(src, secondEdge));
		Assert.assertFalse(network.hasIncomingEdge(anotherSrc, secondEdge));
		Assert.assertFalse(network.hasIncomingEdge(src, firstEdge));
	}

	/**
	 * Test method for
	 * {@link PathNetwork#hasOutgoingEdge(Node, DirectedWeightedEdge)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHasOutgoingEdge() {
		final PathNetwork network = new PathNetwork();
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final Node anotherSrc = new Node(2);
		final int cost = 1;
		final int anotherCost = 2;

		Assert.assertFalse(network.hasOutgoingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addNode(src);
		network.addNode(anotherSrc);
		Assert.assertFalse(network.hasOutgoingEdge(src, new DirectedWeightedEdge(anotherSrc, dest, cost)));
		network.addEdge(src, anotherSrc, cost);
		network.addEdge(anotherSrc, src, anotherCost);

		final DirectedWeightedEdge firstEdge = network.getOutgoingEdges(src).iterator().next();
		final DirectedWeightedEdge secondEdge = network.getOutgoingEdges(anotherSrc).iterator().next();
		Assert.assertTrue(network.hasOutgoingEdge(src, firstEdge));
		Assert.assertTrue(network.hasOutgoingEdge(anotherSrc, secondEdge));
		Assert.assertFalse(network.hasOutgoingEdge(src, secondEdge));
		Assert.assertFalse(network.hasOutgoingEdge(anotherSrc, firstEdge));
	}

	/**
	 * Test method for {@link PathNetwork#PathNetwork()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testPathNetwork() {
		final PathNetwork network = new PathNetwork();
		Assert.assertEquals(0, network.getSize());
		Assert.assertEquals(0, network.getAmountOfEdges());
	}

	/**
	 * Test method for {@link PathNetwork#reduceToLargestScc()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReduceToLargestScc() {
		final PathNetwork network = new PathNetwork();

		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);
		final Node fifthNode = new Node(4);
		final Node sixthNode = new Node(5);

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

		final Node seventhNode = new Node(6);
		final Node eighthNode = new Node(7);
		final Node ninthNode = new Node(8);
		final Node tenthNode = new Node(9);

		network.addNode(seventhNode);
		network.addNode(eighthNode);
		network.addNode(ninthNode);
		network.addNode(tenthNode);

		network.addEdge(fourthNode, seventhNode, 1);
		network.addEdge(seventhNode, eighthNode, 1);
		network.addEdge(eighthNode, ninthNode, 1);
		network.addEdge(ninthNode, seventhNode, 1);
		network.addEdge(tenthNode, eighthNode, 1);

		network.reduceToLargestScc();
		Assert.assertEquals(6, network.getSize());
		Assert.assertEquals(18, network.getAmountOfEdges());
		Assert.assertTrue(network.containsNodeId(firstNode.getId()));
		Assert.assertTrue(network.containsNodeId(secondNode.getId()));
		Assert.assertTrue(network.containsNodeId(thirdNode.getId()));
		Assert.assertTrue(network.containsNodeId(fourthNode.getId()));
		Assert.assertTrue(network.containsNodeId(fifthNode.getId()));
		Assert.assertTrue(network.containsNodeId(sixthNode.getId()));

		Assert.assertFalse(network.containsNodeId(seventhNode.getId()));
		Assert.assertFalse(network.containsNodeId(eighthNode.getId()));
		Assert.assertFalse(network.containsNodeId(ninthNode.getId()));
		Assert.assertFalse(network.containsNodeId(tenthNode.getId()));
	}

	/**
	 * Test method for {@link PathNetwork#removeNode(Node)}.
	 */
	@Test
	public void testRemoveNode() {
		final PathNetwork network = new PathNetwork();

		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);
		final Node fifthNode = new Node(4);
		final Node sixthNode = new Node(5);

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

		Assert.assertEquals(6, network.getSize());
		Assert.assertEquals(18, network.getAmountOfEdges());

		network.removeNode(fifthNode);
		Assert.assertEquals(5, network.getSize());
		Assert.assertEquals(12, network.getAmountOfEdges());

		network.removeNode(fourthNode);
		Assert.assertEquals(4, network.getSize());
		Assert.assertEquals(6, network.getAmountOfEdges());

		network.removeNode(sixthNode);
		Assert.assertEquals(3, network.getSize());
		Assert.assertEquals(6, network.getAmountOfEdges());

		network.removeNode(secondNode);
		Assert.assertEquals(2, network.getSize());
		Assert.assertEquals(2, network.getAmountOfEdges());

		network.removeNode(thirdNode);
		Assert.assertEquals(1, network.getSize());
		Assert.assertEquals(0, network.getAmountOfEdges());

		network.removeNode(firstNode);
		Assert.assertEquals(0, network.getSize());
		Assert.assertEquals(0, network.getAmountOfEdges());

		this.exception.expect(NoSuchElementException.class);
		network.removeNode(fifthNode);
	}

	/**
	 * Test method for {@link PathNetwork#reverse()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testReverse() {
		final PathNetwork network = new PathNetwork();
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final int cost = 1;

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addEdge(firstNode, firstNode, cost);
		network.addEdge(firstNode, secondNode, cost);

		network.reverse();

		final DirectedWeightedEdge firstEdge = network.getOutgoingEdges(firstNode).iterator().next();
		final DirectedWeightedEdge secondEdge = network.getOutgoingEdges(secondNode).iterator().next();

		Assert.assertEquals(firstNode, firstEdge.getSource());
		Assert.assertEquals(firstNode, firstEdge.getDestination());
		Assert.assertEquals(secondNode, secondEdge.getSource());
		Assert.assertEquals(firstNode, secondEdge.getDestination());
	}

	/**
	 * Test method for {@link PathNetwork#toString()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testToString() {
		final PathNetwork network = new PathNetwork();
		Assert.assertEquals("Network[#nodes=0,#edges=0,edges={}]", network.toString());
		final Node node = new Node(0);
		final Node anotherNode = new Node(1);
		final int cost = 1;
		final int anotherCost = 2;

		network.addNode(node);
		network.addNode(anotherNode);
		Assert.assertEquals("Network[#nodes=2,#edges=0,edges={}]", network.toString());

		network.addEdge(node, anotherNode, cost);
		network.addEdge(node, anotherNode, anotherCost);
		network.addEdge(anotherNode, node, cost);
		final String networkAsText = network.toString();
		Assert.assertTrue(networkAsText.startsWith("Network[#nodes=2,#edges=3,edges={"));
		Assert.assertTrue(networkAsText.endsWith("}]"));
	}
}
