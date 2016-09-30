package de.zabuza.pathweaver.network.algorithm;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.OutgoingEdge;

/**
 * Test for {@link TentativeNodeContainer}.
 * 
 * @author Zabuza
 *
 */
public class TentativeCostNodeContainerTest {

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
		Assert.assertTrue(container.compareTo(anotherContainer) < 0);
		Assert.assertTrue(anotherContainer.compareTo(container) > 0);

		container = new TentativeNodeContainer(node, null, cost, estCost);
		anotherContainer = new TentativeNodeContainer(node, null, anotherCost, anotherEstCost);
		yetAnotherContainer = new TentativeNodeContainer(node, null, cost, anotherEstCost);
		Assert.assertTrue(container.compareTo(yetAnotherContainer) > 0);
		Assert.assertTrue(container.compareTo(anotherContainer) > 0);
		Assert.assertTrue(anotherContainer.compareTo(container) < 0);
		Assert.assertTrue(anotherContainer.compareTo(yetAnotherContainer) > 0);
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
		Node node = new Node(0);
		float cost = 1;
		float estCostToDest = 2;
		OutgoingEdge edge = new OutgoingEdge(node, cost);
		TentativeNodeContainer container = new TentativeNodeContainer(node, edge, cost, estCostToDest);
		Assert.assertEquals(node, container.getNode());
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
		Assert.assertEquals(estCostToDest, container.getEstCostToDest(), 0);
		Assert.assertEquals(edge, container.getParentEdge());
	}

}
