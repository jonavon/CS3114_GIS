/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
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
	static final float INITIAL_PORTION = 0.70f;
	static final Integer[] RESIZE_ARRAY = new Integer[] { 31, 53, 97, 193, 389,
			769, 1019, 2027, 4079, 8123, 16267, 32503, 65011, 130027, 260111,
			520279, 1040387, 2080763, 4161539, 8323151, 16646323 };
	static final int INITIAL_CAPACITY = RESIZE_ARRAY[0];

	private Entry<K, V>[] table;
	private float portion;

	private transient int size;
	private transient int limit;
	private transient int collisions;
	private transient int capacityIndex = 0;

	/** Only used in debugging a printing */
	private transient int longestk = 4;
	/** Only used in debugging a printing */
	private transient int longestv = 6;

	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * 
	 * @param <L>
	 * @param <W>
	 */
	private class Entry<L, W> implements Map.Entry<L, W> {
		private L key;
		private W value;
		/**
		 * This object can be allocated to hold a spot after it's values have
		 * been deleted.
		 */
		private boolean isTombstone;

		public Entry(final L key, W value) {
			this.key = key;
			this.setValue(value);
			this.isTombstone = false;
		}

		@Override
		public L getKey() {
			return this.key;
		}

		@Override
		public W getValue() {
			return this.value;
		}

		@Override
		public W setValue(W value) {
			this.value = value;
			return this.value;
		}

		public Entry<L, W> delete() {
			this.isTombstone = true;
			return this;
		}

		public boolean isTombstone() {
			return this.isTombstone;
		}

		public String toString() {
			return this.getKey().toString();
		}
	}

	/**
	 * 
	 */
	public Hashtable() {
		this(INITIAL_CAPACITY);
	}

	public Hashtable(int capacity) {
		this(capacity, INITIAL_PORTION);
	}

	@SuppressWarnings("unchecked")
	public Hashtable(int capacity, float portion) {
		capacity = (capacity < INITIAL_CAPACITY) ? INITIAL_CAPACITY : capacity;
		this.portion = portion;
		this.table = new Entry[capacity];
		this.limit = ((int) (capacity * portion));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#size()
	 */
	@Override
	public int size() {
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		for (Map.Entry<K, V> v : this.table) {
			if (v.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		Entry<K, V> item = this.find(key);
		return (item == null) ? null : item.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		int hash = this.hash(key);
		int index = hash % this.table.length;
		this.collisions = 0;

		// Increase the table if we need to before we put a new entry.
		if (this.size >= this.limit) {
			this.increaseCapacity();
		}
		Map.Entry<K, V> inserted = this.insert(index, new Entry<>(key, value));
		return (inserted == null) ? null : inserted.getValue();
	}

	/**
	 * @param index
	 * @param entry
	 * @return
	 */
	private Map.Entry<K, V> insert(int index, Entry<K, V> entry) {
		if (this.table[index] == null || this.table[index].isTombstone()) {
			this.table[index] = entry;
			int l = entry.getKey().toString().length();
			this.longestk = (l > this.longestk) ? l : this.longestk;
			int m = entry.getValue().toString().length();
			this.longestv = (m > this.longestv) ? m : this.longestv;
			this.size++;
			this.collisions = 0;
		} else {
			int hash = this.rehash(entry.getKey());
			int idx = hash % this.table.length;
			if (this.collisions < this.table.length) {
				return this.insert(idx, entry);
			} else {
				// Hash table is full
				return null;
			}
		}
		return this.table[index];
	}

	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		if (++this.capacityIndex == RESIZE_ARRAY.length) {
			throw new ArrayIndexOutOfBoundsException(
					"We have reached the maximum size of this hash table.");
		}
		Entry<K, V>[] old = this.table;
		int capacity = RESIZE_ARRAY[this.capacityIndex];
		// reset
		this.table = new Entry[capacity];
		this.size = 0;
		this.limit = ((int) (capacity * this.portion));

		for (int i = 0; i < old.length; i++) {
			if (!(old[i] == null || old[i].isTombstone())) {
				int hash = this.hash(old[i].getKey());
				int index = hash % this.table.length;
				this.collisions = 0;
				this.insert(index, old[i]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	@Override
	public V remove(Object key) {
		Entry<K, V> removed = this.find(key);
		if (removed != null) {
			this.size--;
			return removed.delete().getValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Entry<K, V> find(Object key) {
		int hash;
		if (this.collisions == 0) {
			hash = this.hash((K) key);
		} else {
			this.collisions--; // corrective decrement
			hash = this.rehash((K) key);
		}
		int index = hash % this.table.length;
		if ((this.table[index] != null)) {
			if (!this.table[index].isTombstone()
					&& key.equals(this.table[index].getKey())) {
				this.collisions = 0; // reset
				return this.table[index];
			} else {
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
	 * 
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			this.put(entry.getKey(), entry.getValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#clear()
	 */
	@Override
	public void clear() {
		Entry<K, V>[] mesa = this.table;
		for(int i = 0; i < mesa.length; i++) {
			mesa[i] = null;
		}
		this.size = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#keySet()
	 */
	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#values()
	 */
	@Override
	public Collection<V> values() {
		Collection<V> values = new Vector<>(this.size());
		for (Map.Entry<K, V> value : this.table) {
			if (value != null) {
				values.add(value.getValue());
			}
		}
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Map#entrySet()
	 */
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> set = new ConcurrentSkipListSet<>();
		for (Map.Entry<K, V> value : this.table) {
			if (value != null) {
				set.add(value);
			}
		}
		return set;
	}

	/**
	 * @param k
	 * @return
	 */
	private int hash(K k) {
		int hash = 0;
		if (k != null) {
			if (k instanceof String) {
				hash = this.elfhash((String) k);
			} else {
				// not sure if this would work on 64 bit
				hash = (~k.hashCode() + 1) & 0x7FFFFFFF;

			}
		}
		return hash;
	}

	/**
	 * @param k
	 * @return
	 */
	private int elfhash(String k) {
		int elf = 0;
		for (int i = 0; i < k.length(); i++) { // use all characters
			elf = (elf << 4) + k.charAt(i);
			int hibits = elf & 0xF0000000; // get high nybble
			if (hibits != 0) {
				elf ^= hibits >> 24; // xor high nybble with second
			}
			elf &= ~hibits; // clear high nybble
		}
		return elf;
	}

	/**
	 * 
	 */
	private int rehash(K k) {
		int n = ++this.collisions;
		return this.hash(k) + (n * n + n) >> 2;
	}
	
	public String debug() {
		String output ="";
		output += "    NUMBER OF ITEMS: "+ this.size() + "\n";
		output += "     TABLE CAPACITY: "+ this.getCapacity() + "\n";
		output += "        TABLE LIMIT: "+ this.limit + "\n";
		output += this.print();
		return output;
	}
	
	private int getCapacity() {
		return this.table.length;
	}
	/**
	 * Returns a string that contains a human readable version of the hash
	 * table.
	 * 
	 * @return a human readable table.
	 */
	private String print() {
		int k = this.longestk;
		int v = this.longestv;
		StringBuilder output = new StringBuilder();
		output.append("┏━");output.append(this.repeatText('━', 8)); output.append("━┳━"); output.append(this.repeatText('━', k));          output.append("━┳━"); output.append(this.repeatText('━', v));            output.append("━┓\n");
		output.append("┃ ");output.append(String.format("%-8s", "INDEX")); output.append(" ┃ "); output.append(String.format("%-"+k+"s", "KEY")); output.append(" ┃ "); output.append(String.format("%"+v+"s", "VALUES")); output.append(" ┃\n");
		output.append("┗━");output.append(this.repeatText('━', 8)); output.append("━┻━"); output.append(this.repeatText('━', k));          output.append("━┻━"); output.append(this.repeatText('━', v));            output.append("━┛\n");
		
		/*
		output.append("  ");output.append(this.repeatText(' ', 8)); output.append(" ┏━"); output.append(this.repeatText('━', k));          output.append("━┳━"); output.append(this.repeatText('━', v));            output.append("━┓\n");
		output.append("  ");output.append(this.repeatText(' ', 8)); output.append(" ┃ "); output.append(String.format("%-"+k+"s", "KEY")); output.append(" ┃ "); output.append(String.format("%"+v+"s", "VALUES")); output.append(" ┃\n");
		output.append("  ");output.append(this.repeatText(' ', 8)); output.append(" ┗━"); output.append(this.repeatText('━', k));          output.append("━┻━"); output.append(this.repeatText('━', v));            output.append("━┛\n");
		*/
		
		output.append("┌─");output.append(this.repeatText('─', 8)); output.append("─┬─"); output.append(this.repeatText('─', k));          output.append("─┬─"); output.append(this.repeatText('─', v));            output.append("─┐\n");
		for(int i = 0; i < this.table.length; i++) {
			Entry<K,V> e = this.table[i];
			String key = (this.table[i] == null)? "": e.getKey().toString();
			String val = (this.table[i] == null)? "": e.getValue().toString();
			if(this.table[i] != null && this.table[i].isTombstone()) {
				output.append("│ "); output.append(String.format("%08d", i)); output.append(" │ "); output.append(this.repeatText('*', k+v+3)); output.append(" │\n");
			}
			else {
				output.append("│ "); output.append(String.format("%08d", i)); output.append(" │ "); output.append(String.format("%-"+k+"s", key)); output.append(" │ "); output.append(String.format("%"+v+"s", val)); output.append(" │\n");
			}
			output.append("├─"); output.append(this.repeatText('─', 8)); output.append("─┼─");output.append(this.repeatText('─', k)); output.append("─┼─"); output.append(this.repeatText('─', v)); output.append("─┤\n");
		}
		output.append("└─");output.append(this.repeatText('─', 8)); output.append("─┴─"); output.append(this.repeatText('─', k));          output.append("─┴─"); output.append(this.repeatText('─', v));            output.append("─┘\n");

		return output.toString();
	}

	private String repeatText(char c, int number) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < number; i++) {
			output.append(c);
		}
		return output.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.debug();
	}
	
}
