package de.zabuza.pathweaver.network.road;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link RoadUtil}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class RoadUtilTest {

	/**
	 * Test method for {@link RoadUtil#degToRad(double)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDegToRad() {
		Assert.assertEquals(Math.PI, RoadUtil.degToRad(180), 0);
	}

	/**
	 * Test method for {@link RoadUtil#distanceEquiRect(RoadNode, RoadNode)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testDistanceEquiRect() {
		RoadNode firstNode = new RoadNode(0, 49.3413853f, 7.3014897f);
		RoadNode secondNode = new RoadNode(1, 49.3080623f, 7.2517281f);
		Assert.assertEquals(5170.5f, RoadUtil.distanceEquiRect(firstNode, secondNode), 1);
	}

	/**
	 * Test method for {@link RoadUtil#getFastestRoadType()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetFastestRoadType() {
		float speedAccordingToMethod = RoadUtil.getAverageSpeedOfRoadType(RoadUtil.getFastestRoadType());
		float fastestKnownSpeed = -1;
		for (ERoadType type : ERoadType.values()) {
			float speedOfType = RoadUtil.getAverageSpeedOfRoadType(type);
			if (speedOfType > fastestKnownSpeed) {
				fastestKnownSpeed = speedOfType;
			}
		}

		Assert.assertEquals(fastestKnownSpeed, speedAccordingToMethod, 0);
	}

	/**
	 * Test method for {@link RoadUtil#getPositionsTsv(Iterable)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetPositionsTsv() {
		RoadNode firstNode = new RoadNode(0, 47.4f, 31.23f);
		RoadNode secondNode = new RoadNode(1, 11f, -5.4f);
		LinkedList<RoadNode> nodes = new LinkedList<>();
		nodes.add(firstNode);
		nodes.add(secondNode);

		Assert.assertEquals("47.4\t31.23" + System.lineSeparator() + "11.0\t-5.4", RoadUtil.getPositionsTsv(nodes));
	}

	/**
	 * Test method for {@link RoadUtil#getPositionTsv(RoadNode)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetPositionTsv() {
		RoadNode firstNode = new RoadNode(0, 47.4f, 31.23f);
		RoadNode secondNode = new RoadNode(1, 11f, -5.4f);

		Assert.assertEquals("47.4\t31.23", RoadUtil.getPositionTsv(firstNode));
		Assert.assertEquals("11.0\t-5.4", RoadUtil.getPositionTsv(secondNode));
	}

	/**
	 * Test method for {@link RoadUtil#getRoadTypeFromOsm(String)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetRoadTypeFromOsm() {
		Assert.assertEquals(ERoadType.MOTORWAY, RoadUtil.getRoadTypeFromOsm("motorway"));
		Assert.assertEquals(ERoadType.PRIMARY_LINK, RoadUtil.getRoadTypeFromOsm("primary_link"));
	}

	/**
	 * Test method for {@link RoadUtil#getTravelTime(float, float)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetTravelTime() {
		Assert.assertEquals(1800, RoadUtil.getTravelTime(5000, 10), 2);
	}

	/**
	 * Test method for {@link RoadUtil#kmhToMs(float)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testKmhToMs() {
		Assert.assertEquals(5, RoadUtil.kmhToMs(18), 0);
	}

	/**
	 * Test method for {@link RoadUtil#msToKmh(float)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testMsToKmh() {
		Assert.assertEquals(18, RoadUtil.msToKmh(5), 0);
	}

	/**
	 * Test method for {@link RoadUtil#radToDeg(double)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRadToDeg() {
		Assert.assertEquals(180, RoadUtil.radToDeg(Math.PI), 0);
	}

}
