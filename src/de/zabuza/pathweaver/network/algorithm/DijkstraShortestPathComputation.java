package de.zabuza.pathweaver.network.algorithm;

import de.zabuza.pathweaver.network.IPathNetwork;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * Dijkstras shortest path algorithm which solves shortest path computation
 * tasks in {@link PathNetwork}s.
 * 
 * @author Zabuza
 *
 */
public class DijkstraShortestPathComputation implements IShortestPathComputation {
	/**
	 * The path network this object works on.
	 */
	private final IPathNetwork mNetwork;

	/**
	 * Creates a new shortest path computation object
	 * 
	 * @param network
	 */
	public DijkstraShortestPathComputation(final IPathNetwork network) {
		mNetwork = network;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * computeShortestPathCost(de.zabuza.pathweaver.network.Node,
	 * de.zabuza.pathweaver.network.Node)
	 */
	@Override
	public float computeShortestPathCost(Node source, Node destination) {
		// TODO Implement
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.IShortestPathComputation#
	 * getPathNetwork()
	 */
	@Override
	public IPathNetwork getPathNetwork() {
		return mNetwork;
	}

}
