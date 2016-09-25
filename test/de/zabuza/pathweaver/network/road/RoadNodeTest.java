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
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		int differentLongitude = 14;

		RoadNode node = new RoadNode(latitude, longitude);
		RoadNode differentLatNode = new RoadNode(differentLatitude, longitude);
		RoadNode differentLongNode = new RoadNode(latitude, differentLongitude);
		RoadNode totallyDifferentNode = new RoadNode(differentLatitude, differentLongitude);

		RoadNode sameNode = new RoadNode(latitude, longitude);

		Assert.assertEquals(node, node);
		Assert.assertEquals(sameNode, node);
		Assert.assertNotEquals(differentLatNode, node);
		Assert.assertNotEquals(differentLongNode, node);
		Assert.assertNotEquals(totallyDifferentNode, node);
	}

	/**
	 * Test method for {@link RoadNode#getLatitude()}.
	 */
	@Test
	public void testGetLatitude() {
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		RoadNode node = new RoadNode(latitude, longitude);
		RoadNode anotherNode = new RoadNode(differentLatitude, longitude);

		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(differentLatitude, anotherNode.getLatitude(), 0);

		// Test latitude which are out of the bounds
		int exceedingMinLatitude = -100;
		int exceedingMaxLatitude = 100;
		exception.expect(IllegalArgumentException.class);
		new RoadNode(exceedingMinLatitude, longitude);
		new RoadNode(exceedingMaxLatitude, longitude);
	}

	/**
	 * Test method for {@link RoadNode#getLongitude()}.
	 */
	@Test
	public void testGetLongitude() {
		int latitude = 10;
		int longitude = 20;
		int differentLongitude = -10;
		RoadNode node = new RoadNode(latitude, longitude);
		RoadNode anotherNode = new RoadNode(latitude, differentLongitude);

		Assert.assertEquals(longitude, node.getLongitude(), 0);
		Assert.assertEquals(differentLongitude, anotherNode.getLongitude(), 0);

		// Test longitude which are out of the bounds
		int exceedingMinLongitude = -190;
		int exceedingMaxLongitude = 190;
		exception.expect(IllegalArgumentException.class);
		new RoadNode(latitude, exceedingMinLongitude);
		new RoadNode(latitude, exceedingMaxLongitude);
	}

	/**
	 * Test method for {@link RoadNode#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		int latitude = 10;
		int longitude = 20;
		int differentLatitude = -10;
		int differentLongitude = 14;

		RoadNode node = new RoadNode(latitude, longitude);
		RoadNode differentLatNode = new RoadNode(differentLatitude, longitude);
		RoadNode differentLongNode = new RoadNode(latitude, differentLongitude);
		RoadNode totallyDifferentNode = new RoadNode(differentLatitude, differentLongitude);

		RoadNode sameNode = new RoadNode(latitude, longitude);

		Assert.assertEquals(node.hashCode(), node.hashCode());
		Assert.assertEquals(sameNode.hashCode(), node.hashCode());
		Assert.assertNotEquals(differentLatNode.hashCode(), node.hashCode());
		Assert.assertNotEquals(differentLongNode.hashCode(), node.hashCode());
		Assert.assertNotEquals(totallyDifferentNode.hashCode(), node.hashCode());
	}

	/**
	 * Test method for {@link RoadNode#RoadNode(float, float)}.
	 */
	@Test
	public void testRoadNode() {
		int latitude = 10;
		int longitude = 20;
		RoadNode node = new RoadNode(latitude, longitude);
		Assert.assertEquals(latitude, node.getLatitude(), 0);
		Assert.assertEquals(longitude, node.getLongitude(), 0);

		int anotherLatitude = -10;
		int anotherLongitude = 14;
		RoadNode anotherNode = new RoadNode(anotherLatitude, anotherLongitude);
		Assert.assertEquals(anotherLatitude, anotherNode.getLatitude(), 0);
		Assert.assertEquals(anotherLongitude, anotherNode.getLongitude(), 0);
	}

}
