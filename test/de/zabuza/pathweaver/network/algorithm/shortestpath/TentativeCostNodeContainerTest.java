package de.zabuza.pathweaver.network.algorithm.shortestpath;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.algorithm.shortestpath.TentativeNodeContainer;

/**
 * Test for {@link TentativeNodeContainer}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TentativeCostNodeContainerTest {

	/**
	 * Test method for
	 * {@link TentativeNodeContainer#compareTo(TentativeNodeContainer)}.
	 */
	@Test
	public void testCompareTo() {
		Node node = new Node(0);
		float cost = 1;
		float anotherCost = 2;
		float estCost = 3;
		float anotherEstCost = 1;

		TentativeNodeContainer container = new TentativeNodeContainer(node, null, cost);
		TentativeNodeContainer anotherContainer = new TentativeNodeContainer(node, null, anotherCost);
		TentativeNodeContainer yetAnotherContainer = new TentativeNodeContainer(node, null, cost);
		Assert.assertEquals(0, container.compareTo(yetAnotherContainer), 0);
		Assert.assertTrue(container.compareTo(anotherContainer) < 0);
		Assert.assertTrue(anotherContainer.compareTo(container) > 0);

		container = new TentativeNodeContainer(node, null, 0, estCost);
		anotherContainer = new TentativeNodeContainer(node, null, 0, anotherEstCost);
		yetAnotherContainer = new TentativeNodeContainer(node, null, 0, estCost);
		Assert.assertEquals(0, container.compareTo(yetAnotherContainer), 0);
		Assert.assertTrue(container.compareTo(anotherContainer) > 0);
		Assert.assertTrue(anotherContainer.compareTo(container) < 0);

		container = new TentativeNodeContainer(node, null, cost, estCost);
		anotherContainer = new TentativeNodeContainer(node, null, anotherCost, anotherEstCost);
		yetAnotherContainer = new TentativeNodeContainer(node, null, cost, anotherEstCost);
		Assert.assertTrue(container.compareTo(yetAnotherContainer) > 0);
		Assert.assertTrue(container.compareTo(anotherContainer) > 0);
		Assert.assertTrue(anotherContainer.compareTo(container) < 0);
		Assert.assertTrue(anotherContainer.compareTo(yetAnotherContainer) > 0);
	}

	/**
	 * Test method for {@link TentativeNodeContainer#getEstCostToDest()} .
	 */
	@Test
	public void testGetEstCostToDest() {
		Node src = new Node(0);
		Node dest = new Node(1);
		float cost = 1;
		float estCostToDest = 2;
		float anotherEstCostToDest = 2;
		DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		TentativeNodeContainer container = new TentativeNodeContainer(src, edge, cost, estCostToDest);
		TentativeNodeContainer anotherContainer = new TentativeNodeContainer(src, edge, cost, anotherEstCostToDest);

		Assert.assertEquals(estCostToDest, container.getEstCostToDest(), 0);
		Assert.assertEquals(anotherEstCostToDest, anotherContainer.getEstCostToDest(), 0);
	}

	/**
	 * Test method for {@link TentativeNodeContainer#getNode()}.
	 */
	@Test
	public void testGetNode() {
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		float cost = 1;
		TentativeNodeContainer container = new TentativeNodeContainer(node, null, cost);
		TentativeNodeContainer anotherContainer = new TentativeNodeContainer(anotherNode, null, cost);
		Assert.assertEquals(node, container.getNode());
		Assert.assertEquals(anotherNode, anotherContainer.getNode());
	}

	/**
	 * Test method for {@link TentativeNodeContainer#getParentEdge()} .
	 */
	@Test
	public void testGetParentEdge() {
		Node src = new Node(0);
		Node dest = new Node(1);
		float cost = 1;
		float estCostToDest = 2;
		DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		DirectedWeightedEdge anotherEdge = new DirectedWeightedEdge(src, dest, cost);
		TentativeNodeContainer container = new TentativeNodeContainer(src, edge, cost, estCostToDest);
		TentativeNodeContainer anotherContainer = new TentativeNodeContainer(src, anotherEdge, cost, estCostToDest);

		Assert.assertEquals(edge, container.getParentEdge());
		Assert.assertEquals(anotherEdge, anotherContainer.getParentEdge());
	}

	/**
	 * Test method for {@link TentativeNodeContainer#getTentativeCost()}.
	 */
	@Test
	public void testGetTentativeCost() {
		Node node = new Node(0);
		float cost = 1;
		float anotherCost = 2;
		TentativeNodeContainer container = new TentativeNodeContainer(node, null, cost);
		TentativeNodeContainer anotherContainer = new TentativeNodeContainer(node, null, anotherCost);
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
		Assert.assertEquals(anotherCost, anotherContainer.getTentativeCost(), 0);
	}

	/**
	 * Test method for
	 * {@link TentativeNodeContainer#TentativeCostNodeContainer(Node, float)} .
	 */
	@Test
	public void testTentativeCostNodeContainer() {
		Node src = new Node(0);
		Node dest = new Node(1);
		float cost = 1;
		float estCostToDest = 2;
		DirectedWeightedEdge edge = new DirectedWeightedEdge(src, dest, cost);
		TentativeNodeContainer container = new TentativeNodeContainer(src, edge, cost, estCostToDest);
		Assert.assertEquals(src, container.getNode());
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
		Assert.assertEquals(estCostToDest, container.getEstCostToDest(), 0);
		Assert.assertEquals(edge, container.getParentEdge());
	}
}
