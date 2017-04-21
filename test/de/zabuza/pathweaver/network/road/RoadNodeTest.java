package de.zabuza.pathweaver.network.road;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link RoadNode}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoadNodeTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link RoadNode#equals(Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsObject() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final int differentLatitude = -10;
		final int differentLongitude = 14;
		final int differentId = 1;

		final RoadNode node = new RoadNode(id, latitude, longitude);
		final RoadNode differentLatNode = new RoadNode(id, differentLatitude, longitude);
		final RoadNode differentLongNode = new RoadNode(id, latitude, differentLongitude);
		final RoadNode totallyDifferentNode = new RoadNode(differentId, differentLatitude, differentLongitude);

		final RoadNode sameNode = new RoadNode(id, latitude, longitude);
		final RoadNode sameIdNode = new RoadNode(id, differentLatitude, differentLongitude);

		Assert.assertEquals(node, node);
		Assert.assertEquals(sameNode, node);
		Assert.assertEquals(sameIdNode, node);
		Assert.assertEquals(differentLatNode, node);
		Assert.assertEquals(differentLongNode, node);
		Assert.assertNotEquals(totallyDifferentNode, node);
	}

	/**
	 * Test method for {@link RoadNode#getId()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final int differentId = 2;
		final RoadNode node = new RoadNode(id, latitude, longitude);
		final RoadNode anotherNode = new RoadNode(differentId, latitude, longitude);

		Assert.assertEquals(id, node.getId());
		Assert.assertEquals(differentId, anotherNode.getId());
	}

	/**
	 * Test method for {@link RoadNode#getLatitude()}.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetLatitude() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final int differentLatitude = -10;
		final RoadNode node = new RoadNode(id, latitude, longitude);
		final RoadNode anotherNode = new RoadNode(id, differentLatitude, longitude);

		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(differentLatitude, anotherNode.getLatitude(), 0);

		// Test latitude which are out of the bounds
		final int exceedingMinLatitude = -100;
		final int exceedingMaxLatitude = 100;
		this.exception.expect(IllegalArgumentException.class);
		new RoadNode(id, exceedingMinLatitude, longitude);
		new RoadNode(id, exceedingMaxLatitude, longitude);
	}

	/**
	 * Test method for {@link RoadNode#getLongitude()}.
	 */
	@SuppressWarnings("unused")
	@Test
	public void testGetLongitude() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final int differentLongitude = -10;
		final RoadNode node = new RoadNode(id, latitude, longitude);
		final RoadNode anotherNode = new RoadNode(id, latitude, differentLongitude);

		Assert.assertEquals(longitude, node.getLongitude(), 0);
		Assert.assertEquals(differentLongitude, anotherNode.getLongitude(), 0);

		// Test longitude which are out of the bounds
		final int exceedingMinLongitude = -190;
		final int exceedingMaxLongitude = 190;
		this.exception.expect(IllegalArgumentException.class);
		new RoadNode(id, latitude, exceedingMinLongitude);
		new RoadNode(id, latitude, exceedingMaxLongitude);
	}

	/**
	 * Test method for {@link RoadNode#hashCode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final int differentLatitude = -10;
		final int differentLongitude = 14;
		final int differentId = 1;

		final RoadNode node = new RoadNode(id, latitude, longitude);
		final RoadNode differentLatNode = new RoadNode(id, differentLatitude, longitude);
		final RoadNode differentLongNode = new RoadNode(id, latitude, differentLongitude);
		final RoadNode totallyDifferentNode = new RoadNode(differentId, differentLatitude, differentLongitude);

		final RoadNode sameNode = new RoadNode(id, latitude, longitude);
		final RoadNode sameIdNode = new RoadNode(id, differentLatitude, differentLongitude);

		Assert.assertEquals(node.hashCode(), node.hashCode());
		Assert.assertEquals(sameNode.hashCode(), node.hashCode());
		Assert.assertEquals(sameIdNode.hashCode(), node.hashCode());
		Assert.assertEquals(differentLatNode.hashCode(), node.hashCode());
		Assert.assertEquals(differentLongNode.hashCode(), node.hashCode());
		Assert.assertNotEquals(totallyDifferentNode.hashCode(), node.hashCode());
	}

	/**
	 * Test method for {@link RoadNode#RoadNode(int, float, float)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRoadNode() {
		final int id = 0;
		final int latitude = 10;
		final int longitude = 20;
		final RoadNode node = new RoadNode(id, latitude, longitude);
		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(longitude, node.getLongitude(), 0);

		final int anotherLatitude = -10;
		final int anotherLongitude = 14;
		final int anotherId = 1;
		final RoadNode anotherNode = new RoadNode(anotherId, anotherLatitude, anotherLongitude);
		Assert.assertEquals(anotherLatitude, anotherNode.getLatitude(), 0);
		Assert.assertEquals(anotherLongitude, anotherNode.getLongitude(), 0);
	}

	/**
	 * Test method for {@link RoadNode#toString()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testToString() {
		final RoadNode node = new RoadNode(1, 10, 20);
		final RoadNode anotherNode = new RoadNode(3, -14, 75);

		Assert.assertEquals("1", node.toString());
		Assert.assertEquals("3", anotherNode.toString());
	}
}
