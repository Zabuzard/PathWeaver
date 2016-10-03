package de.zabuza.pathweaver.network.algorithm.scc;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Tarjans strongly connected component algorithm which solves strongly
 * connected component computation tasks in {@link PathNetwork}s.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class TarjanSccComputation implements ISccComputation {
	/**
	 * The index for nodes to start with.
	 */
	private static final int STARTING_INDEX = 0;
	/**
	 * The currently largest known SCC.
	 */
	private StronglyConnectedComponent mLargestKnownScc;
	/**
	 * The size of the currently largest known SCC.
	 */
	private int mLargestKnownSccSize;
	/**
	 * The current list of known SCCs.
	 */
	private List<StronglyConnectedComponent> mSccs;
	/**
	 * The current index for nodes to use.
	 */
	protected int mCurrentIndex;
	/**
	 * A deque which contains nodes to process.
	 */
	protected Deque<Node> mDeque;
	/**
	 * Set of nodes that currently are in the deque.
	 */
	protected HashSet<Node> mInDeque;
	/**
	 * The path network this object works on.
	 */
	protected final IPathNetwork mNetwork;
	/**
	 * Maps nodes to their assigned index.
	 */
	protected HashMap<Node, Integer> mNodeToIndex;
	/**
	 * Maps nodes to their low link value.
	 */
	protected HashMap<Node, Integer> mNodeToLowLink;

	/**
	 * Creates a new strongly connected component computation object and starts
	 * the computation.
	 * 
	 * @param network
	 *            The network to work on
	 */
	public TarjanSccComputation(final IPathNetwork network) {
		mNetwork = network;

		clear();
		for (Node node : getPathNetwork().getNodes()) {
			if (!mNodeToIndex.containsKey(node)) {
				strongConnect(node);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.algorithm.scc.ISccComputation#getLargestScc(
	 * )
	 */
	@Override
	public StronglyConnectedComponent getLargestScc() {
		return mLargestKnownScc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.algorithm.scc.ISccComputation#getPathNetwork
	 * ()
	 */
	@Override
	public IPathNetwork getPathNetwork() {
		return mNetwork;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.scc.ISccComputation#getSccs()
	 */
	@Override
	public List<StronglyConnectedComponent> getSccs() {
		return mSccs;
	}

	/**
	 * Processes the successor of the given node.
	 * 
	 * @param node
	 *            Node to process
	 * @param successor
	 *            Successor to process
	 */
	private void processSuccessor(final Node node, final Node successor) {
		if (!mNodeToIndex.containsKey(successor)) {
			strongConnect(successor);
			updateLowLink(node, mNodeToLowLink.get(successor));
		} else if (mInDeque.contains(successor)) {
			updateLowLink(node, mNodeToIndex.get(successor));
		}
	}

	/**
	 * Clears the internal used structures and prepares them for the
	 * computation.
	 */
	protected void clear() {
		IPathNetwork network = getPathNetwork();
		mNodeToIndex = new HashMap<>(network.getSize());
		mNodeToLowLink = new HashMap<>(network.getSize());
		mDeque = new LinkedList<>();
		mInDeque = new HashSet<>();
		mSccs = new LinkedList<>();
		mCurrentIndex = STARTING_INDEX;
		mLargestKnownSccSize = -1;
	}

	/**
	 * Establishes a new SCC using the elements currently in the deque.
	 * 
	 * @param rootNode
	 *            The root node of this SCC
	 */
	protected void establishScc(final Node rootNode) {
		StronglyConnectedComponent scc = new StronglyConnectedComponent();
		Node dequeElement;
		do {
			dequeElement = mDeque.pop();
			mInDeque.remove(dequeElement);
			scc.addNode(dequeElement);
		} while (!rootNode.equals(dequeElement));

		scc.setRootNode(rootNode);
		mSccs.add(scc);

		// Update largest known values
		int sccSize = scc.getSize();
		if (sccSize > mLargestKnownSccSize) {
			mLargestKnownSccSize = sccSize;
			mLargestKnownScc = scc;
		}
	}

	/**
	 * Creates the strongly connected component of the given node and all its
	 * successing nodes.
	 * 
	 * @param node
	 *            Node to connect
	 */
	protected void strongConnect(final Node node) {
		assert (!mNodeToIndex.containsKey(node) && !mNodeToLowLink.containsKey(node));
		IPathNetwork network = getPathNetwork();
		mNodeToIndex.put(node, mCurrentIndex);
		mNodeToLowLink.put(node, mCurrentIndex);
		mCurrentIndex++;

		mDeque.push(node);
		mInDeque.add(node);

		Set<DirectedWeightedEdge> outgoingEdges = network.getOutgoingEdges(node);

		// Start a depth-first-search over all successors
		for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			Node successor = outgoingEdge.getDestination();
			processSuccessor(node, successor);
		}

		// At this point all, from this node, reachable nodes where visited.
		// If this values low link value is equals to its index, then it is the
		// root of this SCC.
		if (mNodeToIndex.get(node).equals(mNodeToLowLink.get(node))) {
			establishScc(node);
		}
	}

	/**
	 * Updates the low link value of the given node if the value candidate is
	 * smaller then the currently set value.
	 * 
	 * @param node
	 *            Node to update its low link value
	 * @param valueCandidate
	 *            The candidate for the new value
	 */
	protected void updateLowLink(final Node node, final int valueCandidate) {
		int minValue = Math.min(mNodeToLowLink.get(node), valueCandidate);
		mNodeToLowLink.put(node, minValue);
	}
}
