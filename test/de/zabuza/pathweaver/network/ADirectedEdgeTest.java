package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link ADirectedEdge}.
 * 
 * @author Zabuza
 *
 */
public final class ADirectedEdgeTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link ADirectedEdge#ADirectedEdge(Node, int)} .
	 */
	@Test
	public void testADirectedEdge() {
		Node node = new Node(0);
		int cost = 5;
		ADirectedEdge edge = new IncomingEdge(node, cost);
		Assert.assertEquals(node, edge.getTarget());
		Assert.assertEquals(cost, edge.getCost());

		Node anotherNode = new Node(1);
		int anotherCost = 2;
		ADirectedEdge anotherEdge = new IncomingEdge(anotherNode, anotherCost);
		Assert.assertEquals(anotherNode, anotherEdge.getTarget());
		Assert.assertEquals(anotherCost, anotherEdge.getCost());
	}

	/**
	 * Test method for {@link ADirectedEdge#equals(Object)} .
	 */
	@Test
	public void testEqualsObject() {
		Node node = new Node(0);
		int cost = 5;
		Node differentNode = new Node(1);
		int differentCost = 2;

		ADirectedEdge edge = new IncomingEdge(node, cost);
		ADirectedEdge differentNodeEdge = new IncomingEdge(differentNode, cost);
		ADirectedEdge differentCostEdge = new IncomingEdge(node, differentCost);
		ADirectedEdge totallyDifferentEdge = new IncomingEdge(differentNode, differentCost);

		ADirectedEdge similarEdge = new IncomingEdge(node, cost);

		Assert.assertEquals(edge, edge);
		Assert.assertNotEquals(similarEdge, edge);
		Assert.assertNotEquals(differentNodeEdge, edge);
		Assert.assertNotEquals(differentCostEdge, edge);
		Assert.assertNotEquals(totallyDifferentEdge, edge);
	}

	/**
	 * Test method for {@link ADirectedEdge#getCost()}.
	 */
	@Test
	public void testGetCost() {
		Node node = new Node(0);
		int cost = 5;
		int differentCost = 2;
		ADirectedEdge edge = new IncomingEdge(node, cost);
		ADirectedEdge anotherEdge = new IncomingEdge(node, differentCost);

		Assert.assertEquals(cost, edge.getCost());
		Assert.assertEquals(differentCost, anotherEdge.getCost());

		// Test cost which are out of the bounds, i.e. not greater than zero
		int zeroCost = 0;
		int negativeCost = -5;
		exception.expect(IllegalArgumentException.class);
		new IncomingEdge(node, zeroCost);
		new IncomingEdge(node, negativeCost);
	}

	/**
	 * Test method for {@link ADirectedEdge#getTarget()}.
	 */
	@Test
	public void testGetTarget() {
		Node node = new Node(0);
		Node differentNode = new Node(1);
		int cost = 5;
		ADirectedEdge edge = new IncomingEdge(node, cost);
		ADirectedEdge anotherEdge = new IncomingEdge(differentNode, cost);

		Assert.assertEquals(node, edge.getTarget());
		Assert.assertEquals(differentNode, anotherEdge.getTarget());
	}

	/**
	 * Test method for {@link ADirectedEdge#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Node node = new Node(0);
		int cost = 5;
		Node differentNode = new Node(1);
		int differentCost = 2;

		ADirectedEdge edge = new IncomingEdge(node, cost);
		ADirectedEdge differentNodeEdge = new IncomingEdge(differentNode, cost);
		ADirectedEdge differentCostEdge = new IncomingEdge(node, differentCost);
		ADirectedEdge totallyDifferentEdge = new IncomingEdge(differentNode, differentCost);

		ADirectedEdge similarEdge = new IncomingEdge(node, cost);

		Assert.assertEquals(edge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(similarEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(differentNodeEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(differentCostEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(totallyDifferentEdge.hashCode(), edge.hashCode());
	}
}
