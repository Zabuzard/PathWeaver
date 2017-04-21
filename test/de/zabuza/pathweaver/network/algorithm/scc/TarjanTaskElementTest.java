package de.zabuza.pathweaver.network.algorithm.scc;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.zabuza.pathweaver.network.Node;

/**
 * Test for {@link TarjanTaskElement}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TarjanTaskElementTest {
	/**
	 * Rule for expecting exceptions.
	 */
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	/**
	 * Test method for {@link TarjanTaskElement#getCurrentTask()}.
	 */
	@Test
	public void testGetCurrentTask() {
		Node node = new Node(0);
		TarjanTaskElement taskElement = new TarjanTaskElement(node);

		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.INDEX, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.GET_SUCCESSORS, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.SET_LOWLINK, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertFalse(taskElement.getCurrentTask().isPresent());

		this.exception.expect(IllegalStateException.class);
		taskElement.reportTaskAccomplished();
		Assert.assertFalse(taskElement.getCurrentTask().isPresent());
	}

	/**
	 * Test method for {@link TarjanTaskElement#getNode()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetNode() {
		Node node = new Node(0);
		Node anotherNode = new Node(1);
		TarjanTaskElement taskElement = new TarjanTaskElement(node);
		TarjanTaskElement anotherTaskElement = new TarjanTaskElement(anotherNode);

		Assert.assertEquals(node, taskElement.getNode());
		Assert.assertEquals(anotherNode, anotherTaskElement.getNode());
	}

	/**
	 * Test method for {@link TarjanTaskElement#getPredecessor()}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetPredecessor() {
		Node node = new Node(0);
		Node predecessor = new Node(1);
		Node anotherPredecessor = new Node(2);
		TarjanTaskElement taskElement = new TarjanTaskElement(node, predecessor);
		TarjanTaskElement anotherTaskElement = new TarjanTaskElement(node, anotherPredecessor);

		Assert.assertTrue(taskElement.getPredecessor().isPresent());
		Assert.assertEquals(predecessor, taskElement.getPredecessor().get());
		Assert.assertTrue(anotherTaskElement.getPredecessor().isPresent());
		Assert.assertEquals(anotherPredecessor, anotherTaskElement.getPredecessor().get());

		TarjanTaskElement yetAnothertaskElement = new TarjanTaskElement(node);
		Assert.assertFalse(yetAnothertaskElement.getPredecessor().isPresent());
	}

	/**
	 * Test method for {@link TarjanTaskElement#reportTaskAccomplished()}.
	 */
	@Test
	public void testReportTaskAccomplished() {
		Node node = new Node(0);
		TarjanTaskElement taskElement = new TarjanTaskElement(node);

		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.INDEX, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.GET_SUCCESSORS, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.SET_LOWLINK, taskElement.getCurrentTask().get());

		taskElement.reportTaskAccomplished();
		Assert.assertFalse(taskElement.getCurrentTask().isPresent());

		this.exception.expect(IllegalStateException.class);
		taskElement.reportTaskAccomplished();
		Assert.assertFalse(taskElement.getCurrentTask().isPresent());
	}

	/**
	 * Test method for {@link TarjanTaskElement#TarjanTaskElement(Node)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTarjanTaskElementNode() {
		Node node = new Node(0);
		TarjanTaskElement taskElement = new TarjanTaskElement(node);

		Assert.assertEquals(node, taskElement.getNode());
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.INDEX, taskElement.getCurrentTask().get());
		Assert.assertFalse(taskElement.getPredecessor().isPresent());
	}

	/**
	 * Test method for {@link TarjanTaskElement#TarjanTaskElement(Node, Node)}.
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testTarjanTaskElementNodeNode() {
		Node node = new Node(0);
		Node predecessor = new Node(1);
		TarjanTaskElement taskElement = new TarjanTaskElement(node, predecessor);

		Assert.assertEquals(node, taskElement.getNode());
		Assert.assertTrue(taskElement.getCurrentTask().isPresent());
		Assert.assertEquals(ETarjanTask.INDEX, taskElement.getCurrentTask().get());
		Assert.assertTrue(taskElement.getPredecessor().isPresent());
		Assert.assertEquals(predecessor, taskElement.getPredecessor().get());
	}

}
