package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Non-recursive implementation of Tarjans strongly connected component
 * algorithm which solves strongly connected component computation tasks in
 * {@link PathNetwork}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class TarjanNonRecursiveSccComputation extends TarjanSccComputation {

	/**
	 * The deque containing all task elements to process.
	 */
	private Deque<TarjanTaskElement> mTaskDeque;

	/**
	 * Creates a new strongly connected component computation object and starts
	 * the computation.
	 * 
	 * @param network
	 *            The network to work on
	 */
	public TarjanNonRecursiveSccComputation(final IPathNetwork network) {
		super(network);
	}

	/**
	 * Processes the successors of the given node.
	 * 
	 * @param node
	 *            The node to process its successors
	 */
	private void doGetSuccessorsTask(final Node node) {
		final Set<DirectedWeightedEdge> outgoingEdges = this.mNetwork.getOutgoingEdges(node);
		for (final DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			final Node successor = outgoingEdge.getDestination();
			if (this.mNodeToIndex.containsKey(successor)) {
				// Update the low link value if not visited the first time
				if (this.mInDeque.contains(successor)) {
					updateLowLink(node, this.mNodeToIndex.get(successor).intValue());
				}
			} else {
				// Register successor if visited the first time
				this.mTaskDeque.push(new TarjanTaskElement(successor, node));
			}
		}
	}

	/**
	 * Registers the given node to be processed.
	 * 
	 * @param node
	 *            Node to register
	 */
	private void doIndexTask(final Node node) {
		assert (!this.mNodeToIndex.containsKey(node) && !this.mNodeToLowLink.containsKey(node));
		this.mNodeToIndex.put(node, Integer.valueOf(this.mCurrentIndex));
		this.mNodeToLowLink.put(node, Integer.valueOf(this.mCurrentIndex));
		this.mCurrentIndex++;

		this.mDeque.push(node);
		this.mInDeque.add(node);
	}

	/**
	 * Finishes this element by updating its low link value or establishing a
	 * new SCC.
	 * 
	 * @param node
	 *            The node to process
	 * @param predecessor
	 *            The predecessor of the node if present
	 */
	private void doSetLowLinkTask(final Node node, final Optional<Node> predecessor) {
		// If this values low link value is equals to its index, then it is the
		// root of this SCC.
		if (this.mNodeToIndex.get(node).equals(this.mNodeToLowLink.get(node))) {
			establishScc(node);
		}
		// If node is not the root, update its predecessors low link value
		if (predecessor.isPresent()) {
			updateLowLink(predecessor.get(), this.mNodeToLowLink.get(node).intValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.algorithm.scc.TarjanSccComputation#clear()
	 */
	@Override
	protected void clear() {
		super.clear();
		this.mTaskDeque = new LinkedList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.scc.TarjanSccComputation#
	 * strongConnect(de.zabuza.pathweaver.network.Node)
	 */
	@Override
	protected void strongConnect(final Node node) {
		// Push the starting task element
		this.mTaskDeque.push(new TarjanTaskElement(node));

		// Process all task elements
		while (!this.mTaskDeque.isEmpty()) {
			final TarjanTaskElement taskElement = this.mTaskDeque.pop();
			final Node elementNode = taskElement.getNode();
			final ETarjanTask currentTask = taskElement.getCurrentTask().get();

			if (currentTask == ETarjanTask.INDEX) {
				// Only register node if it is visited the first time
				if (!this.mNodeToIndex.containsKey(elementNode)) {
					doIndexTask(elementNode);

					// Push the element with the next task to the deque
					taskElement.reportTaskAccomplished();
					this.mTaskDeque.push(taskElement);
				}
			} else if (currentTask == ETarjanTask.GET_SUCCESSORS) {
				// Push the element with the next task to the deque
				taskElement.reportTaskAccomplished();
				this.mTaskDeque.push(taskElement);

				doGetSuccessorsTask(elementNode);
			} else if (currentTask == ETarjanTask.SET_LOWLINK) {
				doSetLowLinkTask(elementNode, taskElement.getPredecessor());

				// All task are accomplished, do not add the element again
				taskElement.reportTaskAccomplished();
			} else {
				throw new AssertionError();
			}
		}
	}
}
