/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 *
 */
public class GIS {
	
	private static final String ERR_FILE = "Could not load files";
	private static GISRecordsFile database;
	private static RandomAccessFile commands;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Check for valid arguments
		
		// Initialize files
		try {
	    database = new GISRecordsFile(args[0]);
	    
    }
    catch (FileNotFoundException e) {
    	System.err.println(ERR_FILE);
    	System.err.println(e.getMessage());
    }
		// Run commands
	}

}
