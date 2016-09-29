package de.zabuza.pathweaver.network.road;

import java.io.IOException;
import java.io.StringReader;
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
		Assert.assertEquals(169, firstEdge.getCost(), 2);

		Set<OutgoingEdge> secondEdges = network.getOutgoingEdges(destination);
		Assert.assertEquals(1, secondEdges.size());
		OutgoingEdge secondEdge = secondEdges.iterator().next();
		Assert.assertEquals(source, secondEdge.getDestination());
		Assert.assertEquals(266, secondEdge.getCost(), 2);
	}

	/**
	 * Test method for {@link RoadNetwork#addRoad(Road)}.
	 */
	@Test
	public void testAddRoad2() {
		RoadNetwork network = new RoadNetwork();
		RoadNode firstNode = new RoadNode(0, 1, 1);
		RoadNode secondNode = new RoadNode(1, 2, 2);
		RoadNode thirdNode = new RoadNode(2, 3, 4);
		network.addRoadNode(firstNode);
		network.addRoadNode(secondNode);
		network.addRoadNode(thirdNode);

		Road firstRoad = new Road(0);
		firstRoad.addRoadNode(firstNode.getId());
		firstRoad.addRoadNode(secondNode.getId());
		firstRoad.addRoadNode(thirdNode.getId());
		firstRoad.setRoadType(ERoadType.PRIMARY);
		firstRoad.setIsOneway(true);
		network.addRoad(firstRoad);
		Assert.assertEquals(3, network.getAmountOfNodes());
		Assert.assertEquals(2, network.getAmountOfEdges());
		OutgoingEdge firstEdge = network.getOutgoingEdges(network.getNodeById(0)).iterator().next();
		Assert.assertEquals(secondNode, firstEdge.getDestination());

		Road secondRoad = new Road(1);
		secondRoad.addRoadNode(secondNode.getId());
		secondRoad.addRoadNode(firstNode.getId());
		secondRoad.setRoadType(ERoadType.MOTORWAY);
		secondRoad.setIsOneway(false);
		network.addRoad(secondRoad);
		Assert.assertEquals(3, network.getAmountOfNodes());
		Assert.assertEquals(4, network.getAmountOfEdges());
	}

	/**
	 * Test method for {@link RoadNetwork#addRoadNode(RoadNode)}.
	 */
	@Test
	public void testAddRoadNode() {
		RoadNetwork network = new RoadNetwork();
		RoadNode node = new RoadNode(0, 1, 1);
		RoadNode anotherNode = new RoadNode(1, 2, 2);

		Assert.assertFalse(network.containsNodeId(0));
		network.addRoadNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		network.addRoadNode(anotherNode);
		Assert.assertTrue(network.containsNodeId(1));

		network.addRoadNode(node);
		Assert.assertTrue(network.containsNodeId(0));
		Assert.assertEquals(2, network.getAmountOfNodes());
	}

	/**
	 * Test method for {@link RoadNetwork#createFromOsmReader(java.io.Reader)}.
	 * 
	 * @throws IOException
	 *             If an I/O-Exception occurred
	 */
	@Test
	public void testCreateFromOsmFile() throws IOException {
		StringReader reader = new StringReader(
				"<?xml version='1.0' encoding='UTF-8'?>\n<osm version=\"0.6\" generator=\"pbf2osm\">\n"
						+ "	<node id=\"470552\" lat=\"49.3413853\" lon=\"7.3014897\" version=\"4\" changeset=\"9382609\" user=\"mmd\" uid=\"249676\" timestamp=\"2011-09-24T09:25:23Z\"/>\n"
						+ "	<node id=\"470553\" lat=\"49.3407084\" lon=\"7.3006280\" version=\"3\" changeset=\"9382609\" user=\"mmd\" uid=\"249676\" timestamp=\"2011-09-24T09:25:23Z\"/>\n"
						+ "	<node id=\"470554\" lat=\"49.3406105\" lon=\"7.3004165\" version=\"3\" changeset=\"9382609\" user=\"mmd\" uid=\"249676\" timestamp=\"2011-09-24T09:25:23Z\"/>\n"
						+ "		<tag k=\"created_by\" v=\"JOSM\" />\n"
						+ "	<way id=\"3998029\" version=\"20\" changeset=\"8405334\" uid=\"30077\" user=\"ChristianW\" timestamp=\"2011-06-11T12:20:08Z\">\n"
						+ "		<nd ref=\"470552\"/>\n" + "		<nd ref=\"470553\"/>\n"
						+ "		<tag k=\"highway\" v=\"motorway\" />\n" + "		<tag k=\"int_ref\" v=\"E 50\" />\n"
						+ "		<tag k=\"oneway\" v=\"yes\" />\n" + "	</way>\n"
						+ "	<way id=\"3998240\" version=\"12\" changeset=\"8405334\" uid=\"30077\" user=\"ChristianW\" timestamp=\"2011-06-11T12:24:36Z\">\n"
						+ "		<nd ref=\"470552\"/>\n" + "		<nd ref=\"470553\"/>\n" + "		<nd ref=\"470554\"/>\n"
						+ "		<tag k=\"highway\" v=\"motorway\" />\n" + "		<tag k=\"ref\" v=\"A 6\" />\n"
						+ "	</way>\n" + "</osm>");
		RoadNetwork network = RoadNetwork.createFromOsmReader(reader);
		Assert.assertEquals(3, network.getAmountOfNodes());
		Assert.assertEquals(5, network.getAmountOfEdges());

		RoadNode firstNode = (RoadNode) network.getNodeById(470552);
		RoadNode secondNode = (RoadNode) network.getNodeById(470553);
		OutgoingEdge edge = network.getOutgoingEdges(firstNode).iterator().next();
		Assert.assertEquals(secondNode, edge.getDestination());
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
