package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class DebugCommand. Log the contents of the specified index structure in a
 * fashion that makes the internal structure and contents of the index clear. It
 * is not necessary to be overly verbose here, but it would be useful to include
 * information like key values and file offsets where appropriate.
 */
/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 *
 */
public class DebugCommand extends AbstractCommand {
	private DebugCommand.TYPE debug;

	/**
	 * Type of command parameter.
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 */
	enum TYPE {
		quad, hash, pool
	}

	//
	// Constructors
	//

	/**
	 * Constructor.
	 * @param args arguments for the command.
	 */
	public DebugCommand(String args) {
		this(args.split("\\s"));
	}

	/**
	 * Constructor.
	 * @param args arguments for the command.
	 */
	public DebugCommand(String... args) {
		this.debug = DebugCommand.TYPE.valueOf(args[0]);
	}

	@Override
	public void execute() {
		switch (this.debug) {
			case quad:
				this.logfile.log(this.database.getCoordIndex().print(true));
			break;
			case hash:
				this.logfile.log(this.database.getNameIndex().debug());
			break;
			case pool:
				this.logfile.log(this.database.getBufferPool().debug());
			break;
			default:
				this.logfile.log("Invalid command");
			break;
		}
		System.out.println("Debug command run.");
	}
}
