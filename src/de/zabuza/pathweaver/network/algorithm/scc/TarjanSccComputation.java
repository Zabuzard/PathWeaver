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
 * @author Zabuza
 *
 */
public final class TarjanSccComputation implements ISccComputation {
	/**
	 * The index for nodes to start with.
	 */
	private static final int STARTING_INDEX = 0;
	/**
	 * The current index for nodes to use.
	 */
	private int mCurrentIndex;
	/**
	 * A deque which contains nodes to work.
	 */
	private Deque<Node> mDeque;
	/**
	 * Set of nodes that currently are in the deque.
	 */
	private HashSet<Node> mInDeque;
	/**
	 * The path network this object works on.
	 */
	private final IPathNetwork mNetwork;
	/**
	 * Maps nodes to their assigned index.
	 */
	private HashMap<Node, Integer> mNodeToIndex;
	/**
	 * Maps nodes to their low link value.
	 */
	private HashMap<Node, Integer> mNodeToLowLink;
	/**
	 * The current list of known SCCs.
	 */
	private List<Set<Node>> mSccs;

	/**
	 * Creates a new strongly connected component computation object
	 * 
	 * @param network
	 *            The network to work on
	 */
	public TarjanSccComputation(final IPathNetwork network) {
		mNetwork = network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.algorithm.scc.ISccComputation#getLargestScc(
	 * )
	 */
	@Override
	public Set<Node> getLargestScc() {
		int largestKnownSize = 0;
		Set<Node> largestKnownScc = null;
		for (Set<Node> scc : getSccs()) {
			int size = scc.size();
			if (size > largestKnownSize) {
				largestKnownScc = scc;
				largestKnownSize = size;
			}
		}
		return largestKnownScc;
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
	public List<Set<Node>> getSccs() {
		clear();
		for (Node node : getPathNetwork().getNodes()) {
			if (!mNodeToIndex.containsKey(node)) {
				strongConnect(node);
			}
		}

		return mSccs;
	}

	/**
	 * Clears the internal used structures and prepares them for a new task.
	 */
	private void clear() {
		IPathNetwork network = getPathNetwork();
		mNodeToIndex = new HashMap<>(network.getSize());
		mNodeToLowLink = new HashMap<>(network.getSize());
		mDeque = new LinkedList<Node>();
		mInDeque = new HashSet<Node>();
		mSccs = new LinkedList<>();
		mCurrentIndex = STARTING_INDEX;
	}

	/**
	 * Creates the strongly connected component of the given node and all its
	 * successing nodes.
	 * 
	 * @param node
	 *            Node to connect
	 */
	private void strongConnect(final Node node) {
		IPathNetwork network = getPathNetwork();
		mNodeToIndex.put(node, mCurrentIndex);
		mNodeToLowLink.put(node, mCurrentIndex);
		assert (!mNodeToIndex.containsKey(node) && mNodeToLowLink.containsKey(node));
		mCurrentIndex++;

		mDeque.push(node);
		mInDeque.add(node);

		Set<DirectedWeightedEdge> outgoingEdges = network.getOutgoingEdges(node);

		for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			Node successor = outgoingEdge.getDestination();
			if (!mNodeToIndex.containsKey(successor)) {
				strongConnect(successor);
				if (mNodeToLowLink.get(successor) < mNodeToLowLink.get(node)) {
					mNodeToLowLink.put(node, mNodeToLowLink.get(successor));
				}
			} else if (mInDeque.contains(successor)) {
				if (mNodeToIndex.get(successor) < mNodeToLowLink.get(node)) {
					mNodeToLowLink.put(node, mNodeToIndex.get(successor));
				}
			}
		}

		if (mNodeToIndex.get(node).equals(mNodeToLowLink.get(node))) {
			HashSet<Node> scc = new HashSet<>();
			Node element;
			do {
				element = mDeque.pop();
				mInDeque.remove(element);
				scc.add(element);
			} while (!element.equals(node));

			mSccs.add(scc);
		}
	}

}
