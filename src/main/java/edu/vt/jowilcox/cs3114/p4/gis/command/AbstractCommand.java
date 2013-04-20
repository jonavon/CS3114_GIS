package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;

/**
 * Class AbstractCommand
 */
public abstract class AbstractCommand implements ICommand {

	protected GISDatabaseFile database;
	protected GISLogFile logfile;
	protected String args;

	//
	// Constructors
	//
	// TODO
	public AbstractCommand(String args) {
		this.args = args;
	}

	// TODO
	public AbstractCommand(String... args) {
	}

	public void setDatabase(GISDatabaseFile database) {
		this.database = database;
	}

	public void setLogfile(GISLogFile logfile) {
		this.logfile = logfile;
	}
}
