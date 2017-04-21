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
		final RoadNetwork network = new RoadNetwork();
		final RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		final RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		final RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

		network.addRoadNode(insideNode);
		network.addRoadNode(outsideNode);
		network.addRoadNode(anotherOutsideNode);

		final float latitudeMin = 49.20f;
		final float latitudeMax = 49.25f;
		final float longitudeMin = 6.95f;
		final float longitudeMax = 7.05f;
		final OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network,
				latitudeMin, latitudeMax, longitudeMin, longitudeMax);

		final Collection<Set<Node>> partitions = provider.getPartitioning();
		Assert.assertEquals(2, partitions.size());
		for (final Set<Node> partition : partitions) {
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
		final RoadNetwork network = new RoadNetwork();
		final float latitudeMin = 49.20f;
		final float latitudeMax = 49.25f;
		final float longitudeMin = 6.95f;
		final float longitudeMax = 7.05f;
		final OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network,
				latitudeMin, latitudeMax, longitudeMin, longitudeMax);

		final RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		final RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		final RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

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
		final RoadNetwork network = new RoadNetwork();
		final float latitudeMin = 49.20f;
		final float latitudeMax = 49.25f;
		final float longitudeMin = 6.95f;
		final float longitudeMax = 7.05f;
		final OneAxisRectanglePartitioningProvider provider = new OneAxisRectanglePartitioningProvider(network,
				latitudeMin, latitudeMax, longitudeMin, longitudeMax);

		final RoadNode insideNode = new RoadNode(0, 49.22f, 6.98f);
		final RoadNode outsideNode = new RoadNode(1, 49.22f, 7.06f);
		final RoadNode anotherOutsideNode = new RoadNode(2, 48.22f, 6.98f);

		Assert.assertTrue(provider.isInsideRectangle(insideNode));
		Assert.assertFalse(provider.isInsideRectangle(outsideNode));
		Assert.assertFalse(provider.isInsideRectangle(anotherOutsideNode));
	}

}
