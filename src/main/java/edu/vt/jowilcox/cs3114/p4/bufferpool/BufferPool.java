package edu.vt.jowilcox.cs3114.p4.bufferpool;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Object pool.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class BufferPool<K, V> implements Serializable {
	/** Generated serial version id */
	private static final long serialVersionUID = -4340171158534043845L;

	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * @param <L>
	 * @param <W>
	 */
	private class HashMapBuffer<L, W> extends LinkedHashMap<L, W> {
		private static final long serialVersionUID = -8751061971956633424L;
		private int capacity;

		/**
		 * @param capacity
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

	/**
   * 
   */
	static final int DEFAULT_CAPACITY = 20;

	/**
	 * 
	 */
	private static final float DO_NOT_RESIZE = 1.7f;

	/** Pool stack */
	private HashMapBuffer<K, V> pool;

	/**
	 * 
	 */
	private transient int longestk = 5;
	/**
	 * 
	 */
	private transient int longestv = 6;
  /** Pool Stack */
  
  /**
   * Constructor.
   */
  public BufferPool(){
  	this(DEFAULT_CAPACITY);
  }
  
  /**
   * Constructor.
   * @param size
   */
  public BufferPool(int capacity) {
  	this.pool = new HashMapBuffer<>(capacity);
  }
  
  /**
   * @param key
   * @return
   */
  public synchronized V get(K key) {
  	return this.pool.get(key);
  }
  
  /**
   * @param key
   * @param value
   */
  public synchronized void put(K key, V value) {
  	this.longestk = (this.longestk < key.toString().length())? key.toString().length() : this.longestk;
  	this.longestv = (this.longestv < value.toString().length())? value.toString().length() : this.longestv;
  	this.pool.put(key, value);
  }
  
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.print();
	}

	/**
	 * Create a string containing interal data.
	 *
	 * @return String internal data.
	 * @formatter:off
	 */
	public String debug() {
		String output ="";
		output += "     POOL SIZE: "+ this.size() + "\n";
		output += this.print();
		return output;
	}
	
	/**
	 * Return the length of the internal array.
	 * @return int current capacity of table.
	 */
	private int size() {
		return this.pool.size();
	}

	/**
	 * Return a string that contains a human readable version of the hash
	 * table.
	 * 
	 * @return a human readable table.
	 */
	private String print() {
		int k = this.longestk;
		int v = this.longestv;
		StringBuilder output = new StringBuilder();
		// Header row
		output.append("┏━");output.append(this.repeatText('━', 5)); output.append("━┳━"); output.append(this.repeatText('━', k));          output.append("━┳━"); output.append(this.repeatText('━', v));            output.append("━┓\n");
		output.append("┃ ");output.append(String.format("%-2s", "INDEX")); output.append(" ┃ "); output.append(String.format("%-"+k+"s", "KEY")); output.append(" ┃ "); output.append(String.format("%"+v+"s", "VALUES")); output.append(" ┃\n");
		output.append("┗━");output.append(this.repeatText('━', 5)); output.append("━┻━"); output.append(this.repeatText('━', k));          output.append("━┻━"); output.append(this.repeatText('━', v));            output.append("━┛\n");
		// Table rows
	  output.append("┌─");output.append(this.repeatText('─', 5)); output.append("─┬─"); output.append(this.repeatText('─', k));          output.append("─┬─"); output.append(this.repeatText('─', v));            output.append("─┐\n");
	  int i = 0;
		for(Entry<K, V> e : this.pool.entrySet()) {
			String key = e.getKey().toString();
			String val = e.getValue().toString();
			output.append("│ "); output.append(String.format("%02d   ", ++i)); output.append(" │ "); output.append(String.format("%"+k+"s", key)); output.append(" │ "); output.append(String.format("%-"+v+"s", val)); output.append(" │\n");
			output.append("├─"); output.append(this.repeatText('─', 5)); output.append("─┼─");output.append(this.repeatText('─', k)); output.append("─┼─"); output.append(this.repeatText('─', v)); output.append("─┤\n");
		}
		output.append("└─");output.append(this.repeatText('─', 5)); output.append("─┴─"); output.append(this.repeatText('─', k));          output.append("─┴─"); output.append(this.repeatText('─', v));            output.append("─┘\n");
		return output.toString();
	}

	/**
	 * Repeats one character a number of times.
	 * @param c the character to be repeated.
	 * @param number Number of times to repeat character.
	 * @return String of repeated text.
	 */
	private String repeatText(char c, int number) {
		assert number >= 0 : "Number must be greater than 0";
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < number; i++) {
			output.append(c);
		}
		return output.toString();
	}
}