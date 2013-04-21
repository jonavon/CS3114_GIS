package edu.vt.jowilcox.cs3114.p4.point;

import java.util.Set;
import java.util.TreeSet;

import edu.vt.jowilcox.cs3114.p4.prquadtree.Compare2D;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Direction;

/**
 * Implements the interface Compare2D is intended to supply facilities that are
 * useful in supporting the the use of a generic spatial structure with a
 * user-defined data type.
 */
public class Point implements Compare2D<Point> {
	private String name;
	/** Represents the human readable name of the object. */
	private Set<String> names;

	/** The X and Y coordinates of the object */
	long xcoord;
	private long ycoord;
	private boolean delete;

	/**
	 * Constructor. Initializes coordinate fields to 0;
	 */
	public Point() {
		xcoord = 0;
		ycoord = 0;
	}

	/**
	 * Constructor.
	 */
	public Point(long x, long y) {
		this.xcoord = x;
		this.ycoord = y;
		this.names = new TreeSet<>();
		this.delete = false;
	}

	public Point(String name, long x, long y) {
		this(x, y);
		this.addName(this.name);
		this.name = null;
		this.addName(name);
	}

	/**
	 * Returns the x-coordinate field of the user data object.
	 * 
	 * @return the X-coordinate of the object.
	 */
	public long getX() {
		return xcoord;
	}

	/**
	 * Returns the y-coordinate field of the user data object.
	 * 
	 * @return the Y-coordinate of the object.
	 */
	public long getY() {
		return ycoord;
	}

	/**
	 * Returns indicator of the direction to the user data object from the
	 * location (X, Y) specified by the parameters. The indicators are defined in
	 * the enumeration Direction, and are used as follows: NE: vector from (X, Y)
	 * to user data object has a direction in the range [0, 90) degrees (relative
	 * to the positive horizontal axis NW: same as above, but direction is in the
	 * range [90, 180) SW: same as above, but direction is in the range [180, 270)
	 * SE: same as above, but direction is in the range [270, 360) NOQUADRANT:
	 * location of user object is equal to (X, Y)
	 * 
	 * @param X
	 *          x-coordinate origin
	 * @param Y
	 *          y-coordinate origin
	 *  @return Direction
	 *          The direction from the origin points.
	 */
	public Direction directionFrom(long X, long Y) {
		if (this.getX() == X && this.getY() == Y) {
			return Direction.NOQUADRANT;
		}
		char ew = ((this.getX() - X) >= 0) ? 'E' : 'W';
		char ns = ((this.getY() - Y) >= 0) ? 'N' : 'S';
		return Direction.valueOf("" + ns + ew);
	}

	/**
	 * Returns indicator of which quadrant of the rectangle specified by the
	 * parameters that user data object lies in. The indicators are defined in the
	 * enumeration Direction, and are used as follows, relative to the center of
	 * the rectangle: NE: user data object lies in NE quadrant, including
	 * non-negative x-axis, but not the positive y-axis NW: user data object lies
	 * in the NW quadrant, including the positive y-axis, but not the negative
	 * x-axis SW: user data object lies in the SW quadrant, including the negative
	 * x-axis, but not the negative y-axis SE: user data object lies in the SE
	 * quadrant, including the negative y-axis, but not the positive x-axis
	 * NOQUADRANT: user data object lies outside the specified rectangle
	 * 
	 * @param xLo
	 *          x-coordinate of the low corner of the bounding square.
	 * @param yLo
	 *          y-coordinate of the low corner of the bounding square.
	 * @param yHi
	 *          y-coordinate of the high corner of the bounding square.
	 * @param xHi
	 *          y-coordinate of the high corner of the bounding square.
	 *  @return Direction
	 *          The direction from the origin points.
	 */
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {
		double midX = (xHi + xLo) / 2;
		double midY = (yHi + yLo) / 2;
		if (this.inBox(xLo, xHi, yLo, yHi)) {
			// If NE
			if (this.inBox(midX, xHi, midY, yHi)) {
				return Direction.NE;
			}
			// If NW
			else if (this.inBox(xLo, midX, midY, yHi)) {
				return Direction.NW;
			}
			// If SW
			else if (this.inBox(xLo, midX, yLo, midY)) {
				return Direction.SW;
			}
			// If SE
			else if (this.inBox(midX, xHi, yLo, midY)) {
				return Direction.SE;
			}
		}
		return Direction.NOQUADRANT;
	}

	/**
	 * Returns true iff the user data object lies within or on the boundaries of
	 * the rectangle specified by the parameters.
	 * 
	 * @param xLo
	 *          x-coordinate of the low corner of the bounding square.
	 * @param yLo
	 *          y-coordinate of the low corner of the bounding square.
	 * @param yHi
	 *          y-coordinate of the high corner of the bounding square.
	 * @param xHi
	 *          y-coordinate of the high corner of the bounding square.
	 * @return true
	 *          If this point can exist within the specified points.
	 */
	public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
		return (this.getX() >= xLo) && (this.getX() <= xHi) && (this.getY() >= yLo)
		    && (this.getY() <= yHi);
	}

	/**
	 * Returns a string representation of this point.
	 * 
	 * @return string representation of point.
	 */
	@Override
	public String toString() {
		String point = (this.name.isEmpty()) ? "" : this.name;
		point += "(";
		point += this.getX();
		point += ", ";
		point += this.getY();
		point += ")";
		return point;
	}

	/**
	 * Overrides the user data object's inherited equals() method with an
	 * appropriate definition; it is necessary to place this in the interface that
	 * is used as a bound on the type parameter for the generic spatial structure,
	 * otherwise the compiler will bind to Object.equals(), which will almost
	 * certainly be inappropriate.
	 * 
	 * @param o
	 *        test object.
	 * @return boolean
	 *          true if the two objects are equivalent.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}

		if ((o.getClass() == Point.class)) {
			return (((Point) o).getX() == this.getX())
			    && (((Point) o).getY() == this.getY());
			// && (((this.getName().equals(((Point) o).getName())))));
		}
		return false;
	}

	/**
	 * @return the name
	 */
	public Set<String> getNames() {
		return names;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	private void addName(String name) {
		if (this.names == null) {
			this.names = new TreeSet<>();
		}
		this.names.add(name);
		this.name = this.implode(" | ", (String[]) this.names.toArray());
	}

	private String implode(String separator, String... data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++) {
			// data.length - 1 => to not add separator at the end
			if (!data[i].matches(" *")) {// empty string are ""; " "; "  "; and so on
				sb.append(data[i]);
				sb.append(separator);
			}
		}
		sb.append(data[data.length - 1]);
		return sb.toString();
	}
	
	protected String getName() {
		return this.name;
	}
	
	public void fusion(Point p) {
		if(this.equals(p)) {
			if(p.getNames().size() > 0) {
				this.names.addAll(p.getNames());
				this.name = this.implode(" | ", (String[]) this.names.toArray());
			}
			else {
				this.addName(p.getName());
			}
		}
	}
	
	public void fission(Point p) {
		if(this.equals(p)) {
			if(p.getNames() != null && p.getNames().size() > 0) {
				this.names.removeAll(p.getNames());
				this.name = this.implode(" | ", (String[]) this.names.toArray());
			}
			else {
				this.delete = true;
			}
		}
	}
	
	public boolean isDeleted() {
		return this.delete;
	}
}
