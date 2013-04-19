package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import edu.vt.jowilcox.cs3114.p4.Hashtable;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Direction;
import edu.vt.jowilcox.cs3114.p4.prquadtree.prQuadtree;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Compare2D;

/**
 * GIS record database.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISDatabaseFile extends AbstractGISFile {
	private Hashtable<String, Index> nameIndex;
	private prQuadtree<Index> coordIndex;

	/**
	 * Index object.
	 * 
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 */
	private class Index implements Compare2D<Index> {
		private long offset;
		private String name;
		private long xcoord;
		private long ycoord;

		/**
		 * @param offset
		 * @param name
		 * @param xcoord
		 * @param ycoord
		 */
		public Index(long offset, String name, long xcoord, long ycoord) {
			this.offset = offset;
			this.name = name;
			this.xcoord = xcoord;
			this.ycoord = ycoord;
		}

		/**
		 * Returns the x-coordinate field of the user data object.
		 * 
		 * @return the X-coordinate of the object.
		 */
		@Override
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
		 * location (X, Y) specified by the parameters. The indicators are defined
		 * in the enumeration Direction, and are used as follows: NE: vector from
		 * (X, Y) to user data object has a direction in the range [0, 90) degrees
		 * (relative to the positive horizontal axis NW: same as above, but
		 * direction is in the range [90, 180) SW: same as above, but direction is
		 * in the range [180, 270) SE: same as above, but direction is in the range
		 * [270, 360) NOQUADRANT: location of user object is equal to (X, Y)
		 * 
		 * @param X
		 *          x-coordinate origin
		 * @param Y
		 *          y-coordinate origin
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
		 * parameters that user data object lies in. The indicators are defined in
		 * the enumeration Direction, and are used as follows, relative to the
		 * center of the rectangle: NE: user data object lies in NE quadrant,
		 * including non-negative x-axis, but not the positive y-axis NW: user data
		 * object lies in the NW quadrant, including the positive y-axis, but not
		 * the negative x-axis SW: user data object lies in the SW quadrant,
		 * including the negative x-axis, but not the negative y-axis SE: user data
		 * object lies in the SE quadrant, including the negative y-axis, but not
		 * the positive x-axis NOQUADRANT: user data object lies outside the
		 * specified rectangle
		 * 
		 * @param xLo
		 *          x-coordinate of the low corner of the bounding square.
		 * @param yLo
		 *          y-coordinate of the low corner of the bounding square.
		 * @param yHi
		 *          y-coordinate of the high corner of the bounding square.
		 * @param xHi
		 *          y-coordinate of the high corner of the bounding square.
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
		 */
		public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
			return (this.getX() >= xLo) && (this.getX() <= xHi)
			    && (this.getY() >= yLo) && (this.getY() <= yHi);
		}

		/**
		 * Returns a string representation of this index.
		 * 
		 * @return string representation of point.
		 */
		@Override
		public String toString() {
			String point = (this.name == null) ? "" : this.getName();
			point += "(";
			point += this.getX();
			point += ", ";
			point += this.getY();
			point += ")";
			point += "<" + this.getOffset() + ">";
			return point;
		}

		/**
		 * Overrides the user data object's inherited equals() method with an
		 * appropriate definition; it is necessary to place this in the interface
		 * that is used as a bound on the type parameter for the generic spatial
		 * structure, otherwise the compiler will bind to Object.equals(), which
		 * will almost certainly be inappropriate.
		 */
		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}

			if ((o.getClass() == Index.class)) {
				return ( ((Index) o).getX() == this.getX())
				    && (((Index) o).getY() == this.getY())
				    && (((Index) o).getOffset() == this.getOffset());
			}
			return false;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the offset
		 */
		public long getOffset() {
			return this.offset;
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param filename
	 *          path to the file.
	 * @throws FileNotFoundException
	 */
	public GISDatabaseFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename,
		    AbstractGISFile.GIS_FILE_READWRITE);
	}

	/**
	 * Read a line of data after offset.
	 * 
	 * @param offset
	 *          byte offset to begin reading
	 * @return String containing the data after offset.
	 * @throws IOException
	 *           thrown if offset is less than 0 or other IOException .
	 */
	public String select(long offset) throws IOException {
		// set file pointer to record offset
		this.file.seek(offset);
		// return the next line
		return this.read();
	}

	private void insert(String read) throws IOException {
		// size of file is offset
		long offset = this.file.length();
		// read line
		this.file.writeBytes(read + "\n");
		GISRecord record = new GISRecord(read);
		// index
		Index index = new Index(
				offset,
				record.getName(), 
				(long) (record.getLongitude() * 3600),
				(long) (record.getLatitude() * 3600));
		
		if(this.nameIndex != null) {
			this.nameIndex.put(record.getName() + ":" + record.getState().toString(), index);
		}
		if(this.coordIndex != null) {
			this.coordIndex.insert(index);
		}
  }
	
	public void insert(GISRecordsFile document) throws IOException {
		// TODO Auto-generated method stub
		long size = document.getFile().length();
		// skip a line
		document.read();
		while (document.getFile().getFilePointer() < size) {
			this.insert(document.read());
		}
	}


	public void truncate() throws IOException {
		this.file.setLength(0);
	}

	/**
	 * @return the nameIndex
	 */
	public Hashtable<String, Index> getNameIndex() {
		return nameIndex;
	}

	/**
	 * @param nameIndex
	 *          the nameIndex to set
	 */
	public void configNameIndex() {
		this.nameIndex = new Hashtable<>();
	}
	
	public void configNameIndex(int capacity) {
		this.nameIndex = new Hashtable<>(capacity);
	}

	public void configNameIndex(int capacity, float portion) {
		this.nameIndex = new Hashtable<>(capacity, portion);
	}
	/**
	 * @return the coordIndex
	 */
	public prQuadtree<Index> getCoordIndex() {
		return coordIndex;
	}

	/**
	 * @param coordIndex
	 *          the coordIndex to set
	 */
	public void configCoordIndex(long xMin, long xMax, long yMin, long yMax) {
		this.coordIndex = new prQuadtree<>(xMin, xMax, yMin, yMax);
	}
	
	public void configCoordIndex(long xMin, long xMax, long yMin, long yMax, int bucketSize) {
		this.coordIndex = new prQuadtree<>(xMin, xMax, yMin, yMax, bucketSize);
	}
	
}
