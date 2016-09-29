package de.zabuza.pathweaver.network.algorithm;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.Node;

/**
 * Test for {@link TentativeCostNodeContainer}.
 * 
 * @author Zabuza
 *
 */
public class TentativeCostNodeContainerTest {

	/**
	 * Test method for
	 * {@link TentativeCostNodeContainer#compareTo(TentativeCostNodeContainer)}.
	 */
	@Test
	public void testCompareTo() {
		Node node = new Node(0);
		float cost = 1;
		float anotherCost = 2;
		TentativeCostNodeContainer container = new TentativeCostNodeContainer(node, cost);
		TentativeCostNodeContainer anotherContainer = new TentativeCostNodeContainer(node, anotherCost);
		TentativeCostNodeContainer yetAnotherContainer = new TentativeCostNodeContainer(node, cost);
		Assert.assertEquals(0, container.compareTo(yetAnotherContainer), 0);
		Assert.assertTrue(container.compareTo(anotherContainer) < 0);
		Assert.assertTrue(anotherContainer.compareTo(container) > 0);
	}

	/**
	 * Test method for {@link TentativeCostNodeContainer#getNode()}.
	 */
	@Test
	public void testGetNode() {
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		float cost = 1;
		TentativeCostNodeContainer container = new TentativeCostNodeContainer(node, cost);
		TentativeCostNodeContainer anotherContainer = new TentativeCostNodeContainer(anotherNode, cost);
		Assert.assertEquals(node, container.getNode());
		Assert.assertEquals(anotherNode, anotherContainer.getNode());
	}

	/**
	 * Test method for {@link TentativeCostNodeContainer#getTentativeCost()}.
	 */
	@Test
	public void testGetTentativeCost() {
		Node node = new Node(0);
		float cost = 1;
		float anotherCost = 2;
		TentativeCostNodeContainer container = new TentativeCostNodeContainer(node, cost);
		TentativeCostNodeContainer anotherContainer = new TentativeCostNodeContainer(node, anotherCost);
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
		Assert.assertEquals(anotherCost, anotherContainer.getTentativeCost(), 0);
	}

	/**
	 * Test method for
	 * {@link TentativeCostNodeContainer#setTentativeCost(float)}.
	 */
	@Test
	public void testSetTentativeCost() {
		Node node = new Node(0);
		float cost = 1;
		float anotherCost = 2;
		TentativeCostNodeContainer container = new TentativeCostNodeContainer(node, cost);
		TentativeCostNodeContainer anotherContainer = new TentativeCostNodeContainer(node, anotherCost);
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
		Assert.assertEquals(anotherCost, anotherContainer.getTentativeCost(), 0);

		container.setTentativeCost(anotherCost);
		anotherContainer.setTentativeCost(cost);
		Assert.assertEquals(anotherCost, container.getTentativeCost(), 0);
		Assert.assertEquals(cost, anotherContainer.getTentativeCost(), 0);
	}

	/**
	 * Test method for
	 * {@link TentativeCostNodeContainer#TentativeCostNodeContainer(Node, float)}
	 * .
	 */
	@Test
	public void testTentativeCostNodeContainer() {
		Node node = new Node(0);
		float cost = 1;
		TentativeCostNodeContainer container = new TentativeCostNodeContainer(node, cost);
		Assert.assertEquals(node, container.getNode());
		Assert.assertEquals(cost, container.getTentativeCost(), 0);
	}

}
