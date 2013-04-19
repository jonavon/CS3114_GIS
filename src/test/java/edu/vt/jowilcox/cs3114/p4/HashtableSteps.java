package edu.vt.jowilcox.cs3114.p4;

import static org.junit.Assert.*;

//import java.lang.reflect.Method;
import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author jonavon
 * 
 */
public class HashtableSteps {
	private Hashtable<String, Integer> table;

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

	@Given("^I have a hash table$")
	public void I_have_a_hash_table() throws Throwable {
		this.table = new Hashtable<>();
	}

	@When("^I put several values:$")
	public void I_put_several_values(List<Entry> entries) throws Throwable {
		for (Entry entry : entries) {
			this.table.put(entry.getKey(), entry.getValue());
		}
	}
	
	@When("^I remove values:$")
	public void I_remove_values(List<Entry> entries) throws Throwable {
		for (Entry entry : entries) {
			this.table.remove(entry.getKey());
		}
	}

	@Then("^I should find these values:$")
	public void I_should_find_these_values(List<Entry> entries)
			throws Throwable {
		for (Entry entry : entries) {
			assertEquals(entry.getValue(), this.table.get(entry.getKey()));
		}
	}

	@Then("^I should not find these values:$")
	public void I_should_not_find_these_values(List<Entry> entries)
			throws Throwable {
		for (Entry entry : entries) {
			assertNull(this.table.get(entry.getKey()));
		}
	}
	
	@When("^when I put a value (\\d+) with key \"([^\"]*)\"$")
	public void when_I_put_a_value_with_key(int value, String key)
			throws Throwable {
		this.table.put(key, value);
	}

//	@SuppressWarnings("static-access")
	@Then("^the internal table size should increase$")
	public void the_internal_table_size_should_increase() throws Throwable {
		/*
		Method m = this.table.getClass().getDeclaredMethod("getCapacity");
		m.setAccessible(true);
		int current = (int) m.invoke(this.table);

		assertTrue(current > this.table.INITIAL_CAPACITY);
		*/
	}
	
	@Then("^the table size should be (\\d+)$")
	public void the_table_size_should_be(int expectedSize) throws Throwable {
		assertEquals(expectedSize, this.table.size());
	}
	
	@When("^clear the table$")
	public void clear_the_table() throws Throwable {
		this.table.clear();
	}


}