package de.zabuza.pathweaver.network.road;

import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.OutgoingEdge;

/**
 * Test for {@link RoadNetwork}.
 * 
 * @author Zabuza
 *
 */
public class RoadNetworkTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link RoadNetwork#addEdge(Node, Node, int)}.
	 */
	@Test
	public void testAddEdge() {
		RoadNetwork network = new RoadNetwork();
		RoadNode source = new RoadNode(0, 1, 1);
		RoadNode destination = new RoadNode(1, 2, 2);
		int cost = 1;

		exception.expect(UnsupportedOperationException.class);
		network.addEdge(source, destination, cost);
	}

	/**
	 * Test method for {@link RoadNetwork#addNode(Node)}.
	 */
	@Test
	public void testAddNode() {
		RoadNetwork network = new RoadNetwork();
		RoadNode source = new RoadNode(0, 1, 1);

		exception.expect(UnsupportedOperationException.class);
		network.addNode(source);
	}

	/**
	 * Test method for
	 * {@link RoadNetwork#addRoad(RoadNode, RoadNode, ERoadType)}.
	 */
	@Test
	public void testAddRoad() {
		RoadNetwork network = new RoadNetwork();
		RoadNode source = new RoadNode(0, 49.3413853f, 7.3014897f);
		RoadNode destination = new RoadNode(1, 49.3080623f, 7.2517281f);

		network.addRoadNode(source);
		network.addRoadNode(destination);
		network.addRoad(source, destination, ERoadType.MOTORWAY);
		network.addRoad(destination, source, ERoadType.PRIMARY);

		Assert.assertEquals(2, network.getAmountOfEdges());
		Set<OutgoingEdge> firstEdges = network.getOutgoingEdges(source);
		Assert.assertEquals(1, firstEdges.size());
		OutgoingEdge firstEdge = firstEdges.iterator().next();
		Assert.assertEquals(destination, firstEdge.getDestination());
		Assert.assertEquals(169, firstEdge.getCost());

		Set<OutgoingEdge> secondEdges = network.getOutgoingEdges(destination);
		Assert.assertEquals(1, secondEdges.size());
		OutgoingEdge secondEdge = secondEdges.iterator().next();
		Assert.assertEquals(source, secondEdge.getDestination());
		Assert.assertEquals(266, secondEdge.getCost());
	}

	/**
	 * Test method for {@link RoadNetwork#addRoadNode(RoadNode)}.
	 */
	@Test
	public void testAddRoadNode() {
		RoadNetwork network = new RoadNetwork();
		RoadNode node = new RoadNode(0, 1, 1);
		RoadNode anotherNode = new RoadNode(1, 2, 2);

		Assert.assertFalse(network.containsNode(node));
		network.addRoadNode(node);
		Assert.assertTrue(network.containsNode(node));
		network.addRoadNode(anotherNode);
		Assert.assertTrue(network.containsNode(anotherNode));

		network.addRoadNode(node);
		Assert.assertTrue(network.containsNode(node));
		Assert.assertEquals(2, network.getAmountOfNodes());
	}

	/**
	 * Test method for {@link RoadNetwork#createFromOsmFile(File)}.
	 */
	@Test
	public void testCreateFromOsmFile() {
		Assert.fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link RoadNetwork#getRoadNodeById(int)}.
	 */
	@Test
	public void testGetRoadNodeById() {
		RoadNetwork network = new RoadNetwork();
		RoadNode node = new RoadNode(0, 1, 1);
		RoadNode anotherNode = new RoadNode(1, 2, 2);

		Assert.assertNull(network.getRoadNodeById(0));
		network.addRoadNode(node);
		Assert.assertEquals(node, network.getRoadNodeById(0));
		network.addRoadNode(anotherNode);
		Assert.assertEquals(anotherNode, network.getRoadNodeById(1));

		Assert.assertTrue(network.containsNode(node));
		network.addRoadNode(anotherNode);
		Assert.assertTrue(network.containsNode(anotherNode));
	}

	/**
	 * Test method for {@link RoadNetwork#RoadNetwork()}.
	 */
	@Test
	public void testRoadNetwork() {
		RoadNetwork network = new RoadNetwork();
		Assert.assertEquals(0, network.getAmountOfNodes());
		Assert.assertEquals(0, network.getAmountOfEdges());
	}

}
