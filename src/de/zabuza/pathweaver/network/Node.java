package de.zabuza.pathweaver.network;

/**
 * A node object which is identified by an Id.
 * 
 * @author Zabuza
 *
 */
public class Node {

	/**
	 * Id of this node.
	 */
	private final int mId;

	/**
	 * Creates a new node with a given id.
	 * 
	 * @param id
	 *            The id of the node
	 */
	public Node(final int id) {
		mId = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Node)) {
			return false;
		}
		Node other = (Node) obj;
		if (mId != other.mId) {
			return false;
		}
		return true;
	}

	public int getId() {
		return mId;
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
		result = prime * result + mId;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mId + "";
	}
}
