package de.zabuza.pathweaver.network.algorithm.metric;

/**
 * Interface for metrics. Computes distances between two given elements.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 * @param <E>
 *            Element to define the metric on
 */
public interface IMetric<E> {

	/**
	 * Computes the distance between the two given elements according to this
	 * metric.
	 * 
	 * @param first
	 *            First element
	 * @param second
	 *            Second element
	 * @return The distance between the two given elements according to this
	 *         metric.
	 */
	public float distance(final E first, final E second);
}
