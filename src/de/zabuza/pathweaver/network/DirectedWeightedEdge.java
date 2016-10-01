package de.zabuza.pathweaver.network;

/**
 * A class which represents directed edges in a {@link PathNetwork}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class DirectedWeightedEdge {
	/**
	 * Message for the exception thrown when the given cost of the edge is
	 * illegal.
	 */
	private static final String EXCEPTION_COST_ILLEGAL = "Cost must be greater than zero. Given: ";

	/**
	 * The cost of this edge which must be greater than <tt>zero</tt>.
	 */
	private final float mCost;

	/**
	 * The destination of this directed edge.
	 */
	private final Node mDestination;
	/**
	 * The source of this directed edge.
	 */
	private final Node mSource;

	/**
	 * Creates an directed edge with a given source, destination and cost.
	 * 
	 * @param source
	 *            The source of this directed edge.
	 * @param destination
	 *            The destination of this directed edge.
	 * @param cost
	 *            The cost of this edge which must be greater than <tt>zero</tt>
	 * @throws IllegalArgumentException
	 *             When cost is not greater than <tt>zero</tt>.
	 */
	public DirectedWeightedEdge(final Node source, final Node destination, final float cost)
			throws IllegalArgumentException {
		if (cost <= 0) {
			throw new IllegalArgumentException(EXCEPTION_COST_ILLEGAL + cost);
		}
		mCost = cost;
		mSource = source;
		mDestination = destination;
	}

	/**
	 * Gets the cost of this edge.
	 * 
	 * @return The cost of this edge
	 */
	public float getCost() {
		return mCost;
	}

	/**
	 * Gets the destination of this directed edge.
	 * 
	 * @return The destination of this directed edge.
	 */
	public Node getDestination() {
		return mDestination;
	}

	/**
	 * Gets the source of this directed edge.
	 * 
	 * @return The source of this directed edge.
	 */
	public Node getSource() {
		return mSource;
	}
}
