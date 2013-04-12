package edu.vt.jowilcox.cs3114.p4.prquadtree;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import edu.vt.jowilcox.cs3114.p4.point.Point;

/**
 * @author jonavon
 * 
 */
public class LewisSteps {
	private Lewis lewis;

	@Given("^I have a lewis \\((-?\\d+), (-?\\d+)\\), \\((-?\\d+), (-?\\d+)\\) element with this points:$")
	public void I_have_a_lewis_element_with_this_points(int x, int y, int a,
			int b, List<Point> pts) throws Throwable {
		Vector<Point> points = new Vector<Point>(pts);
		Random r = new Random();

		lewis = new Lewis(x, a, y, b, points, r, false);
	}

	@Then("^Check the tree initialization$")
	public void Check_the_tree_initialization() throws Throwable {
		lewis.checkTreeInitialization();
	}

	@Then("^Check tree insertion$")
	public void Check_tree_insertion() throws Throwable {
		lewis.checkInsertion();
	}

	@Then("^check delete$")
	public void check_delete() throws Throwable {
		lewis.checkDeletion();
	}
}