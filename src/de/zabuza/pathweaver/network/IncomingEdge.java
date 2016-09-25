package de.zabuza.pathweaver.network;

/**
 * A class which represents incoming edges in a {@link PathNetwork}.
 * 
 * @author Zabuza
 *
 */
public final class IncomingEdge extends ADirectedEdge {
	/**
	 * Creates an incoming edge with a given source and cost.
	 * 
	 * @param source
	 *            The source of this edge
	 * @param cost
	 *            The cost of this edge which must be greater than <tt>zero</tt>
	 * @throws IllegalArgumentException
	 *             When cost is not greater than <tt>zero</tt>.
	 */
	public IncomingEdge(final Node source, final int cost) throws IllegalArgumentException {
		super(source, cost);
	}

	/**
	 * Gets the source of this edge.
	 * 
	 * @return The source of this edge
	 */
	public Node getSource() {
		return getTarget();
	}
}
