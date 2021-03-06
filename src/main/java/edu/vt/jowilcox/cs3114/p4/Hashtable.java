/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Hash table.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 * 
 */
public class Hashtable<K, V> implements Map<K, V> {
	/**
	 * Inner entry class that will be used to store objects.
	 * 
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * @param <L>
	 * @param <W>
	 */
	private class Entry<L, W> implements Map.Entry<L, W> {
		/** Key. */
		private final L key;
		/** Value. */
		private W value;
		/**
		 * This object can be allocated to hold a spot after it's values have been
		 * deleted.
		 */
		private boolean isTombstone;

		/**
		 * Constructor for entry.
		 * 
		 * @param key
		 *          Key for this entry.
		 * @param value
		 *          value to be stored along with key.
		 */
		public Entry(final L key, W value) {
			this.key = key;
			this.setValue(value);
			this.isTombstone = false;
		}

		/**
		 * Sets this Entry as a tombstone.
		 * 
		 * @return the Entry object.
		 */
		public Entry<L, W> delete() {
			this.isTombstone = true;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Map.Entry#getKey()
		 */
		@Override
		public L getKey() {
			return this.key;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Map.Entry#getValue()
		 */
		@Override
		public W getValue() {
			return this.value;
		}

		/**
		 * Determine if object has been deleted.
		 * 
		 * @return boolean True of object has been deleted.
		 */
		public boolean isTombstone() {
			return this.isTombstone;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Map.Entry#setValue(java.lang.Object)
		 */
		@Override
		public W setValue(W value) {
			this.value = value;
			return this.value;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return this.getKey().toString();
		}
	}

	static final float INITIAL_PORTION = 0.70f;
	// @formatter:off
	static final Integer[] RESIZE_ARRAY = new Integer[] {
		/* 31, 53, 97, 193, 389, 769, */1019, 2027, 4079,
	    8123, 16267, 32503, 65011, 130027, 260111, 520279,
			1040387, 2080763, 4161539, 8323151, 16646323 };

	// @formatter:on
	static final int INITIAL_CAPACITY = RESIZE_ARRAY[0];
	private Entry<K, V>[] table;

	private float portion;
	private transient int size;
	private transient int limit;
	private transient int collisions;

	private transient int capacityIndex = 0;
	/** Only used in debugging a printing. */
	private transient int longestk = 4;
	/** Only used in debugging a printing. */
	private transient int longestv = 6;

	private transient int longestProbe = 0;

	/**
	 * Constructor; generate hash table with the default capacity and default load
	 * factor.
	 */
	public Hashtable() {
		this(INITIAL_CAPACITY);
	}

	/**
	 * Constructor; generate hash table with specified capacity and default load
	 * factor.
	 * 
	 * @param capacity
	 *          Initial capacity.
	 */
	public Hashtable(int capacity) {
		this(capacity, INITIAL_PORTION);
	}

	/**
	 * Generate hash table.
	 * 
	 * @param capacity
	 *          initial capacity.
	 * @param portion
	 *          default load factor.
	 */
	@SuppressWarnings("unchecked")
	public Hashtable(int capacity, float portion) {
		capacity = (capacity < INITIAL_CAPACITY) ? INITIAL_CAPACITY : capacity;
		this.portion = portion;
		this.table = new Entry[capacity];
		this.limit = ((int) (capacity * portion));
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		final Entry<K, V>[] mesa = this.table;
		for (int i = 0; i < mesa.length; i++) {
			mesa[i] = null;
		}
		this.size = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		for (final Map.Entry<K, V> v : this.table) {
			if (v.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create a string containing interal data.
	 *
	 * @return String internal data.
	 * @formatter:off
	 */
	public String debug() {
		String output ="";
		output += "    NUMBER OF ITEMS: "+ this.size() + "\n";
		output += "     TABLE CAPACITY: "+ this.getCapacity() + "\n";
		output += "        TABLE LIMIT: "+ this.limit + "\n";
		output += "      LONGEST PROBE: "+ this.longestProbe + "\n";
		output += this.print();
		return output;
	}

	/**
	 * Return a hash based on a string.
	 * 
	 * @param k
	 *          key should be a string.
	 * @return a hash number.
	 */
	private long elfhash(String k) {
		long elf = 0;
		for (int i = 0; i < k.length(); i++) { // use all characters
			elf = (elf << 4) + k.charAt(i);
			final long hibits = elf & 0xF0000000L; // get high nybble
			if (hibits != 0) {
				elf ^= hibits >> 24; // xor high nybble with second
			}
			elf &= ~hibits; // clear high nybble
		}
		return elf;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		final Set<Map.Entry<K, V>> set = new ConcurrentSkipListSet<>();
		for (final Map.Entry<K, V> value : this.table) {
			if (value != null) {
				set.add(value);
			}
		}
		return set;
	}

	/**
	 * Find an Entry based on key.
	 * 
	 * @param key
	 *          key to search with.
	 * @return the Entry found or null if failed.
	 */
	@SuppressWarnings("unchecked")
	private Entry<K, V> find(Object key) {
		int hash;
		if (this.collisions == 0) {
			hash = (int) this.hash((K) key);
		}
		else {
			this.collisions--; // corrective decrement
			hash = this.rehash((K) key);
		}
		final int index = hash % this.table.length;
		if ((this.table[index] != null)) {
			if (!this.table[index].isTombstone()
			    && key.equals(this.table[index].getKey())) {
				this.collisions = 0; // reset
				return this.table[index];
			}
			else {
				// Increment collisions; Don't check more than number of table
				// rows
				if (this.collisions++ < this.table.length) {
					return this.find(key);
				}
			}
		}
		this.collisions = 0; // reset
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		final Entry<K, V> item = this.find(key);
		return (item == null) ? null : item.getValue();
	}

	/**
	 * Return the length of the internal array.
	 * @return int current capacity of table.
	 */
	private int getCapacity() {
		return this.table.length;
	}

	/**
	 * @return the longestProbe
	 */
	public int getLongestProbe() {
		return this.longestProbe;
	}

	/**
	 * Generate hash using key.
	 * 
	 * @param k
	 *          key to hash with.
	 * @return int a hash value.
	 */
	private long hash(K k) {
		long hash = 0;
		if (k != null) {
			if (k instanceof String) {
				hash = this.elfhash((String) k);
			}
			else {
				// not sure if this would work on 64 bit
				hash = (~k.hashCode() + 1) & 0x7FFFFFFF;

			}
		}
		return hash;
	}

	/**
	 * Increase the size of the internal array based on {@link #RESIZE_ARRAY}.
	 */
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		if (++this.capacityIndex == RESIZE_ARRAY.length) {
			throw new ArrayIndexOutOfBoundsException(
			    "We have reached the maximum size of this hash table.");
		}
		final Entry<K, V>[] old = this.table;
		final int capacity = RESIZE_ARRAY[this.capacityIndex];
		// reset
		this.table = new Entry[capacity];
		this.size = 0;
		this.limit = ((int) (capacity * this.portion));

		for (int i = 0; i < old.length; i++) {
			if (!((old[i] == null) || old[i].isTombstone())) {
				final long hash = this.hash(old[i].getKey());
				final long index = hash % this.table.length;
				this.collisions = 0;
				this.insert((int) index, old[i]);
			}
		}
	}

	/**
	 * Insert entry into the table.
	 * 
	 * @param index
	 *          position to insert the entry.
	 * @param entry
	 *          Entry to be inserted.
	 * @return The entry that is inserted or null if failed.
	 */
	private Map.Entry<K, V> insert(int index, Entry<K, V> entry) {
		if ((this.table[index] == null) || this.table[index].isTombstone()) {
			this.table[index] = entry;
			final int l = entry.getKey().toString().length();
			this.longestk = (l > this.longestk) ? l : this.longestk;
			final int m = entry.getValue().toString().length();
			this.longestv = (m > this.longestv) ? m : this.longestv;
			this.size++;
			this.longestProbe = (this.longestProbe < this.collisions) ? this.collisions
			    : this.longestProbe;
			this.collisions = 0;
		}
		else {
			if(this.table[index].key.equals(entry.key)) {
				final Entry<K, V> old = this.table[index];
				this.table[index] = entry;
				this.collisions = 0;
				// return old entry
				return old;
			}
			final int hash = this.rehash(entry.getKey());
			final int idx = hash % this.table.length;
			if (this.collisions < this.table.length) {
				return this.insert(idx, entry);
			}
			else {
				// Hash table is full
				return null;
			}
		}
		// end it
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		final Set<K> keys = new HashSet<K>();
		for (int i = 0; i < this.getCapacity(); i++) {
			if (this.table[i] != null) {
				keys.add(this.table[i].getKey());
			}
		}

		return keys;
	}

	/**
	 * Return a string that contains a human readable version of the hash
	 * table.
	 * 
	 * @return a human readable table.
	 */
	private String print() {
		final int k = this.longestk;
		final int v = this.longestv;
		final StringBuilder output = new StringBuilder();
		// Header row
		output.append("┏━");output.append(this.repeatText('━', 8)); output.append("━┳━"); output.append(this.repeatText('━', k));          output.append("━┳━"); output.append(this.repeatText('━', v));            output.append("━┓\n");
		output.append("┃ ");output.append(String.format("%-8s", "INDEX")); output.append(" ┃ "); output.append(String.format("%-"+k+"s", "KEY")); output.append(" ┃ "); output.append(String.format("%"+v+"s", "VALUES")); output.append(" ┃\n");
		output.append("┗━");output.append(this.repeatText('━', 8)); output.append("━┻━"); output.append(this.repeatText('━', k));          output.append("━┻━"); output.append(this.repeatText('━', v));            output.append("━┛\n");
		
		// Table rows
		this.printRows(k, v, output);

		return output.toString();
	}

	/**
	 * Appends row information to a {@link StringBuilder}.
	 * 
	 * @param k width of key column.
	 * @param v width of value column.
	 * @param output StringBuilder object to append.
	 */
	private void printRows(int k, int v, StringBuilder output) {
	  output.append("┌─");output.append(this.repeatText('─', 8)); output.append("─┬─"); output.append(this.repeatText('─', k));          output.append("─┬─"); output.append(this.repeatText('─', v));            output.append("─┐\n");
		for(int i = 0; i < this.table.length; i++) {
			 // Don't print null rows
			if(this.table[i] != null) {
				final Entry<K,V> e = this.table[i];
				final String key = (this.table[i] == null)? "": e.getKey().toString();
				final String val = (this.table[i] == null)? "": e.getValue().toString();
				if((this.table[i] != null) && this.table[i].isTombstone()) {
					output.append("│ "); output.append(String.format("%08d", i)); output.append(" │ "); output.append(this.repeatText('*', k+v+3)); output.append(" │\n");
				}
				else {
					output.append("│ "); output.append(String.format("%08d", i)); output.append(" │ "); output.append(String.format("%-"+k+"s", key)); output.append(" │ "); output.append(String.format("%"+v+"s", val)); output.append(" │\n");
				}
				output.append("├─"); output.append(this.repeatText('─', 8)); output.append("─┼─");output.append(this.repeatText('─', k)); output.append("─┼─"); output.append(this.repeatText('─', v)); output.append("─┤\n");
			}
		}
		output.append("└─");output.append(this.repeatText('─', 8)); output.append("─┴─"); output.append(this.repeatText('─', k));          output.append("─┴─"); output.append(this.repeatText('─', v));            output.append("─┘\n");
  }

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		final long hash = this.hash(key);
		final long index = hash % this.table.length;
		this.collisions = 0;

		// Increase the table if we need to before we put a new entry.
		if (this.size >= this.limit) {
			this.increaseCapacity();
		}
		final Map.Entry<K, V> inserted = this
		    .insert((int) index, new Entry<>(key, value));
		return (inserted == null) ? null : inserted.getValue();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (final Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Rehash a key.
	 * 
	 * @param k
	 *          Key.
	 * @return int new hash.
	 */
	private int rehash(K k) {
		final int n = ++this.collisions;
		return ((int) this.hash(k) + ((n * n) + n)) >> 2;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		final Entry<K, V> removed = this.find(key);
		if (removed != null) {
			this.size--;
			return removed.delete().getValue();
		}
		return null;
	}
	/**
	 * Repeats one character a number of times.
	 * @param c the character to be repeated.
	 * @param number Number of times to repeat character.
	 * @return String of repeated text.
	 */
	private String repeatText(char c, int number) {
		assert number >= 0 : "Number must be greater than 0";
		final StringBuilder output = new StringBuilder();
		for (int i = 0; i < number; i++) {
			output.append(c);
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.print();
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		final Collection<V> values = new ArrayList<>(this.size());
		for (final Map.Entry<K, V> value : this.table) {
			if (value != null) {
				values.add(value.getValue());
			}
		}
		return values;
	}

}
