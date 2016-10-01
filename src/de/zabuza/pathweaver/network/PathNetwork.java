package de.zabuza.pathweaver.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

import de.zabuza.pathweaver.network.algorithm.scc.ISccComputation;
import de.zabuza.pathweaver.network.algorithm.scc.TarjanSccComputation;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A path network which consists of nodes and directed edges which connects the
 * nodes. Edges have costs for using them. The network can be used for computing
 * shortest paths and similar tasks.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public class PathNetwork implements IPathNetwork {
	/**
	 * Message for the exception thrown when the given nodes where not added to
	 * the network previously though needed.
	 */
	private static final String EXCEPTION_NODE_NOT_ADDED = "The given nodes must already be added to the network before using this operation.";
	/**
	 * The current amount of edges the network has.
	 */
	private int mAmountOfEdges;

	/**
	 * The current amount of nodes the network has.
	 */
	private int mAmountOfNodes;

	/**
	 * The set of nodes the network currently has, accessible by their IDs.
	 */
	private final HashMap<Integer, Node> mIdToNodes;

	/**
	 * Maps nodes to their incoming edges.
	 */
	private final HashMap<Node, HashSet<DirectedWeightedEdge>> mNodeToIncomingEdges;

	/**
	 * Maps nodes to their outgoing edges.
	 */
	private final HashMap<Node, HashSet<DirectedWeightedEdge>> mNodeToOutgoingEdges;

	/**
	 * Creates a new empty path network.
	 */
	public PathNetwork() {
		mAmountOfNodes = 0;
		mAmountOfEdges = 0;
		mIdToNodes = new HashMap<>();
		mNodeToOutgoingEdges = new HashMap<>();
		mNodeToIncomingEdges = new HashMap<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#addEdge(de.zabuza.pathweaver.
	 * network.Node, de.zabuza.pathweaver.network.Node, float)
	 */
	@Override
	public void addEdge(final Node source, final Node destination, final float cost) {
		if (!containsNodeId(source.getId()) || !containsNodeId(destination.getId())) {
			throw new IllegalArgumentException(EXCEPTION_NODE_NOT_ADDED);
		}
		DirectedWeightedEdge edge = new DirectedWeightedEdge(source, destination, cost);
		HashSet<DirectedWeightedEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			outgoingEdges = new LinkedHashSet<>();
		}
		outgoingEdges.add(edge);
		mNodeToOutgoingEdges.put(source, outgoingEdges);

		HashSet<DirectedWeightedEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			incomingEdges = new LinkedHashSet<>();
		}
		incomingEdges.add(edge);
		mNodeToIncomingEdges.put(destination, incomingEdges);

		mAmountOfEdges++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#addNode(de.zabuza.pathweaver.
	 * network.Node)
	 */
	@Override
	public boolean addNode(final Node node) {
		Node previousElement = mIdToNodes.get(node.getId());
		boolean getsAdded = previousElement == null;
		if (getsAdded) {
			mIdToNodes.put(node.getId(), node);
			mAmountOfNodes++;
			assert mAmountOfNodes == mIdToNodes.size();
		}
		return getsAdded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#containsNodeId(int)
	 */
	@Override
	public boolean containsNodeId(final int nodeId) {
		return mIdToNodes.containsKey(nodeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getAmountOfEdges()
	 */
	@Override
	public int getAmountOfEdges() {
		return mAmountOfEdges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#getIncomingEdges(de.zabuza.
	 * pathweaver.network.Node)
	 */
	@Override
	public Set<DirectedWeightedEdge> getIncomingEdges(final Node destination) {
		HashSet<DirectedWeightedEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(incomingEdges);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getNodeById(int)
	 */
	@Override
	public Node getNodeById(final int id) {
		return mIdToNodes.get(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getNodes()
	 */
	@Override
	public Collection<Node> getNodes() {
		return mIdToNodes.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#getOutgoingEdges(de.zabuza.
	 * pathweaver.network.Node)
	 */
	@Override
	public Set<DirectedWeightedEdge> getOutgoingEdges(final Node source) {
		HashSet<DirectedWeightedEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(outgoingEdges);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#getSize()
	 */
	@Override
	public int getSize() {
		return mAmountOfNodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#hasIncomingEdge(de.zabuza.
	 * pathweaver.network.Node, de.zabuza.pathweaver.network.IncomingEdge)
	 */
	@Override
	public boolean hasIncomingEdge(final Node destination, final DirectedWeightedEdge incomingEdge) {
		HashSet<DirectedWeightedEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return false;
		} else {
			return incomingEdges.contains(incomingEdge);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#hasOutgoingEdge(de.zabuza.
	 * pathweaver.network.Node, de.zabuza.pathweaver.network.OutgoingEdge)
	 */
	@Override
	public boolean hasOutgoingEdge(final Node source, final DirectedWeightedEdge outgoingEdge) {
		HashSet<DirectedWeightedEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return false;
		} else {
			return outgoingEdges.contains(outgoingEdge);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.IPathNetwork#reduceToLargestScc()
	 */
	@Override
	public void reduceToLargestScc() {
		ISccComputation sccComputation = new TarjanSccComputation(this);
		Set<Node> largestScc = sccComputation.getLargestScc();
		LinkedList<Node> nodesToRemove = new LinkedList<>();
		for (Node node : getNodes()) {
			if (largestScc.contains(node)) {
				continue;
			}
			nodesToRemove.add(node);
		}
		for (Node node : nodesToRemove) {
			removeNode(node);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.pathweaver.network.IPathNetwork#removeNode(de.zabuza.pathweaver
	 * .network.Node)
	 */
	@Override
	public void removeNode(final Node node) throws NoSuchElementException {
		if (!mIdToNodes.containsKey(node.getId())) {
			throw new NoSuchElementException();
		}

		// First remove all in- and outgoing edges
		Set<DirectedWeightedEdge> outgoingEdges = getOutgoingEdges(node);
		for (DirectedWeightedEdge outgoingEdge : outgoingEdges) {
			// Remove this edge from destinations incoming edges
			Node destination = outgoingEdge.getDestination();
			mNodeToIncomingEdges.get(destination).remove(outgoingEdge);
			mAmountOfEdges--;
		}
		Set<DirectedWeightedEdge> incomingEdges = getIncomingEdges(node);
		for (DirectedWeightedEdge incomingEdge : incomingEdges) {
			// Remove this edge from sources outgoing edges
			Node source = incomingEdge.getSource();
			mNodeToOutgoingEdges.get(source).remove(incomingEdge);
			mAmountOfEdges--;
		}

		// At this point no other node has a link to the node to delete
		// Now delete the node and its links
		mNodeToIncomingEdges.remove(node);
		mNodeToOutgoingEdges.remove(node);
		mIdToNodes.remove(node.getId());
		mAmountOfNodes--;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Network[");
		builder.append("#nodes=" + mAmountOfNodes);
		builder.append(",#edges=" + mAmountOfEdges);
		builder.append(",edges={");
		for (Entry<Node, HashSet<DirectedWeightedEdge>> entry : mNodeToOutgoingEdges.entrySet()) {
			Node source = entry.getKey();
			for (DirectedWeightedEdge outgoingEdge : entry.getValue()) {
				Node destination = outgoingEdge.getDestination();
				float cost = outgoingEdge.getCost();
				builder.append("(" + source + "-" + cost + "->" + destination + "),");
			}
		}
		if (!mNodeToOutgoingEdges.isEmpty()) {
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("}");
		builder.append("]");

		return builder.toString();
	}
}
