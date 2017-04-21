package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Test for {@link TarjanSccComputation}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TarjanSccComputationTest {

	/**
	 * Test method for {@link TarjanSccComputation#getLargestScc()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetLargestScc() {
		PathNetwork network = new PathNetwork();

		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		Node fifthNode = new Node(4);
		Node sixthNode = new Node(5);

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addNode(fifthNode);
		network.addNode(sixthNode);

		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(firstNode, thirdNode, 3);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(firstNode, fourthNode, 10);
		network.addEdge(firstNode, fifthNode, 4);
		network.addEdge(fifthNode, secondNode, 5);
		network.addEdge(fifthNode, sixthNode, 3);
		network.addEdge(sixthNode, fourthNode, 1);

		network.addEdge(secondNode, firstNode, 1);
		network.addEdge(thirdNode, secondNode, 1);
		network.addEdge(thirdNode, firstNode, 3);
		network.addEdge(fourthNode, thirdNode, 1);
		network.addEdge(fourthNode, firstNode, 10);
		network.addEdge(fifthNode, firstNode, 4);
		network.addEdge(secondNode, fifthNode, 5);
		network.addEdge(sixthNode, fifthNode, 3);
		network.addEdge(fourthNode, sixthNode, 1);

		Node seventhNode = new Node(6);
		Node eighthNode = new Node(7);
		Node ninthNode = new Node(8);
		Node tenthNode = new Node(9);

		network.addNode(seventhNode);
		network.addNode(eighthNode);
		network.addNode(ninthNode);
		network.addNode(tenthNode);

		network.addEdge(fourthNode, seventhNode, 1);
		network.addEdge(seventhNode, eighthNode, 1);
		network.addEdge(eighthNode, ninthNode, 1);
		network.addEdge(ninthNode, seventhNode, 1);
		network.addEdge(tenthNode, eighthNode, 1);

		TarjanSccComputation computation = new TarjanSccComputation(network);
		StronglyConnectedComponent scc = computation.getLargestScc();
		Set<Node> nodes = scc.getNodes();
		Assert.assertEquals(6, scc.getSize());
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertTrue(nodes.contains(thirdNode));
		Assert.assertTrue(nodes.contains(fourthNode));
		Assert.assertTrue(nodes.contains(fifthNode));
		Assert.assertTrue(nodes.contains(sixthNode));

		Assert.assertFalse(nodes.contains(seventhNode));
		Assert.assertFalse(nodes.contains(eighthNode));
		Assert.assertFalse(nodes.contains(ninthNode));
		Assert.assertFalse(nodes.contains(tenthNode));
	}

	/**
	 * Test method for {@link TarjanSccComputation#getPathNetwork()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetPathNetwork() {
		PathNetwork network = new PathNetwork();
		TarjanSccComputation computation = new TarjanSccComputation(network);
		PathNetwork anotherNetwork = new PathNetwork();
		TarjanSccComputation anotherComputation = new TarjanSccComputation(anotherNetwork);

		Assert.assertEquals(network, computation.getPathNetwork());
		Assert.assertEquals(anotherNetwork, anotherComputation.getPathNetwork());
	}

	/**
	 * Test method for {@link TarjanSccComputation#getSccs()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSccs() {
		PathNetwork network = new PathNetwork();

		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);
		Node fifthNode = new Node(4);
		Node sixthNode = new Node(5);

		network.addNode(firstNode);
		network.addNode(secondNode);
		network.addNode(thirdNode);
		network.addNode(fourthNode);
		network.addNode(fifthNode);
		network.addNode(sixthNode);

		network.addEdge(firstNode, secondNode, 1);
		network.addEdge(secondNode, thirdNode, 1);
		network.addEdge(firstNode, thirdNode, 3);
		network.addEdge(thirdNode, fourthNode, 1);
		network.addEdge(firstNode, fourthNode, 10);
		network.addEdge(firstNode, fifthNode, 4);
		network.addEdge(fifthNode, secondNode, 5);
		network.addEdge(fifthNode, sixthNode, 3);
		network.addEdge(sixthNode, fourthNode, 1);

		network.addEdge(secondNode, firstNode, 1);
		network.addEdge(thirdNode, secondNode, 1);
		network.addEdge(thirdNode, firstNode, 3);
		network.addEdge(fourthNode, thirdNode, 1);
		network.addEdge(fourthNode, firstNode, 10);
		network.addEdge(fifthNode, firstNode, 4);
		network.addEdge(secondNode, fifthNode, 5);
		network.addEdge(sixthNode, fifthNode, 3);
		network.addEdge(fourthNode, sixthNode, 1);

		Node seventhNode = new Node(6);
		Node eighthNode = new Node(7);
		Node ninthNode = new Node(8);
		Node tenthNode = new Node(9);

		network.addNode(seventhNode);
		network.addNode(eighthNode);
		network.addNode(ninthNode);
		network.addNode(tenthNode);

		network.addEdge(fourthNode, seventhNode, 1);
		network.addEdge(seventhNode, eighthNode, 1);
		network.addEdge(eighthNode, ninthNode, 1);
		network.addEdge(ninthNode, seventhNode, 1);
		network.addEdge(tenthNode, eighthNode, 1);

		TarjanSccComputation computation = new TarjanSccComputation(network);
		List<StronglyConnectedComponent> sccs = computation.getSccs();
		Assert.assertEquals(3, sccs.size());

		boolean foundLargeScc = false;
		for (StronglyConnectedComponent scc : sccs) {
			if (scc.getSize() == 6) {
				foundLargeScc = true;
				break;
			}
		}
		Assert.assertTrue(foundLargeScc);

		boolean foundMiddleScc = false;
		for (StronglyConnectedComponent scc : sccs) {
			if (scc.getSize() == 3) {
				foundMiddleScc = true;
				Set<Node> nodes = scc.getNodes();
				Assert.assertTrue(nodes.contains(seventhNode));
				Assert.assertTrue(nodes.contains(eighthNode));
				Assert.assertTrue(nodes.contains(ninthNode));
				break;
			}
		}
		Assert.assertTrue(foundMiddleScc);

		boolean foundSmallScc = false;
		for (StronglyConnectedComponent scc : sccs) {
			if (scc.getSize() == 1) {
				foundSmallScc = true;
				Set<Node> nodes = scc.getNodes();
				Assert.assertTrue(nodes.contains(tenthNode));
				break;
			}
		}
		Assert.assertTrue(foundSmallScc);
	}

	/**
	 * Test method for
	 * {@link TarjanSccComputation#TarjanSccComputation(IPathNetwork)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTarjanSccComputation() {
		PathNetwork network = new PathNetwork();
		TarjanSccComputation computation = new TarjanSccComputation(network);
		PathNetwork anotherNetwork = new PathNetwork();
		TarjanSccComputation anotherComputation = new TarjanSccComputation(anotherNetwork);

		Assert.assertEquals(network, computation.getPathNetwork());
		Assert.assertEquals(anotherNetwork, anotherComputation.getPathNetwork());
	}

}
