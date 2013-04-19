/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.IOException;

import edu.vt.jowilcox.cs3114.p4.gis.command.GISCommandInvoker;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GIS {

	private static final String ERR_FILE = "Could not load files";
	private static GISDatabaseFile database;
	private static CommandsFile commands;
	private static GISLogFile logfile;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Check for valid arguments

		// Initialize files
		try {
			database = new GISDatabaseFile(args[0]);
			commands = new CommandsFile(args[1]);
			logfile = new GISLogFile(args[2]);
			
			database.truncate();
			logfile.truncate();
		}
		catch (IOException e) {
			System.err.println(ERR_FILE);
			System.err.println(e.getMessage());
		}
		// Run commands
		GISCommandInvoker controller = new GISCommandInvoker(database, logfile);
		try {
			controller.enqueue(commands.readAll());
			StringBuilder sb = new StringBuilder();
			sb.append("Database File: " + args[0] + "\n");
			sb.append("Commands File: " + args[1] + "\n");
			sb.append("     Log File: " + args[2] + "\n");
			logfile.log(sb.toString());
			controller.run();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
