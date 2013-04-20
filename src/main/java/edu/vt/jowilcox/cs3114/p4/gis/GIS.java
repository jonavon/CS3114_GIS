/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		try {
			controller.enqueue(commands.readAll());
			StringBuilder sb = new StringBuilder();
			sb.append("Database File\n:\t" + args[0] + "\n\n");
			sb.append("Commands File\n:\t" + args[1] + "\n\n");
			sb.append("Log File\n:\t" + args[2] + "\n\n");
			sb.append("Start Time\n:\t" + dateformat.format(c.getTime()) + "\n");
			logfile.log(sb.toString());
			controller.run();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Repeats one character a number of times.
	 * 
	 * @param c
	 *          the character to be repeated.
	 * @param number
	 *          Number of times to repeat character.
	 * @return String of repeated text.
	 */
	public static String repeatText(char c, int number) {
		assert number >= 0 : "Number must be greater than 0";
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < number; i++) {
			output.append(c);
		}
		return output.toString();
	}

	/**
	 * Similar to PHP's implode.
	 * 
	 * @param separator
	 * @param data
	 * @return
	 */
	public static String implode(String separator, String... data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length - 1; i++) {
			// data.length - 1 => to not add separator at the end
			if (data[i] != null) {
				if (!data[i].matches(" *")) {// empty string are ""; " "; "  "; and so
																		 // on
					sb.append(data[i]);
					sb.append(separator);
				}
			}
			else {
				sb.append(separator);
			}
		}
		if(data[data.length - 1] != null) {
			sb.append(data[data.length - 1]);
		}
		return sb.toString();
	}

}
