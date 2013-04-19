package edu.vt.jowilcox.cs3114.p4.prquadtree;

// The interface Compare2D is intended to supply facilities that are useful in
// supporting the the use of a generic spatial structure with a user-defined
// data type.
//
public interface Compare2D<T> {

	// Returns the x-coordinate field of the user data object.
	public long getX();

	// Returns the y-coordinate field of the user data object.
	public long getY();

	// Returns indicator of the direction to the user data object from the
	// location (X, Y) specified by the parameters.
	// The indicators are defined in the enumeration Direction, and are used
	// as follows:
	//
	// NE: vector from (X, Y) to user data object has a direction in the
	// range [0, 90) degrees (relative to the positive horizontal axis
	// NW: same as above, but direction is in the range [90, 180)
	// SW: same as above, but direction is in the range [180, 270)
	// SE: same as above, but direction is in the range [270, 360)
	// NOQUADRANT: location of user object is equal to (X, Y)
	//
	public Direction directionFrom(long X, long Y);

	// Returns indicator of which quadrant of the rectangle specified by the
	// parameters that user data object lies in.
	// The indicators are defined in the enumeration Direction, and are used
	// as follows, relative to the center of the rectangle:
	//
	// NE: user data object lies in NE quadrant, including non-negative
	// x-axis, but not the positive y-axis
	// NW: user data object lies in the NW quadrant, including the positive
	// y-axis, but not the negative x-axis
	// SW: user data object lies in the SW quadrant, including the negative
	// x-axis, but not the negative y-axis
	// SE: user data object lies in the SE quadrant, including the negative
	// y-axis, but not the positive x-axis
	// NOQUADRANT: user data object lies outside the specified rectangle
	//
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi);

	// Returns true iff the user data object lies within or on the boundaries
	// of the rectangle specified by the parameters.
	public boolean inBox(double xLo, double xHi, double yLo, double yHi);

	// Overrides the user data object's inherited equals() method with an
	// appropriate definition; it is necessary to place this in the interface
	// that is used as a bound on the type parameter for the generic spatial
	// structure, otherwise the compiler will bind to Object.equals(), which
	// will almost certainly be inappropriate.
	public boolean equals(Object o);

	/**
	 * Add a similar item to a set of properties. Items at the same 2D coordinate
	 * are usually equal but can be differentiated by another field.
	 * 
	 * @param o
	 *          Item with different properties.
	 */
	public void fusion(T o);

	/**
	 * Remove an item from a set. Items at the same 2D coordinate are usually
	 * equal but can be differentiated by another field. you may optionally set a
	 * delete flag here.
	 * 
	 * @param o
	 *          Item with different properties.
	 */
	public void fission(T o);
	
	/**
	 * Flag this object for deletion. Use of this method is left to the client.
	 * @return 
	 */
	public boolean isDeleted();
}
