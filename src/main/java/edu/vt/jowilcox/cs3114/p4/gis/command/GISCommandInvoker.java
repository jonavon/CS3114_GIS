package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISLogFile;

/**
 * Class GISCommandInvoker
 */
public class GISCommandInvoker {
	private Queue<String> commands;
	private GISDatabaseFile database;
	private GISLogFile logfile;
	private static final String COMMENT_PREFIX = ";";

	//
	// Fields
	//
	/**
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 * @formatter:off
	 */
	private enum Command {
		WORLD(WorldCommand.class)
		, IMPORT(ImportCommand.class)
		, WHAT_IS(WhatIsCommand.class)
		, WHAT_IS_AT(WhatIsCommand.class)
		, WHAT_IS_IN(WhatIsCommand.class)
		, DEBUG(DebugCommand.class)
		, QUIT(QuitCommand.class)
		;

		/*
		 * 
		 * @formatter:on
		 */
		/** Class object. */
		private Class<? extends ICommand> clazz;

		/**
		 * @param name
		 * @param clazz
		 */
		private Command(Class<? extends ICommand> clazz) {
			this.clazz = clazz;
		}

		@SuppressWarnings("unused")
    public ICommand createCommandObject()
		    throws InstantiationException, IllegalAccessException,
		    IllegalArgumentException, InvocationTargetException,
		    NoSuchMethodException, SecurityException {
			return this.clazz.getDeclaredConstructor()
			    .newInstance();
		}
		/**
		 * @param params
		 * @return
		 * @throws InstantiationException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 */
		public ICommand createCommandObject(String params)
		    throws InstantiationException, IllegalAccessException,
		    IllegalArgumentException, InvocationTargetException,
		    NoSuchMethodException, SecurityException {
			return this.clazz.getDeclaredConstructor(String.class)
			    .newInstance(params);
		}

		/**
		 * @param params
		 * @return
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
	 * @param logfile
	 * @param database
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
	 * @param command
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
		ICommand command = Command.valueOf(tokens[0].toUpperCase())
		    .createCommandObject(tokens[1]);
		command.setDatabase(this.database);
		command.setLogfile(this.logfile);
		command.execute();
	}

	/**
	 * Run all the command in the queue.
	 */
	public void run() {
		System.out.println(this.commands);
		for (String cmd : this.commands) {
			try {
				this.invoke();
			}
			catch (Exception e) {
				System.err.println("Unable to invoke command:\t" + cmd);
				// System.err.println(e.getMessage());
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

	public void enqueue(List<String> cmds) {
		for (String cmd : cmds) {
			this.enqueue(cmd);
		}
	}
}
