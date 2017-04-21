package de.zabuza.pathweaver.network;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a path from a source node to a destination using edges between
 * nodes. It has properties like a length and a total cost.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
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
	private final LinkedList<DirectedWeightedEdge> mEdges;
	/**
	 * The current head edge of this path.
	 */
	private DirectedWeightedEdge mHeadEdge;
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
		this.mSource = source;
		this.mHeadEdge = null;
		this.mEdges = new LinkedList<>();
		this.mLength = EMPTY_LENGTH;
		this.mTotalCost = EMPTY_TOTAL_COST;
	}

	/**
	 * Adds the given edge to the head of the path, increasing its length.
	 * 
	 * @param edge
	 *            The edge to add
	 */
	public void addEdge(final DirectedWeightedEdge edge) {
		this.mEdges.add(edge);
		this.mTotalCost += edge.getCost();
		this.mLength++;
		this.mHeadEdge = edge;
	}

	/**
	 * Gets the total cost of this path. This value is already computed and
	 * ready for use.
	 * 
	 * @return The total cost of this path.
	 */
	public float getCost() {
		return this.mTotalCost;
	}

	/**
	 * Gets the current destination of this path.
	 * 
	 * @return The current destination of this path. If the path is empty, this
	 *         is the source node.
	 */
	public Node getDestination() {
		if (getLength() == EMPTY_LENGTH) {
			return this.mSource;
		}
		return this.mHeadEdge.getDestination();
	}

	/**
	 * Gets all edges this path consists of. The first edge starts at the source
	 * and the last ends at the destination.
	 * 
	 * @return All edges this path consists of. The first edge starts at the
	 *         source and the last ends at the destination.
	 */
	public List<DirectedWeightedEdge> getEdges() {
		return this.mEdges;
	}

	/**
	 * Gets the length of this path, i.e. the amount of nodes. This value is
	 * already computed and ready for use.
	 * 
	 * @return The length of this path, i.e. the amount of nodes.
	 */
	public int getLength() {
		return this.mLength;
	}

	/**
	 * Gets the source node of this path.
	 * 
	 * @return The source node of this path
	 */
	public Node getSource() {
		return this.mSource;
	}
}
