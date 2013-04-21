package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;


/**
 * Interface ICommand.
 */
public interface ICommand {
	//
	// Methods
	//
	/**
	 * Execute a command.
	 */
	public void execute();
	
	/**
	 * Set a database object. 
	 * @param database the database object.
	 */
	public void setDatabase(GISDatabaseFile database);
	
	/**
	 * Set a log file object.
	 * @param database the log file object.
	 */
	public void setLogfile(GISLogFile database);
}
