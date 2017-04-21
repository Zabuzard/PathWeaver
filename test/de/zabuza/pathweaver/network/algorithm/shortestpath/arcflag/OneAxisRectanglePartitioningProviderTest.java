package de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag;

import java.util.Collection;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.road.RoadNetwork;
import de.zabuza.pathweaver.network.road.RoadNode;

/**
 * Test for {@link OneAxisRectanglePartitioningProvider}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class OneAxisRectanglePartitioningProviderTest {

	/**
	 * Test method for
	 * {@link OneAxisRectanglePartitioningProvider#getPartitioning()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetPartitioning() {
		RoadNetwork network = new RoadNetwork();
		RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

		network.addRoadNode(insideNode);
		network.addRoadNode(outsideNode);
		network.addRoadNode(anotherOutsideNode);

		float latitudeMin = 49.20f;
		float latitudeMax = 49.25f;
		float longitudeMin = 6.95f;
		float longitudeMax = 7.05f;
		OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network, latitudeMin,
				latitudeMax, longitudeMin, longitudeMax);

		Collection<Set<Node>> partitions = provider.getPartitioning();
		Assert.assertEquals(2, partitions.size());
		for (Set<Node> partition : partitions) {
			if (partition.size() == 1) {
				Assert.assertEquals(1, partition.size());
				Assert.assertTrue(partition.contains(insideNode));
				Assert.assertFalse(partition.contains(outsideNode));
				Assert.assertFalse(partition.contains(anotherOutsideNode));
			} else {
				Assert.assertEquals(2, partition.size());
				Assert.assertFalse(partition.contains(insideNode));
				Assert.assertTrue(partition.contains(outsideNode));
				Assert.assertTrue(partition.contains(anotherOutsideNode));
			}
		}
	}

	/**
	 * Test method for
	 * {@link OneAxisRectanglePartitioningProvider#isInsideRectangle(RoadNode)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testIsInsideRectangle() {
		RoadNetwork network = new RoadNetwork();
		float latitudeMin = 49.20f;
		float latitudeMax = 49.25f;
		float longitudeMin = 6.95f;
		float longitudeMax = 7.05f;
		OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network, latitudeMin,
				latitudeMax, longitudeMin, longitudeMax);

		RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

		Assert.assertTrue(provider.isInsideRectangle(insideNode));
		Assert.assertFalse(provider.isInsideRectangle(outsideNode));
		Assert.assertFalse(provider.isInsideRectangle(anotherOutsideNode));
	}

	/**
	 * Test method for
	 * {@link OneAxisRectanglePartitioningProvider#OneAxisRectanglePartitioningProvider(RoadNetwork, float, float, float, float)}
	 * .
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testOneAxisRectanglePartitioningProvider() {
		RoadNetwork network = new RoadNetwork();
		float latitudeMin = 49.20f;
		float latitudeMax = 49.25f;
		float longitudeMin = 6.95f;
		float longitudeMax = 7.05f;
		OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network, latitudeMin,
				latitudeMax, longitudeMin, longitudeMax);

		RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

		Assert.assertTrue(provider.isInsideRectangle(insideNode));
		Assert.assertFalse(provider.isInsideRectangle(outsideNode));
		Assert.assertFalse(provider.isInsideRectangle(anotherOutsideNode));
	}

}
