package de.zabuza.pathweaver.network.algorithm;

import de.zabuza.pathweaver.network.Node;

/**
 * Container which assigns a given node a tentative cost.
 * 
 * @author Zabuza
 *
 */
public final class TentativeCostNodeContainer implements Comparable<TentativeCostNodeContainer> {
	/**
	 * The node of this container.
	 */
	private final Node mNode;
	/**
	 * The tentative cost of this container.
	 */
	private float mTentativeCost;

	public TentativeCostNodeContainer(final Node node, final float tentativeCost) {
		mNode = node;
		mTentativeCost = tentativeCost;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final TentativeCostNodeContainer other) {
		return Float.compare(getTentativeCost(), other.getTentativeCost());
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
	 * Gets the tentative cost of this container.
	 * 
	 * @return The tentative cost of this container
	 */
	public float getTentativeCost() {
		return mTentativeCost;
	}

	/**
	 * Sets the tentative cost of this container
	 * 
	 * @param tentativeCost
	 *            The tentative cost to set
	 */
	public void setTentativeCost(float tentativeCost) {
		mTentativeCost = tentativeCost;
	}

}
