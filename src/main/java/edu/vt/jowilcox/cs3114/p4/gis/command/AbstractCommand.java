package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;

/**
 * Class AbstractCommand.
 */
public abstract class AbstractCommand implements ICommand {

	protected GISDatabaseFile database;
	protected GISLogFile logfile;
	protected String args;

	//
	// Constructors
	//
	/**
	 * Constructor.
	 * @param args arguments for the commands
	 */
	public AbstractCommand(String args) {
		this.args = args;
	}

	/**
	 * Constructor.
	 * @param args arguments for the commands
	 */
	public AbstractCommand(String... args) {
	}

	/* (non-Javadoc)
	 * @see edu.vt.jowilcox.cs3114.p4.gis.command.ICommand#setDatabase(edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile)
	 */
	@Override
	public void setDatabase(GISDatabaseFile database) {
		this.database = database;
	}

	/* (non-Javadoc)
	 * @see edu.vt.jowilcox.cs3114.p4.gis.command.ICommand#setLogfile(edu.vt.jowilcox.cs3114.p4.gis.GISLogFile)
	 */
	@Override
	public void setLogfile(GISLogFile logfile) {
		this.logfile = logfile;
	}
}
