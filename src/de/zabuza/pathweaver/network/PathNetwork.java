package de.zabuza.pathweaver.network;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;

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
	private final HashMap<Node, LinkedList<IncomingEdge>> mNodeToIncomingEdges;

	/**
	 * Maps nodes to their outgoing edges.
	 */
	private final HashMap<Node, LinkedList<OutgoingEdge>> mNodeToOutgoingEdges;

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
		OutgoingEdge outgoingEdge = new OutgoingEdge(destination, cost);
		LinkedList<OutgoingEdge> outgoingEdges = mNodeToOutgoingEdges.get(source);
		if (outgoingEdges == null) {
			outgoingEdges = new LinkedList<>();
		}
		outgoingEdges.add(outgoingEdge);
		mNodeToOutgoingEdges.put(source, outgoingEdges);

		IncomingEdge incomingEdge = new IncomingEdge(source, cost);
		LinkedList<IncomingEdge> incomingEdges = mNodeToIncomingEdges.get(destination);
		if (incomingEdges == null) {
			incomingEdges = new LinkedList<>();
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
		for (Entry<Node, LinkedList<OutgoingEdge>> entry : mNodeToOutgoingEdges.entrySet()) {
			Node source = entry.getKey();
			for (OutgoingEdge outgoingEdge : entry.getValue()) {
				Node destination = outgoingEdge.getDestination();
				int cost = outgoingEdge.getCost();
				builder.append("(" + source + "-" + cost + "->" + destination + "),");
			}
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("}");
		builder.append("]");

		return builder.toString();
	}
}
