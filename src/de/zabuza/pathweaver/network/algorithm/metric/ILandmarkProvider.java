package de.zabuza.pathweaver.network.algorithm.metric;

import java.util.Set;

/**
 * Interface for classes that provide landmarks. Landmark selection is most
 * effective the closest it is on the shortest path between sources and
 * destinations.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 * @param <E>
 *            Type of landmarks
 */
public interface ILandmarkProvider<E> {

	/**
	 * Selects a given amount of landmarks according to the implementing
	 * technique and returns them as set.
	 * 
	 * @param amount
	 *            Amount of landmarks to get
	 * @return A set of given size containing selected landmarks
	 * @throws IllegalArgumentException
	 *             If the given amount is not greater than <tt>zero</tt> or
	 *             there are no such many unique landmarks available.
	 */
	public Set<E> getLandmarks(final int amount) throws IllegalArgumentException;
}
