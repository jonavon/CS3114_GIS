package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;


/**
 * Interface ICommand
 */
public interface ICommand {
	//
	// Methods
	//
	/**
	 * Execute a command.
	 */
	public void execute();
	public void setDatabase(GISDatabaseFile database);
	public void setLogfile(GISLogFile database);
}
