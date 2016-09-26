package de.zabuza.pathweaver.network.road;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link RoadNode}.
 * 
 * @author Zabuza
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
	@Test
	public void testEqualsObject() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		int differentLongitude = 14;
		int differentId = 1;

		RoadNode node = new RoadNode(id, latitude, longitude);
		RoadNode differentLatNode = new RoadNode(id, differentLatitude, longitude);
		RoadNode differentLongNode = new RoadNode(id, latitude, differentLongitude);
		RoadNode totallyDifferentNode = new RoadNode(differentId, differentLatitude, differentLongitude);

		RoadNode sameNode = new RoadNode(id, latitude, longitude);
		RoadNode sameIdNode = new RoadNode(id, differentLatitude, differentLongitude);

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
	@Test
	public void testGetId() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		int differentId = 2;
		RoadNode node = new RoadNode(id, latitude, longitude);
		RoadNode anotherNode = new RoadNode(differentId, latitude, longitude);

		Assert.assertEquals(id, node.getId());
		Assert.assertEquals(differentId, anotherNode.getId());
	}

	/**
	 * Test method for {@link RoadNode#getLatitude()}.
	 */
	@Test
	public void testGetLatitude() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		RoadNode node = new RoadNode(id, latitude, longitude);
		RoadNode anotherNode = new RoadNode(id, differentLatitude, longitude);

		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(differentLatitude, anotherNode.getLatitude(), 0);

		// Test latitude which are out of the bounds
		int exceedingMinLatitude = -100;
		int exceedingMaxLatitude = 100;
		exception.expect(IllegalArgumentException.class);
		new RoadNode(id, exceedingMinLatitude, longitude);
		new RoadNode(id, exceedingMaxLatitude, longitude);
	}

	/**
	 * Test method for {@link RoadNode#getLongitude()}.
	 */
	@Test
	public void testGetLongitude() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		int differentLongitude = -10;
		RoadNode node = new RoadNode(id, latitude, longitude);
		RoadNode anotherNode = new RoadNode(id, latitude, differentLongitude);

		Assert.assertEquals(longitude, node.getLongitude(), 0);
		Assert.assertEquals(differentLongitude, anotherNode.getLongitude(), 0);

		// Test longitude which are out of the bounds
		int exceedingMinLongitude = -190;
		int exceedingMaxLongitude = 190;
		exception.expect(IllegalArgumentException.class);
		new RoadNode(id, latitude, exceedingMinLongitude);
		new RoadNode(id, latitude, exceedingMaxLongitude);
	}

	/**
	 * Test method for {@link RoadNode#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		int differentLongitude = 14;
		int differentId = 1;

		RoadNode node = new RoadNode(id, latitude, longitude);
		RoadNode differentLatNode = new RoadNode(id, differentLatitude, longitude);
		RoadNode differentLongNode = new RoadNode(id, latitude, differentLongitude);
		RoadNode totallyDifferentNode = new RoadNode(differentId, differentLatitude, differentLongitude);

		RoadNode sameNode = new RoadNode(id, latitude, longitude);
		RoadNode sameIdNode = new RoadNode(id, differentLatitude, differentLongitude);

		Assert.assertEquals(node.hashCode(), node.hashCode());
		Assert.assertEquals(sameNode.hashCode(), node.hashCode());
		Assert.assertEquals(sameIdNode.hashCode(), node.hashCode());
		Assert.assertEquals(differentLatNode.hashCode(), node.hashCode());
		Assert.assertEquals(differentLongNode.hashCode(), node.hashCode());
		Assert.assertNotEquals(totallyDifferentNode.hashCode(), node.hashCode());
	}

	/**
	 * Test method for {@link RoadNode#RoadNode(float, float)}.
	 */
	@Test
	public void testRoadNode() {
		int id = 0;
		int latitude = 10;
		int longitude = 20;
		RoadNode node = new RoadNode(id, latitude, longitude);
		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(longitude, node.getLongitude(), 0);

		int anotherLatitude = -10;
		int anotherLongitude = 14;
		int anotherId = 1;
		RoadNode anotherNode = new RoadNode(anotherId, anotherLatitude, anotherLongitude);
		Assert.assertEquals(anotherLatitude, anotherNode.getLatitude(), 0);
		Assert.assertEquals(anotherLongitude, anotherNode.getLongitude(), 0);
	}

	/**
	 * Test method for {@link RoadNode#toString()}.
	 */
	@Test
	public void testToString() {
		RoadNode node = new RoadNode(1, 10, 20);
		RoadNode anotherNode = new RoadNode(3, -14, 75);

		Assert.assertEquals("1", node.toString());
		Assert.assertEquals("3", anotherNode.toString());
	}
}
