package de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.road.RoadNetwork;
import de.zabuza.pathweaver.network.road.RoadNode;

/**
 * Partitions a given road network into two areas. One area contains the nodes
 * which are inside of a given axis aligned rectangle and the other contains the
 * nodes which are outside of it.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class OneAxisRectanglePartitioningProvider implements INetworkPartitioningProvider {
	/**
	 * The maximal latitude of the rectangle.
	 */
	private float mLatitudeMax;
	/**
	 * The minimal latitude of the rectangle.
	 */
	private float mLatitudeMin;
	/**
	 * The maximal longitude of the rectangle.
	 */
	private float mLongitudeMax;
	/**
	 * The minimal longitude of the rectangle.
	 */
	private float mLongitudeMin;
	/**
	 * The network to create a partitioning for.
	 */
	private RoadNetwork mNetwork;

	/**
	 * Creates a new network partitioning provider which partitions a given road
	 * network into two areas. One area contains the nodes which are inside of a
	 * given axis aligned rectangle and the other contains the nodes which are
	 * outside of it.
	 * 
	 * @param network
	 *            The network to create a partitioning for
	 * @param latitudeMin
	 *            The minimal latitude of the rectangle
	 * @param latitudeMax
	 *            The maximal latitude of the rectangle
	 * @param longitudeMin
	 *            The minimal longitude of the rectangle
	 * @param longitudeMax
	 *            The maximal longitude of the rectangle
	 */
	public OneAxisRectanglePartitioningProvider(final RoadNetwork network, final float latitudeMin,
			final float latitudeMax, final float longitudeMin, final float longitudeMax) {
		mNetwork = network;
		mLatitudeMin = latitudeMin;
		mLatitudeMax = latitudeMax;
		mLongitudeMin = longitudeMin;
		mLongitudeMax = longitudeMax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.algorithm.shortestpath.arcflag.
	 * INetworkPartitioningProvider#getPartitioning()
	 */
	@Override
	public Collection<Set<Node>> getPartitioning() throws IllegalArgumentException {
		Set<Node> insideRectangle = new HashSet<Node>();
		Set<Node> outsideRectangle = new HashSet<Node>();

		for (Node node : mNetwork.getNodes()) {
			if (isInsideRectangle((RoadNode) node)) {
				insideRectangle.add(node);
			} else {
				outsideRectangle.add(node);
			}
		}

		LinkedList<Set<Node>> partitions = new LinkedList<>();
		partitions.add(insideRectangle);
		partitions.add(outsideRectangle);
		return partitions;
	}

	/**
	 * Returns whether the given road node is inside the axis aligned rectangle
	 * or not.
	 * 
	 * @param node
	 *            The node in question
	 * @return <tt>True</tt> when the given road node is inside the axis aligned
	 *         rectangle, <tt>false</tt> if not.
	 */
	public boolean isInsideRectangle(final RoadNode node) {
		float nodeLatitude = node.getLatitude();
		float nodeLongitude = node.getLongitude();

		boolean isInside = (nodeLatitude >= mLatitudeMin && nodeLatitude <= mLatitudeMax)
				&& (nodeLongitude >= mLongitudeMin && nodeLongitude <= mLongitudeMax);
		return isInside;
	}
}
