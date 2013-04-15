package edu.vt.jowilcox.cs3114.p4;

import static org.junit.Assert.*;
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
		 * @param key the key to set
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
		 * @param value the value to set
		 */
		public void setValue(Integer value) {
			this.value = value;
		}
	}
	
	@Given("^I have a hash table$")
	public void I_have_a_hash_table() throws Throwable {
		this.table = new Hashtable<>();
	}

	@When("^I put several values:$")
	public void I_put_several_values(List<Entry> entries) throws Throwable {
		for(Entry entry : entries) {
			this.table.put(entry.getKey(), entry.getValue());
		}
	}

	@Then("^I should find these values:$")
	public void I_should_find_these_values(List<Entry> entries) throws Throwable {
		for(Entry entry : entries) {
			assertEquals(entry.getValue(),this.table.get(entry.getKey()));
		}
	}

}