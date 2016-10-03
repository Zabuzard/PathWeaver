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
		Set<DirectedWeightedEdge> outgoingEdges = mNetwork.getOutgoingEdges(node);
		for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			Node successor = outgoingEdge.getDestination();
			if (mNodeToIndex.containsKey(successor)) {
				// Update the low link value if not visited the first time
				if (mInDeque.contains(successor)) {
					updateLowLink(node, mNodeToIndex.get(successor));
				}
			} else {
				// Register successor if visited the first time
				mTaskDeque.push(new TarjanTaskElement(successor, node));
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
		assert (!mNodeToIndex.containsKey(node) && !mNodeToLowLink.containsKey(node));
		mNodeToIndex.put(node, mCurrentIndex);
		mNodeToLowLink.put(node, mCurrentIndex);
		mCurrentIndex++;

		mDeque.push(node);
		mInDeque.add(node);
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
		if (mNodeToIndex.get(node).equals(mNodeToLowLink.get(node))) {
			establishScc(node);
		}
		// If node is not the root, update its predecessors low link value
		if (predecessor.isPresent()) {
			updateLowLink(predecessor.get(), mNodeToLowLink.get(node));
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
		mTaskDeque = new LinkedList<>();
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
		mTaskDeque.push(new TarjanTaskElement(node));

		// Process all task elements
		while (!mTaskDeque.isEmpty()) {
			TarjanTaskElement taskElement = mTaskDeque.pop();
			Node elementNode = taskElement.getNode();
			ETarjanTask currentTask = taskElement.getCurrentTask().get();

			if (currentTask == ETarjanTask.INDEX) {
				// Only register node if it is visited the first time
				if (!mNodeToIndex.containsKey(elementNode)) {
					doIndexTask(elementNode);

					// Push the element with the next task to the deque
					taskElement.reportTaskAccomplished();
					mTaskDeque.push(taskElement);
				}
			} else if (currentTask == ETarjanTask.GET_SUCCESSORS) {
				// Push the element with the next task to the deque
				taskElement.reportTaskAccomplished();
				mTaskDeque.push(taskElement);

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
