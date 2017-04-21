package de.zabuza.pathweaver.network.road;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.zabuza.pathweaver.util.ReverseIterator;

/**
 * Data container used for constructing roads.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class Road {
	/**
	 * The id of the road.
	 */
	private final int mId;
	/**
	 * Whether the road is only one-way or not.
	 */
	private boolean mIsOneway;
	/**
	 * The list of IDs of the road nodes that belong to the road.
	 */
	private final List<Integer> mNodes;

	/**
	 * The type of the road.
	 */
	private ERoadType mRoadType;

	/**
	 * Creates a new road construction data with a given id.
	 * 
	 * @param id
	 *            The id of the road to construct
	 */
	public Road(final int id) {
		this.mId = id;
		this.mIsOneway = false;
		this.mRoadType = ERoadType.UNCLASSIFIED;
		this.mNodes = new LinkedList<>();
	}

	/**
	 * Adds a road node to the road which gets connected with the previous added
	 * node.
	 * 
	 * @param roadNodeId
	 *            The id of the road node to add
	 */
	public void addRoadNode(final int roadNodeId) {
		this.mNodes.add(Integer.valueOf(roadNodeId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Road)) {
			return false;
		}
		final Road other = (Road) obj;
		if (this.mId != other.mId) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the id of the road to construct.
	 * 
	 * @return The id of the road to construct
	 */
	public int getId() {
		return this.mId;
	}

	/**
	 * Gets an iterator over the IDs of the road nodes belonging to this road.
	 * 
	 * @return An iterator over the IDs of the road nodes belonging to this road
	 */
	public Iterator<Integer> getRoadNodes() {
		return this.mNodes.iterator();
	}

	/**
	 * Gets the amount of road nodes that belong to this road.
	 * 
	 * @return The amount of road nodes that belong to this road
	 */
	public int getRoadNodesAmount() {
		return this.mNodes.size();
	}

	/**
	 * Gets an iterator over the IDs of the road nodes belonging to this road
	 * which traverses the elements in reversed order.
	 * 
	 * @return An iterator over the IDs of the road nodes belonging to this road
	 *         which traverses the elements in reversed order.
	 */
	public Iterator<Integer> getRoadNodesReversed() {
		return new ReverseIterator<>(this.mNodes);
	}

	/**
	 * Gets the type of the road to construct.
	 * 
	 * @return The type of the road to construct
	 */
	public ERoadType getRoadType() {
		return this.mRoadType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.mId;
		return result;
	}

	/**
	 * Gets whether the road to construct is only one-way or no.
	 * 
	 * @return Whether the road to construct is only one-way or no
	 */
	public boolean isOneway() {
		return this.mIsOneway;
	}

	/**
	 * Set whether the road is only one-way or not.
	 * 
	 * @param isOneway
	 *            If the road is only one-way or not
	 */
	public void setIsOneway(final boolean isOneway) {
		this.mIsOneway = isOneway;
	}

	/**
	 * Sets the type of the road to construct.
	 * 
	 * @param roadType
	 *            The type of the road to set
	 */
	public void setRoadType(final ERoadType roadType) {
		this.mRoadType = roadType;
	}
}
