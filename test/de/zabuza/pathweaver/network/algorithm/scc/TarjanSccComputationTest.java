package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

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
	@Test
	public void testGetLargestScc() {
		PathNetwork network = new PathNetwork();
		TarjanSccComputation computation = new TarjanSccComputation(network);

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

		Set<Node> scc = computation.getLargestScc();
		Assert.assertEquals(6, scc.size());
		Assert.assertTrue(scc.contains(firstNode));
		Assert.assertTrue(scc.contains(secondNode));
		Assert.assertTrue(scc.contains(thirdNode));
		Assert.assertTrue(scc.contains(fourthNode));
		Assert.assertTrue(scc.contains(fifthNode));
		Assert.assertTrue(scc.contains(sixthNode));

		Assert.assertFalse(scc.contains(seventhNode));
		Assert.assertFalse(scc.contains(eighthNode));
		Assert.assertFalse(scc.contains(ninthNode));
		Assert.assertFalse(scc.contains(tenthNode));
	}

	/**
	 * Test method for {@link TarjanSccComputation#getPathNetwork()}.
	 */
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
	@Test
	public void testGetSccs() {
		PathNetwork network = new PathNetwork();
		TarjanSccComputation computation = new TarjanSccComputation(network);

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

		List<Set<Node>> sccs = computation.getSccs();
		Assert.assertEquals(3, sccs.size());

		boolean foundLargeScc = false;
		for (Set<Node> scc : sccs) {
			if (scc.size() == 6) {
				foundLargeScc = true;
				break;
			}
		}
		Assert.assertTrue(foundLargeScc);

		boolean foundMiddleScc = false;
		for (Set<Node> scc : sccs) {
			if (scc.size() == 3) {
				foundMiddleScc = true;
				Assert.assertTrue(scc.contains(seventhNode));
				Assert.assertTrue(scc.contains(eighthNode));
				Assert.assertTrue(scc.contains(ninthNode));
				break;
			}
		}
		Assert.assertTrue(foundMiddleScc);

		boolean foundSmallScc = false;
		for (Set<Node> scc : sccs) {
			if (scc.size() == 1) {
				foundSmallScc = true;
				Assert.assertTrue(scc.contains(tenthNode));
				break;
			}
		}
		Assert.assertTrue(foundSmallScc);
	}

	/**
	 * Test method for
	 * {@link TarjanSccComputation#TarjanSccComputation(IPathNetwork)}.
	 */
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