package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Node}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class NodeTest {

	/**
	 * Test method for {@link Node#equals(Object)}.
	 */
	@SuppressWarnings("static-method")
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
	@SuppressWarnings("static-method")
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
	@SuppressWarnings("static-method")
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
	@SuppressWarnings("static-method")
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
	@SuppressWarnings("static-method")
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
