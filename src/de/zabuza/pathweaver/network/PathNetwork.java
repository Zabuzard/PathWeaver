package de.zabuza.pathweaver.network;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * A path network which consists of nodes and directed edges which connects the
 * nodes. Edges have costs for using them. The network can be used for computing
 * shortest paths and similar tasks.
 * 
 * @author Zabuza
 *
 */
public class PathNetwork {
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
	 * The set of nodes the network currently has.
	 */
	private final HashSet<Node> mNodes;

	/**
	 * Maps nodes to their incoming edges.
	 */
	private final HashMap<Node, HashSet<IncomingEdge>> mNodeToIncomingEdges;

	/**
	 * Maps nodes to their outgoing edges.
	 */
	private final HashMap<Node, HashSet<OutgoingEdge>> mNodeToOutgoingEdges;

	/**
	 * Creates a new empty path network.
	 */
	public PathNetwork() {
		mAmountOfNodes = 0;
		mAmountOfEdges = 0;
		mNodes = new HashSet<>();
		mNodeToOutgoingEdges = new HashMap<>();
		mNodeToIncomingEdges = new HashMap<>();
	}

	/**
	 * Adds an directed edge with a given source and destination node. Also a
	 * cost for using the edge must be given. Be aware that this method allows
	 * adding an edge multiple times.
	 * 
	 * @param source
	 *            The source of the directed edge
	 * @param destination
	 *            The destination of the directed edge
	 * @param cost
	 *            The cost of the edge which must be inside the limit specified
	 *            by {@link ADirectedEdge#ADirectedEdge(Node, int)
	 *            ADirectedEdge(Node, int)}.
	 */
	public void addEdge(final Node source, final Node destination, final int cost) {
		if (!containsNode(source) || !containsNode(destination)) {
			throw new IllegalArgumentException(EXCEPTION_NODE_NOT_ADDED);
		}
		OutgoingEdge outgoingEdge = new OutgoingEdge(destination, cost);
		HashSet<OutgoingEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			outgoingEdges = new LinkedHashSet<>();
		}
		outgoingEdges.add(outgoingEdge);
		mNodeToOutgoingEdges.put(source, outgoingEdges);

		IncomingEdge incomingEdge = new IncomingEdge(source, cost);
		HashSet<IncomingEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			incomingEdges = new LinkedHashSet<>();
		}
		incomingEdges.add(incomingEdge);
		mNodeToIncomingEdges.put(destination, incomingEdges);

		mAmountOfEdges++;
	}

	/**
	 * Adds the given node to the graph if not already contained.
	 * 
	 * @param node
	 *            The node to add
	 * @return <tt>True</tt> if the node was added, i.e. it was not already
	 *         contained. <tt>False</tt> otherwise.
	 */
	public boolean addNode(final Node node) {
		boolean wasAdded = mNodes.add(node);
		if (wasAdded) {
			mAmountOfNodes++;
			assert mAmountOfNodes == mNodes.size();
		}
		return wasAdded;
	}

	/**
	 * Returns whether the network contains the given node or not.
	 * 
	 * @param node
	 *            The node to check
	 * @return <tt>True</tt> if the node is contained in the network,
	 *         <tt>false</tt> otherwise
	 */
	public boolean containsNode(final Node node) {
		return mNodes.contains(node);
	}

	/**
	 * Gets the amount of edges the network currently has.
	 * 
	 * @return The amount of edges the network currently has.
	 */
	public int getAmountOfEdges() {
		return mAmountOfEdges;
	}

	/**
	 * Gets the amount of nodes the network currently has.
	 * 
	 * @return The amount of nodes the network currently has.
	 */
	public int getAmountOfNodes() {
		return mAmountOfNodes;
	}

	/**
	 * Gets an unmodifiable set of all incoming edges the given destination has
	 * or an <tt>empty set</tt> if there are no.
	 * 
	 * @param destination
	 *            The destination to get its incoming edges from
	 * @return An unmodifiable set of all incoming edges the given destination
	 *         has or an <tt>empty set</tt> if there are no
	 */
	public Set<IncomingEdge> getIncomingEdges(final Node destination) {
		HashSet<IncomingEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(incomingEdges);
		}
	}

	/**
	 * Gets an unmodifiable set of all outgoing edges the given source has or an
	 * <tt>empty set</tt> if there are no.
	 * 
	 * @param source
	 *            The source to get its outgoing edges from
	 * @return An unmodifiable set of all outgoing edges the given source has or
	 *         an <tt>empty set</tt> if there are no
	 */
	public Set<OutgoingEdge> getOutgoingEdges(final Node source) {
		HashSet<OutgoingEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return Collections.emptySet();
		} else {
			return Collections.unmodifiableSet(outgoingEdges);
		}
	}

	/**
	 * Returns whether the node has the given incoming edge or not.
	 * 
	 * @param destination
	 *            The destination to check
	 * @param incomingEdge
	 *            The incoming edge to check
	 * @return <tt>True</tt> if the node has the incoming edge, <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasIncomingEdge(final Node destination, final IncomingEdge incomingEdge) {
		HashSet<IncomingEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			return false;
		} else {
			return incomingEdges.contains(incomingEdge);
		}
	}

	/**
	 * Returns whether the node has the given outgoing edge or not.
	 * 
	 * @param source
	 *            The source to check
	 * @param outgoingEdge
	 *            The outgoing edge to check
	 * @return <tt>True</tt> if the node has the outgoing edge, <tt>false</tt>
	 *         otherwise
	 */
	public boolean hasOutgoingEdge(final Node source, final OutgoingEdge outgoingEdge) {
		HashSet<OutgoingEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			return false;
		} else {
			return outgoingEdges.contains(outgoingEdge);
		}
	}

	/**
	 * Reduces the graph to its largest strongly connected component. Inside
	 * such a component every node is reachable from all others.
	 */
	public void reduceToLargestScc() {
		// TODO Implement method
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
		for (Entry<Node, HashSet<OutgoingEdge>> entry : mNodeToOutgoingEdges.entrySet()) {
			Node source = entry.getKey();
			for (OutgoingEdge outgoingEdge : entry.getValue()) {
				Node destination = outgoingEdge.getDestination();
				int cost = outgoingEdge.getCost();
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
