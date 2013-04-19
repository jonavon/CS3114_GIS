package edu.vt.jowilcox.cs3114.p4.gis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.vt.jowilcox.cs3114.p4.bufferpool.BufferPool;

/**
 * @author jonavon
 */
@SuppressWarnings("unused")
public class BufferPoolSteps {
	private BufferPool<String, Integer> pool;
	
	class Entry {
		private String key;
		private Integer value;

		public Entry(String key, Integer value) {
			this.setKey(key);
			this.setValue(value);
		}

		/**
		 * @return the key
		 */
		public String getKey() {
			return key;
		}

		/**
		 * @param key
		 *            the key to set
		 */
		public void setKey(String key) {
			this.key = key;
		}

		/**
		 * @return the value
		 */
		public Integer getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the value to set
		 */
		public void setValue(Integer value) {
			this.value = value;
		}
		
		public String toString() {
			return this.key;
		}
	}
	
	@Given("^I have a new buffer pool$")
	public void I_have_a_new_buffer_pool() throws Throwable {
		this.pool = new BufferPool<>();
	}

	@When("^I load these records:$")
	public void I_load_these_records(List<Entry> records) throws Throwable {
		for(Entry e : records) {
			this.pool.put(e.getKey(), e.getValue());
		}
	}

	@Then("^I should find these pooled records:$")
	public void I_should_find_these_pooled_records(List<Entry> records) throws Throwable {
		for(Entry e : records) {
			assertEquals(e.getValue(), this.pool.get(e.getKey()));
		}
	}
}