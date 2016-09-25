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
	private final int LATITUDE_MAX = 90;

	/**
	 * The minimal allowed value for a latitude position.
	 */
	private final int LATITUDE_MIN = -90;
	/**
	 * The maximal allowed value for a longitude position.
	 */
	private final int LONGITUDE_MAX = 180;

	/**
	 * The minimal allowed value for a longitude position.
	 */
	private final int LONGITUDE_MIN = -180;

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
	 * Creates a new road node with given position in latitude and longitude.
	 * 
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
	public RoadNode(final float latitude, final float longitude) throws IllegalArgumentException {
		if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX) {
			throw new IllegalArgumentException(EXCEPTION_LATITUDE_ILLEGAL + latitude);
		}
		if (longitude < LONGITUDE_MIN || longitude > LONGITUDE_MAX) {
			throw new IllegalArgumentException(EXCEPTION_LONGITUDE_ILLEGAL + longitude);
		}
		mLatitude = latitude;
		mLongitude = longitude;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RoadNode)) {
			return false;
		}
		RoadNode other = (RoadNode) obj;
		if (Float.floatToIntBits(mLatitude) != Float.floatToIntBits(other.mLatitude)) {
			return false;
		}
		if (Float.floatToIntBits(mLongitude) != Float.floatToIntBits(other.mLongitude)) {
			return false;
		}
		return true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(mLatitude);
		result = prime * result + Float.floatToIntBits(mLongitude);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RoadNode [latitude=" + mLatitude + ", longitude=" + mLongitude + "]";
	}
}
