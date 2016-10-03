package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.zabuza.pathweaver.network.Node;

/**
 * Test for {@link StronglyConnectedComponent}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class StronglyConnectedComponentTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link StronglyConnectedComponent#addNode(Node)}.
	 */
	@Test
	public void testAddNode() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();

		scc.addNode(firstNode);
		Set<Node> nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertFalse(nodes.contains(secondNode));

		scc.addNode(secondNode);
		nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertEquals(2, scc.getSize());

		scc.addNode(thirdNode);
		scc.addNode(fourthNode);
		Assert.assertEquals(4, scc.getSize());

		nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertTrue(nodes.contains(thirdNode));
		Assert.assertTrue(nodes.contains(fourthNode));

		scc.setRootNode(firstNode);
		Node fifthNode = new Node(4);
		exception.expect(UnsupportedOperationException.class);
		scc.addNode(fifthNode);

	}

	/**
	 * Test method for {@link StronglyConnectedComponent#getNodes()}.
	 */
	@Test
	public void testGetNodes() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();

		scc.addNode(firstNode);
		Set<Node> nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertFalse(nodes.contains(secondNode));

		scc.addNode(secondNode);
		nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));

		scc.addNode(thirdNode);
		scc.addNode(fourthNode);

		nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertTrue(nodes.contains(thirdNode));
		Assert.assertTrue(nodes.contains(fourthNode));
	}

	/**
	 * Test method for {@link StronglyConnectedComponent#getRootNode()}.
	 */
	@Test
	public void testGetRootNode() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		scc.addNode(firstNode);
		scc.addNode(secondNode);

		scc.setRootNode(firstNode);
		Assert.assertEquals(firstNode, scc.getRootNode());
	}

	/**
	 * Test method for {@link StronglyConnectedComponent#getSize()}.
	 */
	@Test
	public void testGetSize() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();

		scc.addNode(firstNode);
		Assert.assertEquals(1, scc.getSize());

		scc.addNode(secondNode);
		Assert.assertEquals(2, scc.getSize());

		scc.addNode(thirdNode);
		scc.addNode(fourthNode);
		Assert.assertEquals(4, scc.getSize());
	}

	/**
	 * Test method for {@link StronglyConnectedComponent#setRootNode(Node)}.
	 */
	@Test
	public void testSetRootNode() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();

		scc.addNode(firstNode);
		scc.addNode(secondNode);

		scc.setRootNode(firstNode);
		exception.expect(UnsupportedOperationException.class);
		scc.setRootNode(secondNode);
	}

	/**
	 * Test method for
	 * {@link StronglyConnectedComponent#StronglyConnectedComponent()}.
	 */
	@Test
	public void testStronglyConnectedComponent() {
		Node firstNode = new Node(0);
		Node secondNode = new Node(1);
		Node thirdNode = new Node(2);
		Node fourthNode = new Node(3);

		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		scc.addNode(firstNode);
		scc.addNode(secondNode);
		scc.addNode(thirdNode);
		scc.addNode(fourthNode);
		Assert.assertEquals(4, scc.getSize());

		scc.setRootNode(firstNode);
		Assert.assertEquals(firstNode, scc.getRootNode());

		Set<Node> nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertTrue(nodes.contains(thirdNode));
		Assert.assertTrue(nodes.contains(fourthNode));
	}

}
