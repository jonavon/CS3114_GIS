package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.vt.jowilcox.cs3114.p4.Hashtable;
import edu.vt.jowilcox.cs3114.p4.bufferpool.BufferPool;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Direction;
import edu.vt.jowilcox.cs3114.p4.prquadtree.prQuadtree;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Compare2D;

/**
 * GIS record database class. Provides an name index, coordinate index, and a
 * buffer that will allow for management of the wrapped {@link RandomAccessFile}
 * object.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISDatabaseFile extends AbstractGISFile {

	/**
	 * Coordinate Index object. Public inner class that contains coordinate fields
	 * to be used in a QuadTree.
	 * 
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 */
	public class CoordIndex extends Index implements Compare2D<CoordIndex> {
		private final String name;
		private Map<String, Long> shell;
		private final long xcoord;
		private final long ycoord;
		private boolean delete;

		/**
		 * Constructor.
		 * 
		 * @param offset
		 *          the file offset.
		 * @param name
		 *          the name.
		 * @param xcoord
		 *          the x value for coordinates.
		 * @param ycoord
		 *          the y value for coordinates.
		 */
		public CoordIndex(long offset, String name, long xcoord, long ycoord) {
			super(offset);
			this.name = name;
			this.xcoord = xcoord;
			this.ycoord = ycoord;
			this.delete = false;
			this.shell = new TreeMap<>();
			this.shell.put(this.name, this.getOffset());
		}

		@Override
		public Direction directionFrom(long X, long Y) {
			if (this.getX() == X && this.getY() == Y) {
				return Direction.NOQUADRANT;
			}
			char ew = ((this.getX() - X) >= 0) ? 'E' : 'W';
			char ns = ((this.getY() - Y) >= 0) ? 'N' : 'S';
			return Direction.valueOf("" + ns + ew);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}

			if ((o.getClass() == CoordIndex.class)) {
				return (((CoordIndex) o).getX() == this.getX())
				    && (((CoordIndex) o).getY() == this.getY());
			}
			return false;
		}

		@Override
		public void fission(CoordIndex o) {
			if (this.equals(o)) {
				for (String k : o.shell.keySet()) {
					this.shell.remove(k);
				}
				this.delete = (this.shell.size() == 0);
			}

		}

		@Override
		public void fusion(CoordIndex o) {
			if (this.equals(o)) {
				this.shell.putAll(o.shell);
			}
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
		public Collection<Long> getOffsets() {
			return this.shell.values();
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
		 * @return boolean true if object can exist within the coordinates.
		 */
		public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
			return (this.getX() >= xLo) && (this.getX() <= xHi)
			    && (this.getY() >= yLo) && (this.getY() <= yHi);
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
		 * @return Direction
		 *  a direction quadrant within the set of points.
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

		@Override
		public boolean isDeleted() {
			return this.delete;
		}

		/**
		 * Returns a string representation of this index.
		 * 
		 * @return string representation of point.
		 */
		@Override
		public String toString() {
			StringBuilder output = new StringBuilder();
			int attached = this.shell.size();
			if (attached > 1) {
				output.append("{");
				output.append(attached);
				output.append(" attached}");
			}
			else {
				output.append((this.name == null) ? "" : this.getName());
			}
			output.append("(");
			output.append(this.getX());
			output.append(", ");
			output.append(this.getY());
			output.append(")");
			if (attached > 1) {
				for (Entry<String, Long> entry : this.shell.entrySet()) {
					output.append(" ");
					output.append(entry.getKey());
					output.append("<");
					output.append(entry.getValue());
					output.append(">");
				}
			}
			else {
				output.append("<");
				output.append(this.getOffset());
				output.append(">");
			}
			return output.toString();
		}
	}

	/**
	 * Index class used to be stored within an Indexer.
	 * 
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 */
	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 *
	 */
	public class Index {
		private final long offset;
		private List<Long> shell;

		/**
		 * Constructor.
		 * 
		 * @param offset the database file offset.
		 */
		public Index(final long offset) {
			this.offset = offset;
			this.shell = new ArrayList<>();
			this.shell.add(offset);
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}

			if ((o.getClass() == Index.class)) {
				return (((Index) o).getOffset() == this.getOffset());
			}
			return false;
		}

		/**
		 * Divide an objects values.
		 * 
		 * @param o the other index object.
		 */
		public void fission(Index o) {
			for (Long item : o.shell) {
				this.shell.remove(item);
			}
		}

		/**
		 * Combine another index object's values.
		 * 
		 * @param o the other index object.
		 */
		public void fusion(Index o) {
			this.shell.addAll(o.shell);
		}

		/**
		 * @return the offset
		 */
		public long getOffset() {
			return this.offset;
		}

		/**
		 * @return the offset
		 */
		public Collection<Long> getOffsets() {
			return this.shell;
		}

		/**
		 * Returns a string representation of this index.
		 * 
		 * @return string representation of point.
		 */
		@Override
		public String toString() {
			int size = this.shell.size();
			StringBuilder output = new StringBuilder();
			// for (Long offset : this.shell) {
			output.append("<");
			output.append(offset);
			output.append(">");
			if (size > 1) {
				output.append("+").append(size - 1).append(" more");
			}
			// }
			return output.toString();
		}
	}

	/** Name index. */
	private Hashtable<String, Index> nameIndex;
	/** Coordinate Index. */
	private prQuadtree<CoordIndex> coordIndex;
	/** Buffer Pool. */
	private BufferPool<Long, GISRecord> bufferPool;

	/** Counter for imported indices. */
	private int nameImportCnt;

	/** Counter for imported indices. */
	private int coordImportCnt;

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
	 * Set up the internal coordinate index if it is not already.
	 * 
	 * @param xMin world boundary.
	 * @param xMax world boundary.
	 * @param yMin world boundary.
	 * @param yMax world boundary.
	 */
	public void configCoordIndex(long xMin, long xMax, long yMin, long yMax) {
		if (this.coordIndex == null) {
			this.coordIndex = new prQuadtree<>(xMin, xMax, yMin, yMax);
		}
	}

	/**
	 * Set up the internal coordinate index if it is not already.
	 * 
	 * @param xMin world boundary.
	 * @param xMax world boundary.
	 * @param yMin world boundary.
	 * @param yMax world boundary.
	 * @param bucketSize the size of the buckets.
	 */
	public void configCoordIndex(long xMin, long xMax, long yMin, long yMax,
	    int bucketSize) {
		if (this.coordIndex == null) {
			this.coordIndex = new prQuadtree<>(xMin, xMax, yMin, yMax, bucketSize);
		}
	}

	/**
	 * Setup the name index if it is not already.
	 */
	public void configNameIndex() {
		if (this.nameIndex == null) {
			this.nameIndex = new Hashtable<>();
		}
	}

	/**
	 * Setup the name index if it is not already.
	 * 
	 * @param capacity initial capacity of the name index.
	 */
	public void configNameIndex(int capacity) {
		if (this.nameIndex == null) {
			this.nameIndex = new Hashtable<>(capacity);
		}
	}

	/**
	 * Setup the name index if it is not already.
	 * 
	 * @param capacity initial capacity of the name index.
	 * @param portion load factor for the name index.
	 */
	public void configNameIndex(int capacity, float portion) {
		if (this.nameIndex == null) {
			this.nameIndex = new Hashtable<>(capacity, portion);
		}
	}

	/**
	 * Get the buffer pool for the database.
	 * @return the buffer pool.
	 */
	public BufferPool<Long, GISRecord> getBufferPool() {
		return this.bufferPool;
	}

	/**
	 * Get the number of imports for debugging.
	 * @return the coordImportCnt
	 */
	public int getCoordImportCnt() {
		return this.coordImportCnt;
	}

	/**
	 * @return the coordIndex
	 */
	public prQuadtree<CoordIndex> getCoordIndex() {
		return coordIndex;
	}

	/**
	 * @return the number of imports into the coordinate index.
	 */
	public int getCoordIndexSize() {
		return this.coordIndex.size();
	}

	/**
	 * @return the nameImportCnt
	 */
	public int getNameImportCnt() {
		return this.nameImportCnt;
	}

	/**
	 * @return the nameIndex
	 */
	public Hashtable<String, Index> getNameIndex() {
		return nameIndex;
	}

	/**
	 * @return get the size of the name index.
	 */
	public int getNameIndexSize() {
		return this.nameIndex.size();
	}

	/**
	 * Insert records in the database.
	 * @param document a file that contains records to be imported.
	 * @throws IOException
	 */
	public void insert(GISRecordsFile document) throws IOException {
		long size = document.getFile().length();
		// skip a line
		// reset tracking variables
		this.nameImportCnt = 0;
		this.coordImportCnt = 0;
		document.read();
		while (document.getFile().getFilePointer() < size) {
			this.insert(document.read());
		}
	}

	/**
	 * Insert a single record into the database.
	 * @param read a string that represents a record.
	 * @throws IOException
	 */
	private void insert(String read) throws IOException {
		// size of file is offset
		long offset = this.file.length();
		// read line
		this.file.seek(offset);
		this.file.write((read + "\n").getBytes());

		GISRecord record = new GISRecord(read);
		// index
		Index index = new Index(offset);
		CoordIndex cindex = new CoordIndex(offset, record.getName(),
		    (long) Math.round((record.getLongitude() * 3600)),
		    (long) Math.round((record.getLatitude() * 3600)));

		if (this.nameIndex != null) {
			String key = record.getName() + ":" + record.getState().toString();
			if (this.nameIndex.containsKey(key)) {
				index.fusion(this.nameIndex.get(key));
			}
			if (this.nameIndex.put(record.getName() + ":"
			    + record.getState().toString(), index) != index) {
				this.nameImportCnt++; // for debugging
			}
		}
		if (this.coordIndex != null) {
			if (this.coordIndex.insert(cindex)) {
				this.coordImportCnt++; // for debugging
			}
		}
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
	public GISRecord select(long offset) throws IOException {
		if (this.bufferPool == null) {
			this.bufferPool = new BufferPool<>();
		}
		// Check the buffer first
		GISRecord data = this.bufferPool.get(offset);
		if (data == null) {
			// set file pointer to record offset
			this.file.seek(offset);
			// return the next line
			data = new GISRecord(this.read());
			this.bufferPool.put(offset, data);
		}
		return data;
	}

	/**
	 * Empty this file.
	 * @throws IOException
	 */
	public void truncate() throws IOException {
		this.file.setLength(0);
	}

}
