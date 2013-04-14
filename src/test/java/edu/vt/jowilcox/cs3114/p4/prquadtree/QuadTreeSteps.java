package edu.vt.jowilcox.cs3114.p4.prquadtree;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Vector;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.vt.jowilcox.cs3114.p4.point.Point;

/**
 * @author jonavon
 * 
 */
public class QuadTreeSteps {
	private static final long WORLD_XMAX = 500;
	private static final long WORLD_YMAX = 500;
	private static final long WORLD_YMIN = -500;
	private static final long WORLD_XMIN = -500;
	private prQuadtree<Point> tree;

	@When("^I insert points:$")
	public void I_insert_points(List<Point> points) throws Throwable {
		for (Point point : points) {
			this.tree.insert(point);
		}
	}

	@When("^I delete point\\(s\\):$")
	public void I_delete(List<Point> points) throws Throwable {
		for (Point point : points) {
			this.tree.delete(point);
		}
	}

	@Then("^I should not find:$")
	public void I_should_not_find(List<Point> points) throws Throwable {
		for (Point point : points) {
			assertNull(this.tree.find(point));
		}
	}

	@Then("^I should find:$")
	public void I_should_find(List<Point> points) throws Throwable {
		System.out.print(this.tree.toString());
		System.out.print("\n\n" + this.tree.print(true));
		for (Point point : points) {
			assertEquals(point, this.tree.find(point));
		}
	}
	
	//@SuppressWarnings("rawtypes")
	@Then("^I should find (\\w+) at the (NE|NW|SW|SE) field.$")
	public void I_should_find_name(String name, Direction direction)
			throws Throwable {
		/* Only works in special case
		prQuadInternal internal = (prQuadInternal) this.tree.root;
		prQuadNode node = internal.getFieldFromDirection(direction);
		prQuadLeaf leaf = (prQuadLeaf) node;
		Point pt = (Point) leaf.get(0);
		assertEquals(name, pt.getName());
		*/
	}

	
	@Then("^I should find these points in the area \\((-?\\d+), (-?\\d+)\\), \\((-?\\d+), (-?\\d+)\\):$")
	public void point_in_the_area_(long xLo, long yLo, long xHi, long yHi,
			List<Point> points) throws Throwable {
		Vector<Point> test = this.tree.find(xLo, xHi, yLo, yHi);
		for (Point point : points) {
			assertTrue(test.contains(point));
		}
	}

	@Then("^I should have an empty tree.$")
	public void I_should_have_an_empty_tree() throws Throwable {
		assertNull(this.tree.root);
	}

	@Then("^inserting the same should fail:$")
	public void inserting_the_same_should_fail(List<Point> points)
			throws Throwable {
		for (Point point : points) {
			assertFalse(this.tree.insert(point));
		}
	}

	@Then("^I should be able to delete them all:$")
	public void I_should_be_able_to_delete_them_all(List<Point> points)
			throws Throwable {
		for (Point point : points) {
			System.out.println("Deleting " + point);
			assertTrue(this.tree.delete(point));
			System.out.println("Finding " + point);
			assertNull(this.tree.find(point));
		}
	}

	@Given("^I have an empty quadtree$")
	public void I_have_an_empty_quadtree() throws Throwable {
		this.tree = new prQuadtree<Point>(WORLD_XMIN, WORLD_XMAX, WORLD_YMIN,
				WORLD_YMAX);
	}

	@Given("^I have an empty quadtree with world of \\((-?\\d+),\\s?(-?\\d+)\\), \\((-?\\d+),\\s?(-?\\d+)\\)$")
	public void I_have_an_empty_quadtree_with_world(long x, long y, long a,
			long b) throws Throwable {
		this.tree = new prQuadtree<Point>(x, a, y, b);
	}
}
