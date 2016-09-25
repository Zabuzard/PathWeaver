package de.zabuza.pathweaver.network;

/**
 * A class which represents outgoing edges in a {@link PathNetwork}.
 * 
 * @author Zabuza
 *
 */
public final class OutgoingEdge extends ADirectedEdge {
	/**
	 * Creates an outgoing edge with a given destination and cost.
	 * 
	 * @param destination
	 *            The destination of this edge
	 * @param cost
	 *            The cost of this edge which must be greater than <tt>zero</tt>
	 * @throws IllegalArgumentException
	 *             When cost is not greater than <tt>zero</tt>.
	 */
	public OutgoingEdge(final Node destination, final int cost) throws IllegalArgumentException {
		super(destination, cost);
	}

	/**
	 * Gets the destination of this edge.
	 * 
	 * @return The destination of this edge
	 */
	public Node getDestination() {
		return getTarget();
	}
}
