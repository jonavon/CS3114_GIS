package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class DebugCommand
 */
public class DebugCommand extends AbstractCommand {
	private DebugCommand.TYPE debug;

	//
	// Fields
	//
	enum TYPE {
		quad, hash, pool
	}

	//
	// Constructors
	//

	public DebugCommand(String args) {
		this(args.split("\\s"));
	}

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
