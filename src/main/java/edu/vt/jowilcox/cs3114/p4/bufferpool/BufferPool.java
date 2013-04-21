package edu.vt.jowilcox.cs3114.p4.bufferpool;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Object pool. An LRU pool. It prints in UTF-format.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 *
 * @param <K> Key.
 * @param <V> Value.
 */
public class BufferPool<K, V> implements Serializable {
	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * @param <L> Key.
	 * @param <W> Value.
	 */
	private class HashMapBuffer<L, W> extends LinkedHashMap<L, W> {
		private static final long serialVersionUID = -8751061971956633424L;
		private final int capacity;

		/**
		 * Maximum number of items that can be held by this buffer pool.
		 * 
		 * @param capacity maximum capacity for the buffer.
		 */
		public HashMapBuffer(int capacity) {
			super(capacity + 1, BufferPool.DO_NOT_RESIZE, true);
			this.capacity = capacity;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
		 */
		@Override
		protected boolean removeEldestEntry(Map.Entry<L, W> eldest) {
			return this.size() > this.capacity;
		}
	}

	/** Generated serial version id. */
	private static final long serialVersionUID = -4340171158534043845L;

	/** The capacity if no capacity is set. */
	static final int DEFAULT_CAPACITY = 20;

	/** Constant to prevent resize. */
	private static final float DO_NOT_RESIZE = 1.7f;

	/** Pool stack. */
	private HashMapBuffer<K, V> pool;

	/** Used for printing. */
	private transient int longestk = 5;
	/** Used for printing. */
	private transient int longestv = 6;
	/** Used for printing. */
	private transient int trys = 0;
	/** Used for printing. */
	private transient int hits = 0;

	/**
	 * Constructor.
	 */
	public BufferPool() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor.
	 * 
	 * @param capacity maximum size of the buffer pool.
	 */
	public BufferPool(int capacity) {
		this.pool = new HashMapBuffer<>(capacity);
	}

	/**
	 * Create a string containing internal data.
	 *
	 * @return String internal data.
	 * @formatter:off
	 */
	public String debug() {
		String output ="";
		output += "POOL SIZE: "+ this.size() + "\n";
		output += " HIT RATE: "+ String.format("%3.2f", (((float) this.hits / (float) this.trys) * 1.00f)) + "\n";
		output += this.print();
		return output;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 * 
	 * @param key Key.
	 * @return V the value for the specified key.
	 */
	public synchronized V get(K key) {
		final V value = this.pool.get(key);
		this.trys++;
		this.hits += (value == null) ? 0 : 1;
		return value;
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
		output.append("┏━");output.append(this.repeatText('━', 5)); output.append("━┳━"); output.append(this.repeatText('━', k));          output.append("━┳━"); output.append(this.repeatText('━', v));            output.append("━┓\n");
		output.append("┃ ");output.append(String.format("%-2s", "INDEX")); output.append(" ┃ "); output.append(String.format("%-"+k+"s", "KEY")); output.append(" ┃ "); output.append(String.format("%"+v+"s", "VALUES")); output.append(" ┃\n");
		output.append("┗━");output.append(this.repeatText('━', 5)); output.append("━┻━"); output.append(this.repeatText('━', k));          output.append("━┻━"); output.append(this.repeatText('━', v));            output.append("━┛\n");
		// Table rows
	  output.append("┌─");output.append(this.repeatText('─', 5)); output.append("─┬─"); output.append(this.repeatText('─', k));          output.append("─┬─"); output.append(this.repeatText('─', v));            output.append("─┐\n");
	  int i = 0;
		for(final Entry<K, V> e : this.pool.entrySet()) {
			final String key = e.getKey().toString();
			final String val = e.getValue().toString();
			output.append("│ "); output.append(String.format("%02d   ", ++i)); output.append(" │ "); output.append(String.format("%"+k+"s", key)); output.append(" │ "); output.append(String.format("%-"+v+"s", val)); output.append(" │\n");
			output.append("├─"); output.append(this.repeatText('─', 5)); output.append("─┼─");output.append(this.repeatText('─', k)); output.append("─┼─"); output.append(this.repeatText('─', v)); output.append("─┤\n");
		}
		output.append("└─");output.append(this.repeatText('─', 5)); output.append("─┴─"); output.append(this.repeatText('─', k));          output.append("─┴─"); output.append(this.repeatText('─', v));            output.append("─┘\n");
		return output.toString();
	}

	/**
	 * Associates the specified value with the specified key in this map. If the 
	 * map previously contained a mapping for the key, the old value is replaced.
	 * 
	 * @param key Key.
	 * @param value Value.
	 */
	public synchronized void put(K key, V value) {
		this.longestk = (this.longestk < key.toString().length()) ? key.toString()
		    .length() : this.longestk;
		this.longestv = (this.longestv < value.toString().length()) ? value
		    .toString().length() : this.longestv;
		this.pool.put(key, value);
	}
	
	/**
	 * Repeats one character a number of times.
	 * 
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

	/**
	 * Return the number items stored within the buffer pool.
	 * 
	 * @return int current capacity of table.
	 */
	private int size() {
		return this.pool.size();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.print();
	}
}