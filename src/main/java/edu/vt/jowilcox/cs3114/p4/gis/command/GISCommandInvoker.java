package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;

/**
 * Class GISCommandInvoker.
 * 
 * Invokes a list of commands.
 */
public class GISCommandInvoker {
	private Queue<String> commands;
	private GISDatabaseFile database;
	private GISLogFile logfile;
	private static final String COMMENT_PREFIX = ";";
	private static final String COMMANDS_EXIT = "quit";

	//
	// Fields
	//
	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * @formatter:off
	 */
	private enum Command {
		// @formatter:off
		WORLD(WorldCommand.class)
		, IMPORT(ImportCommand.class)
		, WHAT_IS(WhatIsCommand.class)
		, WHAT_IS_AT(WhatIsAtCommand.class)
		, WHAT_IS_IN(WhatIsInCommand.class)
		, DEBUG(DebugCommand.class)
		, QUIT(QuitCommand.class)
		;
		// @formatter:on

		/** Class object. */
		private Class<? extends ICommand> clazz;

		/**
		 * Constructor.
		 * 
		 * @param clazz class this enum constant represents.
		 */
		private Command(Class<? extends ICommand> clazz) {
			this.clazz = clazz;
		}

		/**
		 * Create a concrete command depending on the enum.
		 * 
		 * @return a concrete command object.
		 * @throws InstantiationException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 */
		public ICommand createCommandObject() throws InstantiationException,
		    IllegalAccessException, IllegalArgumentException,
		    InvocationTargetException, NoSuchMethodException, SecurityException {
			return this.clazz.getDeclaredConstructor().newInstance();
		}

		/**
		 * Create a concrete command depending on the enum.
		 * 
		 * @param params a string that represents a parameter.
		 * @throws InstantiationException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 * @return a concrete command object.
		 */
		public ICommand createCommandObject(String params)
		    throws InstantiationException, IllegalAccessException,
		    IllegalArgumentException, InvocationTargetException,
		    NoSuchMethodException, SecurityException {
			return this.clazz.getDeclaredConstructor(String.class)
			    .newInstance(params);
		}

		/**
		 * Create a concrete command depending on the enum.
		 * 
		 * @param params array of parameter strings.
		 * @return a Command object that extends {@link ICommand}
		 * @throws InstantiationException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 */
		@SuppressWarnings("unused")
		public ICommand createCommandObject(String... params)
		    throws InstantiationException, IllegalAccessException,
		    IllegalArgumentException, InvocationTargetException,
		    NoSuchMethodException, SecurityException {
			return this.clazz.getDeclaredConstructor(String[].class).newInstance(
			    (Object) params);
		}
	}

	/**
	 * Contructor.
	 * 
	 * @param logfile the logfile object.
	 * @param database the database object.
	 */
	public GISCommandInvoker(GISDatabaseFile database, GISLogFile logfile) {
		this.commands = new ArrayDeque<>();
		this.setDatabase(database);
		this.setLogfile(logfile);
	}

	//
	// Methods
	//
	/**
	 * Store commands in the FIFO queue.
	 * 
	 * @param command
	 *           a string that represents a command to the system.
	 */
	public void enqueue(String command) {
		if (!command.startsWith(COMMENT_PREFIX)) {
			this.commands.add(command);
		}
	}

	/**
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public void invoke() throws InstantiationException, IllegalAccessException,
	    NoSuchMethodException, SecurityException, IllegalArgumentException,
	    InvocationTargetException {
		String current = this.commands.poll();
		String[] tokens = current.split("\\s", 2);
		ICommand command;
		if(tokens.length > 1) {
			command = Command.valueOf(tokens[0].toUpperCase())
				.createCommandObject(tokens[1]);
		}
		else {
			command = Command.valueOf(tokens[0].toUpperCase())
				.createCommandObject();
		}
		command.setDatabase(this.database);
		command.setLogfile(this.logfile);
		command.execute();
	}

	/**
	 * Run all the command in the queue.
	 */
	public void run() {
		int count = 0;
		for (String cmd : this.commands) {
			String title = "Command " + count++ + ":\t" + cmd;
			StringBuilder section = new StringBuilder();
			section.append("\n")
			       .append("\n")
			       .append(GIS.repeatText('=', title.length() + 3))
			       .append("\n")
			       .append(title)
			       .append("\n")
			       .append(GIS.repeatText('-', title.length() + 3))
			       .append("\n")
			;
			this.logfile.log(section.toString());
			try {
				this.invoke();
			}
			catch (Exception e) {
				System.err.println("Unable to invoke command:\t" + cmd);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Set the database file.
	 * 
	 * @param database
	 *          the database to set
	 */
	public void setDatabase(GISDatabaseFile database) {
		this.database = database;
	}

	/**
	 * Set the log file.
	 * 
	 * @param logfile
	 *          the logfile to set
	 */
	public void setLogfile(GISLogFile logfile) {
		this.logfile = logfile;
	}

	/**
	 * Enqueue a list of command strings.
	 * 
	 * @param cmds List of command strings.
	 */
	public void enqueue(List<String> cmds) {
		for (String cmd : cmds) {
			this.enqueue(cmd);
			if (cmd.startsWith(COMMANDS_EXIT)) {
				break;
			}
		}
	}
}
