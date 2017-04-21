package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link DirectedWeightedEdge}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class DirectedEdgeTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for
	 * {@link DirectedWeightedEdge#DirectedWeightedEdge(Node, Node, float)} .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDirectedEdge() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final int cost = 5;
		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		Assert.assertEquals(src, edge.getSource());
		Assert.assertEquals(dest, edge.getDestination());
		Assert.assertEquals(cost, edge.getCost(), 0);

		final Node anotherSource = new Node(2);
		final int anotherCost = 2;
		final DirectedWeightedEdge anotherEdge = new DirectedWeightedEdge(anotherSource, dest, anotherCost);
		Assert.assertEquals(anotherSource, anotherEdge.getSource());
		Assert.assertEquals(dest, edge.getDestination());
		Assert.assertEquals(anotherCost, anotherEdge.getCost(), 0);
	}

	/**
	 * Test method for {@link DirectedWeightedEdge#equals(Object)} .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsObject() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final int cost = 5;
		final Node differentSrc = new Node(2);
		final int differentCost = 2;

		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		final DirectedWeightedEdge differentNodeEdge = new DirectedWeightedEdge(differentSrc, dest, cost);
		final DirectedWeightedEdge differentCostEdge = new DirectedWeightedEdge(src, dest, differentCost);
		final DirectedWeightedEdge totallyDifferentEdge = new DirectedWeightedEdge(differentSrc, dest, differentCost);

		final DirectedWeightedEdge similarEdge = new DirectedWeightedEdge(src, dest, cost);

		Assert.assertEquals(edge, edge);
		Assert.assertNotEquals(similarEdge, edge);
		Assert.assertNotEquals(differentNodeEdge, edge);
		Assert.assertNotEquals(differentCostEdge, edge);
		Assert.assertNotEquals(totallyDifferentEdge, edge);
	}

	/**
	 * Test method for {@link DirectedWeightedEdge#getCost()}.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetCost() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final int cost = 5;
		final int differentCost = 2;
		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		final DirectedWeightedEdge anotherEdge = new DirectedWeightedEdge(src, dest, differentCost);

		Assert.assertEquals(cost, edge.getCost(), 0);
		Assert.assertEquals(differentCost, anotherEdge.getCost(), 0);

		// Test cost which are out of the bounds, i.e. not greater than zero
		final int zeroCost = 0;
		final int negativeCost = -5;
		this.exception.expect(IllegalArgumentException.class);
		new DirectedWeightedEdge(src, dest, zeroCost);
		new DirectedWeightedEdge(src, dest, negativeCost);
	}

	/**
	 * Test method for {@link DirectedWeightedEdge#getDestination()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDestination() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final Node differentDest = new Node(2);
		final int cost = 5;
		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		final DirectedWeightedEdge anotherEdge = new DirectedWeightedEdge(src, differentDest, cost);

		Assert.assertEquals(dest, edge.getDestination());
		Assert.assertEquals(differentDest, anotherEdge.getDestination());
	}

	/**
	 * Test method for {@link DirectedWeightedEdge#getSource()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSource() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final Node differentSrc = new Node(2);
		final int cost = 5;
		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		final DirectedWeightedEdge anotherEdge = new DirectedWeightedEdge(differentSrc, dest, cost);

		Assert.assertEquals(src, edge.getSource());
		Assert.assertEquals(differentSrc, anotherEdge.getSource());
	}

	/**
	 * Test method for {@link DirectedWeightedEdge#hashCode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		final Node src = new Node(0);
		final Node dest = new Node(1);
		final int cost = 5;
		final Node differentSrc = new Node(2);
		final int differentCost = 2;

		final DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		final DirectedWeightedEdge differentNodeEdge = new DirectedWeightedEdge(differentSrc, dest, cost);
		final DirectedWeightedEdge differentCostEdge = new DirectedWeightedEdge(src, dest, differentCost);
		final DirectedWeightedEdge totallyDifferentEdge = new DirectedWeightedEdge(differentSrc, dest, differentCost);

		final DirectedWeightedEdge similarEdge = new DirectedWeightedEdge(src, dest, cost);

		Assert.assertEquals(edge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(similarEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(differentNodeEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(differentCostEdge.hashCode(), edge.hashCode());
		Assert.assertNotEquals(totallyDifferentEdge.hashCode(), edge.hashCode());
	}
}
