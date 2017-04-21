package de.zabuza.pathweaver.network.algorithm.shortestpath;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.network.algorithm.metric.IMetric;

/**
 * A-star shortest path algorithm which solves shortest path computation tasks
 * in {@link PathNetwork}s using a given metric.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class AStarShortestPathComputation extends DijkstraShortestPathComputation {
	/**
	 * The metric to use.
	 */
	private final IMetric<Node> mMetric;

	/**
	 * Creates a new shortest path computation object with a given heuristic.
	 * 
	 * @param network
	 *            The network to work on
	 * @param metric
	 *            The metric to use
	 */
	public AStarShortestPathComputation(final IPathNetwork network, final IMetric<Node> metric) {
		super(network);
		this.mMetric = metric;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.
	 * DijkstraShortestPathComputation#getEstCostToDest(de.zabuza.pathweaver.
	 * network.Node, de.zabuza.pathweaver.network.Node)
	 */
	@Override
	protected float getEstCostToDest(final Node node, final Node dest) {
		return this.mMetric.distance(node, dest);
	}

}
