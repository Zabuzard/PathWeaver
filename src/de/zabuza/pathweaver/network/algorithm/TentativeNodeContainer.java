package de.zabuza.pathweaver.network.algorithm;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.Node;

/**
 * Container which assigns a given node tentative data.
 * 
 * @author Zabuza
 *
 */
public final class TentativeNodeContainer implements Comparable<TentativeNodeContainer> {
	/**
	 * The default estimated cost to reach the destination.
	 */
	public final static int DEFAULT_EST_COST_TO_DEST = 0;
	/**
	 * The estimated remaining cost to reach the destination.
	 */
	private final float mEstCostToDest;
	/**
	 * The node of this container.
	 */
	private final Node mNode;
	/**
	 * The edge pointing to the node which this container belongs to.
	 */
	private final DirectedWeightedEdge mParentEdge;
	/**
	 * The tentative cost of this container.
	 */
	private final float mTentativeCost;

	/**
	 * Creates a new container for a node which stores tentative data. The
	 * estimated cost for reaching the destination from this node is set to
	 * {@link DEFAULT_EST_COST_TO_DEST}.
	 * 
	 * @param node
	 *            The node this container belongs to
	 * @param parentEdge
	 *            The edge pointing to the node which this container belongs to
	 * @param tentativeCost
	 *            The tentative cost of this container
	 */
	public TentativeNodeContainer(final Node node, final DirectedWeightedEdge parentEdge, final float tentativeCost) {
		this(node, parentEdge, tentativeCost, DEFAULT_EST_COST_TO_DEST);
	}

	/**
	 * Creates a new container for a node which stores tentative data.
	 * 
	 * @param node
	 *            The node this container belongs to
	 * @param parentEdge
	 *            The edge pointing to the node which this container belongs to
	 * @param tentativeCost
	 *            The tentative cost of this container
	 * @param estCostToDest
	 *            The estimated remaining cost to reach the destination
	 */
	public TentativeNodeContainer(final Node node, final DirectedWeightedEdge parentEdge, final float tentativeCost,
			final float estCostToDest) {
		mNode = node;
		mTentativeCost = tentativeCost;
		mEstCostToDest = estCostToDest;
		mParentEdge = parentEdge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final TentativeNodeContainer other) {
		return Float.compare(getTentativeCost() + getEstCostToDest(),
				other.getTentativeCost() + other.getEstCostToDest());
	}

	/**
	 * Gets the estimated remaining cost to reach the destination.
	 * 
	 * @return The estimated remaining cost to reach the destination
	 */
	public float getEstCostToDest() {
		return mEstCostToDest;
	}

	/**
	 * Gets the node of this container.
	 * 
	 * @return The node to this container
	 */
	public Node getNode() {
		return mNode;
	}

	/**
	 * Gets the edge pointing to the node which this container belongs to.
	 * 
	 * @return The edge pointing to the node which this container belongs to
	 */
	public DirectedWeightedEdge getParentEdge() {
		return mParentEdge;
	}

	/**
	 * Gets the tentative cost of this container.
	 * 
	 * @return The tentative cost of this container
	 */
	public float getTentativeCost() {
		return mTentativeCost;
	}
}
