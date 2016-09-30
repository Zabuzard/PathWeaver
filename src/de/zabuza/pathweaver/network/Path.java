package de.zabuza.pathweaver.network;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a path from a source node to a destination using edges between
 * nodes. It has properties like a length and a total cost.
 * 
 * @author Zabuza
 *
 */
public final class Path {
	/**
	 * The length for an empty path, it only contains the source.
	 */
	private static final int EMPTY_LENGTH = 1;
	/**
	 * The total cost for an empty path, it only contains the source.
	 */
	private static final float EMPTY_TOTAL_COST = 0f;

	/**
	 * A list of all edges this path represents.
	 */
	private final LinkedList<OutgoingEdge> mEdges;
	/**
	 * The current head edge of this path.
	 */
	private OutgoingEdge mHeadEdge;
	/**
	 * The length of this path, i.e. the amount of nodes.
	 */
	private int mLength;
	/**
	 * The source node of this path.
	 */
	private final Node mSource;
	/**
	 * The total cost of this path, i.e. the sum of all costs from its edges.
	 */
	private float mTotalCost;

	/**
	 * Creates a new empty path which starts at the given source node.
	 * 
	 * @param source
	 *            The source node for this path
	 */
	public Path(final Node source) {
		mSource = source;
		mHeadEdge = null;
		mEdges = new LinkedList<>();
		mLength = EMPTY_LENGTH;
		mTotalCost = EMPTY_TOTAL_COST;
	}

	/**
	 * Adds the given edge to the head of the path, increasing its length.
	 * 
	 * @param edge
	 *            The edge to add
	 */
	public void addEdge(final OutgoingEdge edge) {
		mEdges.add(edge);
		mTotalCost += edge.getCost();
		mLength++;
		mHeadEdge = edge;
	}

	/**
	 * Gets the total cost of this path. This value is already computed and
	 * ready for use.
	 * 
	 * @return The total cost of this path.
	 */
	public float getCost() {
		return mTotalCost;
	}

	/**
	 * Gets the current destination of this path.
	 * 
	 * @return The current destination of this path. If the path is empty, this
	 *         is the source node.
	 */
	public Node getDestination() {
		if (getLength() == EMPTY_LENGTH) {
			return mSource;
		} else {
			return mHeadEdge.getDestination();
		}
	}

	/**
	 * Gets all edges this path consists of. The first edge starts at the source
	 * and the last ends at the destination.
	 * 
	 * @return All edges this path consists of. The first edge starts at the
	 *         source and the last ends at the destination.
	 */
	public List<OutgoingEdge> getEdges() {
		return mEdges;
	}

	/**
	 * Gets the length of this path, i.e. the amount of nodes. This value is
	 * already computed and ready for use.
	 * 
	 * @return The length of this path, i.e. the amount of nodes.
	 */
	public int getLength() {
		return mLength;
	}

	/**
	 * Gets the source node of this path.
	 * 
	 * @return The source node of this path
	 */
	public Node getSource() {
		return mSource;
	}
}
