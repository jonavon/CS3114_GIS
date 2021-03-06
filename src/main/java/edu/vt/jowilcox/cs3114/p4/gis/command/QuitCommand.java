package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class QuitCommand.
 * 
 * Terminate program execution.
 */
public class QuitCommand extends AbstractCommand {

	//
	// Constructors
	//

	/**
	 * Constructor.
	 */
	public QuitCommand() { }

	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
	public QuitCommand(String args) { }

	@Override
	public void execute() {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		StringBuilder o = new StringBuilder();
		o.append("Completed:\t").append(dateformat.format(c.getTime())).append("\n");
		this.logfile.log(o.toString());
		System.out.println("Quit command given! Exiting.");
	}
}
