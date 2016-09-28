package de.zabuza.pathweaver.network.road;

import de.zabuza.pathweaver.network.Node;

/**
 * Class for nodes that represent geographical road crossings.
 * 
 * @author Zabuza
 *
 */
public class RoadNode extends Node {
	/**
	 * Message for the exception thrown when the given cost of the edge is
	 * illegal.
	 */
	private static final String EXCEPTION_LATITUDE_ILLEGAL = "Latitude position must be between -90 and 90 (both inclusive). Given: ";

	/**
	 * Message for the exception thrown when the given cost of the edge is
	 * illegal.
	 */
	private static final String EXCEPTION_LONGITUDE_ILLEGAL = "Longitude position must be between -180 and 180 (both inclusive). Given: ";

	/**
	 * The maximal allowed value for a latitude position.
	 */
	private static final int LATITUDE_MAX = 90;

	/**
	 * The minimal allowed value for a latitude position.
	 */
	private static final int LATITUDE_MIN = -90;

	/**
	 * The maximal allowed value for a longitude position.
	 */
	private static final int LONGITUDE_MAX = 180;
	/**
	 * The minimal allowed value for a longitude position.
	 */
	private static final int LONGITUDE_MIN = -180;

	/**
	 * The latitude position of this node. Must be between <tt>-90<tt> and
	 * <tt>90<tt> (both inclusive).
	 */
	private final float mLatitude;
	/**
	 * The latitude position of this node. Must be between <tt>-180<tt> and
	 * <tt>180<tt> (both inclusive).
	 */
	private final float mLongitude;

	/**
	 * Creates a new road node with given an ID and a position in latitude and
	 * longitude.
	 * 
	 * @param id
	 *            The id of this node
	 * @param latitude
	 *            The latitude position of this node which must be between
	 *            <tt>-90</tt> and <tt>90</tt> (both inclusive)
	 * @param longitude
	 *            The longitude position of this node which must be between
	 *            <tt>-180</tt> and <tt>180</tt> (both inclusive)
	 * @throws IllegalArgumentException
	 *             If either the given latitude or longitude exceeds the allowed
	 *             range
	 */
	public RoadNode(final int id, final float latitude, final float longitude) throws IllegalArgumentException {
		super(id);
		if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
			throw new IllegalArgumentException(EXCEPTION_LATITUDE_ILLEGAL + latitude);
		}
		if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
			throw new IllegalArgumentException(EXCEPTION_LONGITUDE_ILLEGAL + longitude);
		}
		mLatitude = latitude;
		mLongitude = longitude;
	}

	/**
	 * Gets the latitude position of this node.
	 * 
	 * @return The latitude position of this node.
	 */
	public float getLatitude() {
		return mLatitude;
	}

	/**
	 * Gets the longitude position of this node.
	 * 
	 * @return The longitude position of this node.
	 */
	public float getLongitude() {
		return mLongitude;
	}
}
