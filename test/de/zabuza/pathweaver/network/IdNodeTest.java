package de.zabuza.pathweaver.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link IdNode}.
 * 
 * @author Zabuza
 *
 */
public final class IdNodeTest {

	/**
	 * Test method for {@link IdNode#equals(Object)}.
	 */
	@Test
	public void testEqualsObject() {
		int id = 0;
		int anotherId = 1;
		IdNode node = new IdNode(id);
		IdNode anotherNode = new IdNode(anotherId);

		Assert.assertEquals(node, node);
		Assert.assertNotEquals(anotherNode, node);
	}

	/**
	 * Test method for {@link IdNode#getId()}.
	 */
	@Test
	public void testGetId() {
		int id = 0;
		int anotherId = 1;
		IdNode node = new IdNode(id);
		IdNode anotherNode = new IdNode(anotherId);

		Assert.assertEquals(id, node.getId());
		Assert.assertEquals(anotherId, anotherNode.getId());
	}

	/**
	 * Test method for {@link IdNode#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		int id = 0;
		int anotherId = 1;
		IdNode node = new IdNode(id);
		IdNode anotherNode = new IdNode(anotherId);

		Assert.assertEquals(node.hashCode(), node.hashCode());
		Assert.assertNotEquals(anotherNode.hashCode(), node.hashCode());
	}

	/**
	 * Test method for {@link IdNode#IdNode(int)}.
	 */
	@Test
	public void testIdNode() {
		int id = 0;
		int anotherId = 1;
		IdNode node = new IdNode(id);
		IdNode anotherNode = new IdNode(anotherId);

		Assert.assertEquals(node, node);
		Assert.assertNotEquals(anotherNode, node);
	}

	/**
	 * Test method for {@link IdNode#toString()}.
	 */
	@Test
	public void testToString() {
		int id = 0;
		int anotherId = 1;
		IdNode node = new IdNode(id);
		IdNode anotherNode = new IdNode(anotherId);

		Assert.assertEquals(id + "", node.toString());
		Assert.assertEquals(anotherId + "", anotherNode.toString());
	}

}
