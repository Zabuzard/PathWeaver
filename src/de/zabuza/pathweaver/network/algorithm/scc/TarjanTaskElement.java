package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.Optional;

import de.zabuza.pathweaver.network.Node;

/**
 * Task element of Tarjans algorithm for SCC computation which wanders through
 * several {@link ETarjanTask}s until completion.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class TarjanTaskElement {
	/**
	 * Message which is shown when trying to accomplish a task though all tasks
	 * where already accomplished.
	 */
	private final static String TASKS_ALREADY_ACCOMPLISHED = "All task already where accomplished.";
	/**
	 * The current task of this element if present.
	 */
	private Optional<ETarjanTask> mCurrentTask;
	/**
	 * The node this element belongs to.
	 */
	private final Node mNode;

	/**
	 * The predecessor of this elements node if present.
	 */
	private final Optional<Node> mPredecessor;

	/**
	 * Creates a new task element with a given node and no predecessor. The
	 * current task is set to the starting task.
	 * 
	 * @param node
	 *            The node this element belongs to
	 */
	public TarjanTaskElement(final Node node) {
		this(node, Optional.empty());
	}

	/**
	 * Creates a new task element with a given node and a predecessor. The
	 * current task is set to the starting task.
	 * 
	 * @param node
	 *            The node this element belongs to
	 * @param predecessor
	 *            The predecessor of this elements node
	 */
	public TarjanTaskElement(final Node node, final Node predecessor) {
		this(node, Optional.of(predecessor));
	}

	/**
	 * Creates a new task element with a given node and a predecessor, if
	 * present. The current task is set to the starting task.
	 * 
	 * @param node
	 *            The node this element belongs to
	 * @param predecessor
	 *            The predecessor of this elements node if present
	 */
	private TarjanTaskElement(final Node node, final Optional<Node> predecessor) {
		this.mNode = node;
		this.mPredecessor = predecessor;
		this.mCurrentTask = Optional.of(ETarjanTask.INDEX);
	}

	/**
	 * Gets the current task if present.
	 * 
	 * @return The current task if present
	 */
	public Optional<ETarjanTask> getCurrentTask() {
		return this.mCurrentTask;
	}

	/**
	 * Gets the node this element belongs to.
	 * 
	 * @return The node this element belongs to
	 */
	public Node getNode() {
		return this.mNode;
	}

	/**
	 * Gets the predecessor of this elements node if present.
	 * 
	 * @return The predecessor of this elements node if present
	 */
	public Optional<Node> getPredecessor() {
		return this.mPredecessor;
	}

	/**
	 * Reports the current task as accomplished and switches to the next task.
	 * 
	 * @throws IllegalStateException
	 *             If all tasks where already accomplished
	 */
	public void reportTaskAccomplished() throws IllegalStateException {
		if (this.mCurrentTask.isPresent()) {
			ETarjanTask task = this.mCurrentTask.get();
			if (task == ETarjanTask.INDEX) {
				this.mCurrentTask = Optional.of(ETarjanTask.GET_SUCCESSORS);
			} else if (task == ETarjanTask.GET_SUCCESSORS) {
				this.mCurrentTask = Optional.of(ETarjanTask.SET_LOWLINK);
			} else if (task == ETarjanTask.SET_LOWLINK) {
				this.mCurrentTask = Optional.empty();
			} else {
				throw new AssertionError();
			}
		} else {
			throw new IllegalStateException(TASKS_ALREADY_ACCOMPLISHED);
		}
	}
}
