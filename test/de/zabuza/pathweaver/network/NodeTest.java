package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Node}.
 * 
 * @author Zabuza
 *
 */
public final class NodeTest {

	/**
	 * Test method for {@link Node#equals(Object)}.
	 */
	@Test
	public void testEqualsObject() {
		int id = 0;
		int anotherId = 1;
		Node node = new Node(id);
		Node anotherNode = new Node(anotherId);

		Assert.assertEquals(node, node);
		Assert.assertNotEquals(anotherNode, node);
	}

	/**
	 * Test method for {@link Node#getId()}.
	 */
	@Test
	public void testGetId() {
		int id = 0;
		int anotherId = 1;
		Node node = new Node(id);
		Node anotherNode = new Node(anotherId);

		Assert.assertEquals(id, node.getId());
		Assert.assertEquals(anotherId, anotherNode.getId());
	}

	/**
	 * Test method for {@link Node#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		int id = 0;
		int anotherId = 1;
		Node node = new Node(id);
		Node anotherNode = new Node(anotherId);

		Assert.assertEquals(node.hashCode(), node.hashCode());
		Assert.assertNotEquals(anotherNode.hashCode(), node.hashCode());
	}

	/**
	 * Test method for {@link Node#Node(int)}.
	 */
	@Test
	public void testNode() {
		int id = 0;
		int anotherId = 1;
		Node node = new Node(id);
		Node anotherNode = new Node(anotherId);

		Assert.assertEquals(node, node);
		Assert.assertNotEquals(anotherNode, node);
	}

	/**
	 * Test method for {@link Node#toString()}.
	 */
	@Test
	public void testToString() {
		int id = 0;
		int anotherId = 1;
		Node node = new Node(id);
		Node anotherNode = new Node(anotherId);

		Assert.assertEquals(id + "", node.toString());
		Assert.assertEquals(anotherId + "", anotherNode.toString());
	}

}
