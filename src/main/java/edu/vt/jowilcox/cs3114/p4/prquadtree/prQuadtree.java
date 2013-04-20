package edu.vt.jowilcox.cs3114.p4.prquadtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The test harness will belong to the following package; the quadtree
 * implementation must belong to it as well. In addition, the quadtree
 * implementation must specify package access for the node types and tree
 * members so that the test harness may have access to it.
 * 
 * @param <T>
 *          User defined spacial structure.
 */
public class prQuadtree<T extends Compare2D<? super T>> {

	private static final int DEFAULT_BUCKET_CAPACITY = 4;

	prQuadNode root;
	long xMin, xMax, yMin, yMax;
	private transient int size = 0;
	final int bucketSize;

	/**
	 * You must use a hierarchy of node types with an abstract base class. You may
	 * use different names for the node types if you like (change displayHelper()
	 * accordingly).
	 */
	abstract class prQuadNode {
	}

	/**
	 * prQuadInternal is a concrete class of {@link prQuadNode}. It contains no
	 * elements it only contains pointers to other nodes.
	 */
	class prQuadInternal extends prQuadNode {
		prQuadNode NW, NE, SE, SW;

		long x, y, a, b;

		/**
		 * Sets the world dimensions for this internal node.
		 * 
		 * @param x
		 *          abscissa value of the south west corner of the region.
		 * @param a
		 *          abscissa value of the north east corner of the region.
		 * @param y
		 *          ordinate value of the south west corner of the region.
		 * @param b
		 *          ordinate value of the north east corner of the region.
		 */
		public void setDimensions(long x, long a, long y, long b) {
			this.x = x;
			this.y = y;
			this.a = a;
			this.b = b;
		}

		/**
		 * Returns the world dimensions of this internal node. Used in debugging.
		 * 
		 * @return String showing the world dimensions.
		 */
		@Override
		public String toString() {
			return "<(" + x + ", " + y + "), (" + a + ", " + b + ")>";
		}

		/**
		 * Returns a class field based off the direction enum given.
		 * 
		 * @param direction
		 *          Enum value from package {@link Direction}
		 * @return prQuadNode a class field.
		 */
		public prQuadNode getFieldFromDirection(Direction direction) {
			switch (direction) {
				case NE:
					return this.NE;
				case NW:
					return this.NW;
				case SW:
					return this.SW;
				case SE:
					return this.SE;
				default:
					throw new RuntimeException("No field found for this direction: "
					    + direction);
			}
		}

		/**
		 * Helper method to get class fields that point to another node.
		 * 
		 * @return the number of nodes that has something.
		 */
		private int numberOfChildNodes() {
			int numberOfChildren = 0;
			numberOfChildren += (this.NE == null) ? 0 : 1;
			numberOfChildren += (this.NW == null) ? 0 : 1;
			numberOfChildren += (this.SW == null) ? 0 : 1;
			numberOfChildren += (this.SE == null) ? 0 : 1;
			return numberOfChildren;
		}

		/**
		 * If this node is a candidate for compression, this method will return the
		 * leaf node to replace it with.
		 * 
		 * @return prQuadNode this node or a single child leaf node.
		 */
		public prQuadNode compressMe() {
			int numkids = this.numberOfChildNodes();
			if (numkids == 0) {
				return null;
			}
			if (numkids == 1) {
				if (this.NE != null) {
					return (prQuadtree.this.isLeaf(this.NE)) ? this.NE : this;
				}
				if (this.NW != null) {
					return (prQuadtree.this.isLeaf(this.NW)) ? this.NW : this;
				}
				if (this.SW != null) {
					return (prQuadtree.this.isLeaf(this.SW)) ? this.SW : this;
				}
				if (this.SE != null) {
					return (prQuadtree.this.isLeaf(this.SE)) ? this.SE : this;
				}
			}
			return this;
		}
	}

	/**
	 * prQuadLeaf is a concrete prQuadNode that only holds elements of size.
	 * {@link prQuadtree#bucketSize}.
	 */
	class prQuadLeaf extends prQuadNode {
		List<T> Elements = new ArrayList<T>(prQuadtree.this.bucketSize);

		/**
		 * Constructor. Takes an element and stores it in the field for this object.
		 * 
		 * @param element
		 *          The element that will be stored in this leaf node.
		 */
		public prQuadLeaf(T element) {
			this.Elements.add(element);
		}

		/**
		 * Returns element at specified index of the bucket.
		 * 
		 * @param index
		 *          the index of the element.
		 * @return the element at specified index.
		 */
		public T get(int index) throws ArrayIndexOutOfBoundsException {
			return this.Elements.get(index);
		}

		/**
		 * Returns the item stored in the leaf.
		 * 
		 * @see java.lang.Object#toString()
		 * @return prints the item in the bucket.
		 */
		public String toString() {
			return this.Elements.toString();
		}

		/**
		 * @return number elements stored in node.
		 */
		public int size() {
			return this.Elements.size();
		}
	}

	/**
	 * Initialize quadtree to empty state, representing the specified region.
	 * 
	 * @param xMin
	 *          abscissa value of the south west corner of the region.
	 * @param xMax
	 *          abscissa value of the north east corner of the region.
	 * @param yMin
	 *          ordinate value of the south west corner of the region.
	 * @param yMax
	 *          ordinate value of the north east corner of the region.
	 * @param bucketSize
	 *          size of the bucket in each leaf node.
	 */
	public prQuadtree(long xMin, long xMax, long yMin, long yMax, int bucketSize) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.bucketSize = bucketSize;
	}

	/**
	 * Initialize quadtree to empty state, representing the specified region.
	 * 
	 * @param xMin
	 *          abscissa value of the south west corner of the region.
	 * @param xMax
	 *          abscissa value of the north east corner of the region.
	 * @param yMin
	 *          ordinate value of the south west corner of the region.
	 * @param yMax
	 *          ordinate value of the north east corner of the region.
	 */
	public prQuadtree(long xMin, long xMax, long yMin, long yMax) {
		this(xMin, xMax, yMin, yMax, DEFAULT_BUCKET_CAPACITY);
	}

	/**
	 * Pre: elem != null Post: If elem lies in the tree's region, and a matching
	 * element occurs in the tree, then that element has been removed.
	 * 
	 * @param elem
	 *          Element to delete.
	 * @return true iff a matching element has been removed from the tree.
	 */
	public boolean delete(T elem) {
		assert elem != null : "Element is null";
		boolean deleted;
		try {
			this.root = this.delete(elem, this.root, this.xMin, this.xMax, this.yMin,
			    this.yMax);
			deleted = true;
			this.size--;
		}
		catch (Exception e) {
			deleted = false;
		}
		return deleted;
	}

	/**
	 * A helper method that search recursively for element to remove from the
	 * tree.
	 * 
	 * @param elem
	 *          Element to delete.
	 * @param node
	 *          starting node to search for element to delete.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return node that will replace the deleted element.
	 */
	private prQuadNode delete(T elem, prQuadNode node, long xLo, long xHi,
	    long yLo, long yHi) {
		if (node != null) {
			if (this.isLeaf(node)) {
				@SuppressWarnings("unchecked")
				prQuadLeaf leaf = (prQuadLeaf) node;
				for (T t : leaf.Elements) {
					if (t.equals(elem)) {
						t.fission(elem);
						elem = t;
						break;
					}
				}
				if (elem.isDeleted()) {
					node = (leaf.Elements.remove(elem)) ? leaf : node;
					node = (leaf.size() == 0) ? null : node;
				}
			}
			else {
				long midx = (xHi + xLo) / 2;
				long midy = (yHi + yLo) / 2;
				@SuppressWarnings("unchecked")
				prQuadInternal internal = (prQuadInternal) node;
				Direction quadrant = elem.directionFrom(midx, midy);
				switch (quadrant) {
					case NE:
						internal.NE = this.delete(elem, internal.NE, midx, xHi, midy, yHi);
					break;
					case NW:
						internal.NW = this.delete(elem, internal.NW, xLo, midx, midy, yHi);
					break;
					case SW:
						internal.SW = this.delete(elem, internal.SW, xLo, midx, yLo, midy);
					break;
					case SE:
						internal.SE = this.delete(elem, internal.SE, midx, xHi, yLo, midy);
					break;
					default:
				}
				node = internal.compressMe();
			}
		}
		return node;
	}

	/**
	 * Pre: xLo, xHi, yLo and yHi define a rectangular region Returns a collection
	 * of (references to) all elements x such that x is in the tree and x lies at
	 * coordinates within the defined rectangular region, including the boundary
	 * of the region.
	 * 
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return Vector<T> list of elements that are within the region.
	 */
	public Vector<T> find(long xLo, long xHi, long yLo, long yHi) {
		return this.find(this.root, xLo, xHi, yLo, yHi);
	}

	/**
	 * Helper method to recursively search for valid elements after node.
	 * 
	 * @param node
	 *          starting node.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return Vector<T> list of elements that are within the region.
	 */
	private Vector<T> find(prQuadNode node, long xLo, long xHi, long yLo, long yHi) {
		Vector<T> results = new Vector<T>();
		if (node != null) {
			boolean overlaps = this.overlaps(xLo, xHi, yLo, yHi);
			if (overlaps) {
				if (this.isLeaf(node)) {
					@SuppressWarnings("unchecked")
					prQuadLeaf leaf = (prQuadLeaf) node;
					for (T item : leaf.Elements) {
						if (item.inBox(xLo, xHi, yLo, yHi)) {
							results.add(item);
						}
					}
				}
				else {
					long midx = (xHi + xLo) / 2;
					long midy = (yHi + yLo) / 2;
					@SuppressWarnings("unchecked")
					prQuadInternal internal = (prQuadInternal) node;
					if (this.overlaps(midx, xHi, midy, yHi)) {
						results.addAll(this.find(internal.NE, xLo, xHi, yLo, yHi));
					}
					if (this.overlaps(xLo, midx, midy, yHi)) {
						results.addAll(this.find(internal.NW, xLo, xHi, yLo, yHi));
					}
					if (this.overlaps(xLo, midx, yLo, midy)) {
						results.addAll(this.find(internal.SW, xLo, xHi, yLo, yHi));
					}
					if (this.overlaps(midx, xHi, yLo, midy)) {
						results.addAll(this.find(internal.SE, xLo, xHi, yLo, yHi));
					}
				}
			}
		}
		return results;
	}

	/**
	 * Checks to see if coordinates overlap this tree's region.
	 * 
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return true if regions overlap.
	 */
	private boolean overlaps(long xLo, long xHi, long yLo, long yHi) {
		return (!((xLo > this.xMax) || (yLo > this.yMax) || (xHi < this.xMin) || (yHi < this.yMin)));
	}

	/**
	 * Pre: elem != null Returns reference to an element x within the tree such
	 * that elem.equals(x)is true, provided such a matching element occurs within
	 * the tree; returns null otherwise.
	 * 
	 * @param elem
	 *          Element to find within tree.
	 * @return reference to an element x within the tree.
	 */
	public T find(T elem) {
		assert elem != null : "Element cannot be null";
		return this.find(elem, this.root, this.xMin, this.xMax, this.yMin,
		    this.yMax);
	}

	/**
	 * Helper function to find an element with this tree.
	 * 
	 * @param elem
	 *          Element to find within tree.
	 * @param node
	 *          Node to start search from.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return reference to an element x within the tree.
	 */
	private T find(T elem, prQuadNode node, long xLo, long xHi, long yLo, long yHi) {
		Vector<T> items = this.find(node, xLo, xHi, yLo, yHi);
		int i = items.indexOf(elem);
		T item = (i >= 0) ? items.get(i) : null;
		return item;
	}

	/**
	 * Pre: elem != null Post: If elem lies within the tree's region, and elem is
	 * not already present in the tree, elem has been inserted into the tree.
	 * 
	 * @param elem
	 *          Element to insert within tree.
	 * @return true iff elem is inserted into the tree.
	 */
	public boolean insert(T elem) {
		/**
		 * if (elem.equals(this.find(elem))) { return false; }
		 */
		boolean inserted;
		try {
			this.root = this.insert(elem, this.root, this.xMin, this.xMax, this.yMin,
			    this.yMax);
			inserted = true;
			this.size++;
		}
		catch (Exception e) {
			inserted = false;
		}
		return inserted;
	}

	/**
	 * Helper function to insert an element into a tree.
	 * 
	 * @param elem
	 *          Element to insert within tree.
	 * @param node
	 *          Node to start search from.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return prQuadNode new root node.
	 */
	@SuppressWarnings("unchecked")
	private prQuadNode insert(T elem, prQuadNode node, long xLo, long xHi,
	    long yLo, long yHi) {
		// does the element belong?
		if (elem.inBox(xLo, xHi, yLo, yHi)) {
			// empty node?
			if (node == null) {
				// insert into prQuadLeaf
				node = new prQuadLeaf(elem);
			}
			else {
				// leaf node?
				if (this.isLeaf(node)) {
					prQuadLeaf leaf = ((prQuadLeaf) node);
					boolean isfused = false;
					for (T t : leaf.Elements) {
						if (t.equals(elem)) {
							t.fusion(elem);
							isfused = true;
							break;
						}
					}
					if (isfused) {
						node = leaf;
					}
					else {
						if (leaf.size() < this.bucketSize) {
							leaf.Elements.add(elem);
							node = leaf;
						}
						else {
							node = this.partitionLeaf(node, xLo, xHi, yLo, yHi);
							node = this.inserttElementAtInternal(elem, (prQuadInternal) node,
							    xLo, xHi, yLo, yHi);
						}
					}
				}
				else {
					// internal node
					node = this.inserttElementAtInternal(elem, (prQuadInternal) node,
					    xLo, xHi, yLo, yHi);
				}
			}
		}
		else {
			throw new RuntimeException("Element could not be inserted.");
		}
		return node;
	}

	/**
	 * Return true if node type is a leaf.
	 * 
	 * @param node
	 *          Node that will be tested.
	 * @return true if node is a leaf.
	 */
	private boolean isLeaf(prQuadNode node) {
		return node.getClass().getName().equals(prQuadLeaf.class.getName());
	}

	/**
	 * Helper method that will break a leaf node into an internal node.
	 * 
	 * @param node
	 *          Node to partition.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return prQuadInternal new root node.
	 */
	private prQuadInternal partitionLeaf(prQuadNode node, long xLo, long xHi,
	    long yLo, long yHi) {
		assert this.isLeaf(node) : "Node is not a leaf node.";

		@SuppressWarnings("unchecked")
		prQuadLeaf leaf = (prQuadLeaf) node;
		prQuadInternal internal = new prQuadInternal();
		for (T element : leaf.Elements) {
			internal = this.inserttElementAtInternal(element, internal, xLo, xHi,
			    yLo, yHi);
		}
		// T element = leaf.Elements.elementAt(0);
		return internal;
	}

	/**
	 * Helper method that inserts an element into the appropriate position of an
	 * internal node.
	 * 
	 * @param elem
	 *          element to insert.
	 * @param node
	 *          Node to partition.
	 * @param xLo
	 *          abscissa value of the south west corner of the region.
	 * @param xHi
	 *          abscissa value of the north east corner of the region.
	 * @param yLo
	 *          ordinate value of the south west corner of the region.
	 * @param yHi
	 *          ordinate value of the north east corner of the region.
	 * @return prQuadInternal new root node.
	 */
	private prQuadInternal inserttElementAtInternal(T elem, prQuadInternal node,
	    long xLo, long xHi, long yLo, long yHi) {
		assert !this.isLeaf(node) : "Node is a leaf node.";

		long midx = (xHi + xLo) / 2;
		long midy = (yHi + yLo) / 2;

		// new internal node that points to leaf
		Direction quadrant = elem.directionFrom(midx, midy);
		switch (quadrant) {
			case NE:
				node.NE = this.insert(elem, node.NE, midx, xHi, midy, yHi);
			break;
			case NW:
				node.NW = this.insert(elem, node.NW, xLo, midx, midy, yHi);
			break;
			case SW:
				node.SW = this.insert(elem, node.SW, xLo, midx, yLo, midy);
			break;
			case SE:
				node.SE = this.insert(elem, node.SE, midx, xHi, yLo, midy);
			break;
			default:
		}
		node.setDimensions(xLo, xHi, yLo, yHi);
		return (prQuadInternal) node;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.print("ROOT", this.root, "", true);
	}

	/**
	 * Print a tree of nodes.
	 * 
	 * @param expanded
	 *          Show more detail if true.
	 * @return a String containing the printed tree.
	 */
	public String print(boolean expanded) {
		return this.print("ROOT", this.root, "", true, expanded);
	}

	/**
	 * Print a tree of nodes.
	 * 
	 * @param descriptor
	 *          Help text to describe item printed.
	 * @param node
	 *          Current printed node.
	 * @param prefix
	 *          String prefix for this printed item.
	 * @param isLast
	 *          true if last item.
	 * @param expanded
	 *          Show more detail if true.
	 * @return a String containing the printed tree.
	 */
	@SuppressWarnings("unchecked")
	private String print(String descriptor, prQuadNode node, String prefix,
	    boolean isLast, boolean expanded) {
		if (node == null) {
			return "";
		}
		String n = (!expanded && !this.isLeaf(node)) ? "" : " " + node.toString();
		n = (expanded && this.isLeaf(node)) ? "" : n;
		StringBuilder output = new StringBuilder();
		output.append(this.print(descriptor + n, prefix, isLast));
		// Change prefix for traversal
		prefix = prefix + ((isLast) ? "    " : "│   ");
		if (this.isLeaf(node)) {
			if (expanded) {
				prQuadLeaf leaf = (prQuadLeaf) node;
				int size = leaf.Elements.size();
				for (int i = 0; i < size; i++) {
					T element = leaf.Elements.get(i);
					output.append(this.print(element.toString(), prefix,
					    (i == (size - 1))));
				}
			}
		}
		else {
			prQuadInternal internal = (prQuadInternal) node;
			output.append(this
			    .print("SW", internal.SW, prefix, ((internal.SE == null)
			        && (internal.NW == null) && (internal.NE == null)), expanded));
			output.append(this.print("SE", internal.SE, prefix,
			    ((internal.NW == null) && (internal.NE == null)), expanded));
			output.append(this.print("NW", internal.NW, prefix,
			    ((internal.NE == null)), expanded));
			output.append(this.print("NE", internal.NE, prefix, true, expanded));
		}

		return output.toString();
	}

	/**
	 * Print a tree of nodes.
	 * 
	 * @param descriptor
	 *          Help text to describe item printed.
	 * @param node
	 *          Current printed node.
	 * @param prefix
	 *          String prefix for this printed item.
	 * @param isLast
	 *          true if last item.
	 * @return a String containing the printed tree.
	 */
	private String print(String descriptor, prQuadNode node, String prefix,
	    boolean isLast) {
		return this.print(descriptor, node, prefix, isLast, false);
	}

	/**
	 * Print a tree of nodes.
	 * 
	 * @param element
	 *          String to print.
	 * @param prefix
	 *          String prefix for this printed item.
	 * @param isLast
	 *          true if last item.
	 * @return a String containing the printed row.
	 */
	private String print(String element, String prefix, boolean isLast) {
		String output = prefix;
		output += (isLast) ? "└── " : "├── ";
		output += element + '\n';
		return output;
	}

	/**
	 * @return the size
	 */
	public int size() {
		return size;
	}
}
