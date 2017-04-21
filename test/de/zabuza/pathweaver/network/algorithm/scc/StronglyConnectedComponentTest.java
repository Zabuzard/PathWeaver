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
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();

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
		final Node fifthNode = new Node(4);
		this.exception.expect(UnsupportedOperationException.class);
		scc.addNode(fifthNode);

	}

	/**
	 * Test method for {@link StronglyConnectedComponent#getNodes()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetNodes() {
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();

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
	@SuppressWarnings("static-method")
	@Test
	public void testGetRootNode() {
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();
		scc.addNode(firstNode);
		scc.addNode(secondNode);

		scc.setRootNode(firstNode);
		Assert.assertEquals(firstNode, scc.getRootNode());
	}

	/**
	 * Test method for {@link StronglyConnectedComponent#getSize()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSize() {
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();

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
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();

		scc.addNode(firstNode);
		scc.addNode(secondNode);

		scc.setRootNode(firstNode);
		this.exception.expect(UnsupportedOperationException.class);
		scc.setRootNode(secondNode);
	}

	/**
	 * Test method for
	 * {@link StronglyConnectedComponent#StronglyConnectedComponent()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStronglyConnectedComponent() {
		final Node firstNode = new Node(0);
		final Node secondNode = new Node(1);
		final Node thirdNode = new Node(2);
		final Node fourthNode = new Node(3);

		final StronglyConnectedComponent scc = new StronglyConnectedComponent();
		scc.addNode(firstNode);
		scc.addNode(secondNode);
		scc.addNode(thirdNode);
		scc.addNode(fourthNode);
		Assert.assertEquals(4, scc.getSize());

		scc.setRootNode(firstNode);
		Assert.assertEquals(firstNode, scc.getRootNode());

		final Set<Node> nodes = scc.getNodes();
		Assert.assertTrue(nodes.contains(firstNode));
		Assert.assertTrue(nodes.contains(secondNode));
		Assert.assertTrue(nodes.contains(thirdNode));
		Assert.assertTrue(nodes.contains(fourthNode));
	}

}
