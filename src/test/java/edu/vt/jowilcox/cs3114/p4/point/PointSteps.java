package edu.vt.jowilcox.cs3114.p4.point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Direction;

/**
 * Step definitions for point class.
 * 
 * @author Jonavon Wilcox <jowilcox@vt.edu>
 */
public class PointSteps {
	private Point p;
	private Vector<Point> set;

	@Given("^a point x-coordinate (-?\\d+) and y-coordinate (-?\\d+)$")
	public void a_point_x_coordinate_and_y_coordinate_(long x, long y)
			throws Throwable {
		this.p = new Point(x, y);
	}

	@Then("^it should return (true|false) for box (-?\\d+), (-?\\d+), (-?\\d+), (-?\\d+)$")
	public void it_should_return_false_for_box(boolean inbox, double xLo,
			double yLo, double xHi, double yHi) throws Throwable {
		if (inbox) {
			assertTrue(p.inBox(xLo, xHi, yLo, yHi));
		} 
		else {
			assertFalse(p.inBox(xLo, xHi, yLo, yHi));
		}
	}

	@Then("^it should be (NE|SE|SW|NW|NOQUADRANT) from (\\d+), (\\d+)$")
	public void it_should_be_NE_from_(String quadrant, long x, long y)
			throws Throwable {
		assertEquals(Direction.valueOf(quadrant), p.directionFrom(x, y));
	}

	@Then("^it should return (NE|SE|SW|NW|NOQUADRANT) for rectangle \\((-?\\d+), (-?\\d+)\\) \\((-?\\d+), (-?\\d+)\\)$")
	public void it_should_return_NE_for_rectangle_(String quadrant, int xLo,
			int yLo, int xHi, int yHi) throws Throwable {
		assertEquals(Direction.valueOf(quadrant),
				p.inQuadrant(xLo, xHi, yLo, yHi));
	}

	@Given("^a point:$")
	public void a_point(List<Point> points) throws Throwable {
		this.set = new Vector<Point>();
		for (Point pt : points) {
			this.set.add(pt);
		}
	}

	@Then("^it should equal the point:$")
	public void it_should_equal_the_point(List<Point> points) throws Throwable {
		for (int i = 0; i < points.size(); i++) {
			Point pt = points.get(i);
			assertEquals(pt, this.set.get(i));
		}
	}

}
