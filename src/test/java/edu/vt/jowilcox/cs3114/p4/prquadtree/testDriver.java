package edu.vt.jowilcox.cs3114.p4.prquadtree;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import edu.vt.jowilcox.cs3114.p4.point.Point;

// Run this with a command-line argument (any argument) to run with random data:
//
//    java testDriver seedvalue
//
// You can obtain seed values from the posted runs on the course website and 
// use them to compare your results to mine.
//
// Run without a command-line argument, this will generate random data for
// the test.
//
// This file goes in the src directory of your Eclipse project and the actual test
// harness Lewis.java goes in the Minor/P3/DS subdirectory.
public class testDriver {

	static int xMin = 0; // world is [0, 2^15] on all sides
	static int xMax = 1 << 15;
	static int yMin = 0;
	static int yMax = 1 << 15;
	static Vector<Point> data;
	static Vector<Long> seps; // holds subregion boundaries
	static Random source;
	static boolean profMode = false;
	static long Seed = -1;

	public static void main(String[] args) throws IOException {

		if (args.length > 0) { // expecting a seed value in args[0]
			profMode = false;
			Seed = Long.parseLong(args[0]);
		} else {
			profMode = true;
			Seed = System.currentTimeMillis();
		}

		seps = new Vector<Long>();
		computePartition(10);

		String summaryName = "Summary.txt";
		if (profMode)
			summaryName = "prof" + summaryName;
		FileWriter summary = new FileWriter(summaryName);
		summary.write("Seed value for this test: " + Seed + "\n");
		data = new Vector<Point>();
		generatePoints();

		for (int i = 0; i < data.size(); i++) {
			System.out.println(i + ":  " + data.elementAt(i));
		}

		@SuppressWarnings("unused")
		long minsep = checkScatterAll();

		Lewis louie = new Lewis(xMin, xMax, yMin, yMax, data, source, profMode);

		int numTestsRun = 0;
		try {
			checkTreeInitialization(louie, summary);
			++numTestsRun;

			checkInsertion(louie, summary);
			++numTestsRun;

			checkDeletion(louie, summary);
			++numTestsRun;
		} catch (Exception e) {
			System.out.println("Exception caught in main: " + e.getMessage());
		}

		summary.write(" Completed " + numTestsRun + " tests.\n");
		summary.close();
	}

	private static void checkTreeInitialization(Lewis louie, FileWriter summary)
			throws IOException {

		try {
			louie.checkTreeInitialization();
		} catch (Exception e) {
			summary.write(" OOPS: caught an exception from call to checkTreeInitialization().\n");
		}
		summary.write(" Completed test of quadtree initialization.\n");
	}

	private static void checkInsertion(Lewis louie, FileWriter summary)
			throws IOException {

		try {
			louie.checkInsertion();
		} catch (Exception e) {
			summary.write(" OOPS: caught an exception from call to checkInsertion().\n");
		}
		summary.write(" Completed test of quadtree insertion.\n");
	}

	private static void checkDeletion(Lewis louie, FileWriter summary)
			throws IOException {

		try {
			louie.checkDeletion();
		} catch (Exception e) {
			summary.write(" OOPS: caught an exception from call to checkDeletion().\n");
		}
		summary.write(" Completed test of quadtree deletion.\n");
	}

	private static void generatePoints() throws IOException {

		source = new Random(Seed);
		int numPts = 20 + Math.abs(source.nextInt()) % 21;

		int pt = 0;
		while (pt < numPts) {
			long x = Math.abs(source.nextInt()) % xMax;
			long y = Math.abs(source.nextInt()) % yMax;

			if (seps.contains(x)) {
				++x;
			}
			if (seps.contains(y)) {
				++y;
			}

			Point nxt = new Point(x, y);
			if (checkScatterOK(nxt, 4L)) {
				if (!data.contains(nxt)) {
					++pt;
					data.add(nxt);
				} else {
					System.out.println("too close");
				}
			} else {
				System.out.println("checkScatterOK() said no");
			}
		}
	}

	private static boolean checkScatterOK(Point A, long Min) {

		for (int i = 0; i < data.size(); i++) {
			Point N = data.get(i);
			if (taxiDistance(A, N) < Min)
				return false;
		}
		return true;
	}

	private static long taxiDistance(Point A, Point B) {

		return Math.abs(A.getX() - B.getX()) + Math.abs(A.getY() - B.getY());
	}

	private static long checkScatterAll() {

		long minimumSeparation = (1 << 20);
		for (int i = 0; i < data.size(); i++) {
			Point A = data.get(i);
			for (int j = 0; j < data.size(); j++) {
				if (j != i) {
					Point B = data.get(j);
					long currSeparation = taxiDistance(A, B);
					if (currSeparation < minimumSeparation)
						minimumSeparation = currSeparation;
				}
			}
		}
		return minimumSeparation;
	}

	private static void computePartition(int Divisions) {

		int numParts = 1 << Divisions;
		int Step = (xMax - xMin) / numParts;
		for (int lvl = 0; lvl <= numParts; lvl++) {

			long x = xMin + lvl * Step;
			seps.add(x);
		}
	}
}
