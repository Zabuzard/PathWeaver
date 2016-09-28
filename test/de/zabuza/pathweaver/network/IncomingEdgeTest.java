package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link IncomingEdge}.
 * 
 * @author Zabuza
 *
 */
public final class IncomingEdgeTest {

	/**
	 * Test method for {@link IncomingEdge#getSource()}.
	 */
	@Test
	public void testGetSource() {
		Node node = new Node(0);
		Node differentNode = new Node(1);
		int cost = 5;
		IncomingEdge edge = new IncomingEdge(node, cost);
		IncomingEdge anotherEdge = new IncomingEdge(differentNode, cost);

		Assert.assertEquals(node, edge.getSource());
		Assert.assertEquals(differentNode, anotherEdge.getSource());
	}

	/**
	 * Test method for {@link IncomingEdge#IncomingEdge(Node, int)} .
	 */
	@Test
	public void testIncomingEdge() {
		Node node = new Node(0);
		int cost = 5;
		IncomingEdge edge = new IncomingEdge(node, cost);
		Assert.assertEquals(node, edge.getSource());
		Assert.assertEquals(cost, edge.getCost(), 0);

		Node anotherNode = new Node(1);
		int anotherCost = 2;
		IncomingEdge anotherEdge = new IncomingEdge(anotherNode, anotherCost);
		Assert.assertEquals(anotherNode, anotherEdge.getSource());
		Assert.assertEquals(anotherCost, anotherEdge.getCost(), 0);
	}

}
