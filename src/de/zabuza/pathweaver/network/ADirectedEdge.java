package de.zabuza.pathweaver.network;

/**
 * A class which represents directed edges in a {@link PathNetwork}.
 * 
 * @author Zabuza
 *
 */
public abstract class ADirectedEdge {
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
	 * The target of this directed edge. This can either be the head or tail
	 * depending on the perspective.
	 */
	private final Node mTarget;

	/**
	 * Creates an directed edge with a given target and cost.
	 * 
	 * @param target
	 *            The target of this directed edge. This can either be the head
	 *            or tail depending on the perspective.
	 * @param cost
	 *            The cost of this edge which must be greater than <tt>zero</tt>
	 * @throws IllegalArgumentException
	 *             When cost is not greater than <tt>zero</tt>.
	 */
	public ADirectedEdge(final Node target, final float cost) throws IllegalArgumentException {
		if (cost <= 0) {
			throw new IllegalArgumentException(EXCEPTION_COST_ILLEGAL + cost);
		}
		mCost = cost;
		mTarget = target;
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
	 * Gets the target of this directed edge. This can either be the head or
	 * tail depending on the perspective.
	 * 
	 * @return The target of this directed edge. This can either be the head or
	 *         tail depending on the perspective.
	 */
	protected Node getTarget() {
		return mTarget;
	}
}
