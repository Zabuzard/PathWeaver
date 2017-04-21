package de.zabuza.pathweaver.network.road;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link Road}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoadTest {

	/**
	 * Test method for {@link Road#addRoadNode(int)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testAddRoadNode() {
		Road road = new Road(0);
		RoadNode firstNode = new RoadNode(0, 1, 1);
		road.addRoadNode(firstNode.getId());

		Assert.assertEquals(1, road.getRoadNodesAmount());
		Assert.assertEquals(new Integer(firstNode.getId()), road.getRoadNodes().next());
	}

	/**
	 * Test method for {@link Road#equals(Object)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testEqualsObject() {
		Road road = new Road(0);
		road.setRoadType(ERoadType.MOTORWAY);
		Road similarRoad = new Road(0);
		similarRoad.setRoadType(ERoadType.PRIMARY);
		Road differentRoad = new Road(1);
		differentRoad.setRoadType(ERoadType.MOTORWAY);

		Assert.assertEquals(road, road);
		Assert.assertEquals(road, similarRoad);
		Assert.assertNotEquals(road, differentRoad);
	}

	/**
	 * Test method for {@link Road#getId()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		Road road = new Road(0);
		Road anotherRoad = new Road(1);

		Assert.assertEquals(0, road.getId());
		Assert.assertEquals(1, anotherRoad.getId());
	}

	/**
	 * Test method for {@link Road#getRoadNodes()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoadNodes() {
		Road road = new Road(0);
		RoadNode firstNode = new RoadNode(0, 1, 1);
		RoadNode secondNode = new RoadNode(1, 2, 2);
		RoadNode thirdNode = new RoadNode(2, 3, 3);
		road.addRoadNode(firstNode.getId());
		road.addRoadNode(secondNode.getId());
		road.addRoadNode(thirdNode.getId());

		Assert.assertEquals(3, road.getRoadNodesAmount());
		Iterator<Integer> iter = road.getRoadNodes();

		Assert.assertEquals(new Integer(firstNode.getId()), iter.next());
		Assert.assertEquals(new Integer(secondNode.getId()), iter.next());
		Assert.assertEquals(new Integer(thirdNode.getId()), iter.next());
	}

	/**
	 * Test method for {@link Road#getRoadNodesAmount()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoadNodesAmount() {
		Road road = new Road(0);
		RoadNode firstNode = new RoadNode(0, 1, 1);
		RoadNode secondNode = new RoadNode(1, 2, 2);
		road.addRoadNode(firstNode.getId());

		Assert.assertEquals(1, road.getRoadNodesAmount());

		road.addRoadNode(secondNode.getId());
		Assert.assertEquals(2, road.getRoadNodesAmount());

		road.addRoadNode(firstNode.getId());
		Assert.assertEquals(3, road.getRoadNodesAmount());
	}

	/**
	 * Test method for {@link Road#getRoadNodesReversed()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoadNodesReversed() {
		Road road = new Road(0);
		RoadNode firstNode = new RoadNode(0, 1, 1);
		RoadNode secondNode = new RoadNode(1, 2, 2);
		RoadNode thirdNode = new RoadNode(2, 3, 3);
		road.addRoadNode(firstNode.getId());
		road.addRoadNode(secondNode.getId());
		road.addRoadNode(thirdNode.getId());

		Assert.assertEquals(3, road.getRoadNodesAmount());
		Iterator<Integer> iterReversed = road.getRoadNodesReversed();

		Assert.assertEquals(new Integer(thirdNode.getId()), iterReversed.next());
		Assert.assertEquals(new Integer(secondNode.getId()), iterReversed.next());
		Assert.assertEquals(new Integer(firstNode.getId()), iterReversed.next());
	}

	/**
	 * Test method for {@link Road#getRoadType()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoadType() {
		Road road = new Road(0);
		road.setRoadType(ERoadType.MOTORWAY);
		Road anotherRoad = new Road(1);
		anotherRoad.setRoadType(ERoadType.PRIMARY);

		Assert.assertEquals(ERoadType.MOTORWAY, road.getRoadType());
		Assert.assertEquals(ERoadType.PRIMARY, anotherRoad.getRoadType());
	}

	/**
	 * Test method for {@link Road#hashCode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testHashCode() {
		Road road = new Road(0);
		road.setRoadType(ERoadType.MOTORWAY);
		Road similarRoad = new Road(0);
		similarRoad.setRoadType(ERoadType.PRIMARY);
		Road differentRoad = new Road(1);
		differentRoad.setRoadType(ERoadType.MOTORWAY);

		Assert.assertEquals(road.hashCode(), road.hashCode());
		Assert.assertEquals(road.hashCode(), similarRoad.hashCode());
		Assert.assertNotEquals(road.hashCode(), differentRoad.hashCode());
	}

	/**
	 * Test method for {@link Road#isOneway()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testIsOneway() {
		Road road = new Road(0);
		road.setIsOneway(true);
		Road anotherRoad = new Road(1);
		anotherRoad.setIsOneway(false);

		Assert.assertEquals(Boolean.TRUE, Boolean.valueOf(road.isOneway()));
		Assert.assertEquals(Boolean.FALSE, Boolean.valueOf(anotherRoad.isOneway()));
	}

	/**
	 * Test method for {@link Road#Road(int)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRoad() {
		Road road = new Road(0);
		road.setRoadType(ERoadType.MOTORWAY);
		Road similarRoad = new Road(0);
		similarRoad.setRoadType(ERoadType.PRIMARY);
		Road differentRoad = new Road(1);
		differentRoad.setRoadType(ERoadType.MOTORWAY);

		Assert.assertEquals(road, road);
		Assert.assertEquals(ERoadType.MOTORWAY, road.getRoadType());
		Assert.assertEquals(road, similarRoad);
		Assert.assertNotEquals(road, differentRoad);
	}

	/**
	 * Test method for {@link Road#setIsOneway(boolean)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSetIsOneway() {
		Road road = new Road(0);
		road.setIsOneway(true);
		Road anotherRoad = new Road(1);
		anotherRoad.setIsOneway(false);

		Assert.assertEquals(Boolean.TRUE, Boolean.valueOf(road.isOneway()));
		Assert.assertEquals(Boolean.FALSE, Boolean.valueOf(anotherRoad.isOneway()));
	}

	/**
	 * Test method for {@link Road#setRoadType(ERoadType)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testSetRoadType() {
		Road road = new Road(0);
		road.setRoadType(ERoadType.MOTORWAY);
		Road anotherRoad = new Road(1);
		anotherRoad.setRoadType(ERoadType.PRIMARY);

		Assert.assertEquals(ERoadType.MOTORWAY, road.getRoadType());
		Assert.assertEquals(ERoadType.PRIMARY, anotherRoad.getRoadType());
	}

}
