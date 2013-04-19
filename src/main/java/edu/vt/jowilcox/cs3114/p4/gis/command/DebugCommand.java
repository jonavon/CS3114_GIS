package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class DebugCommand
 */
public class DebugCommand extends AbstractCommand {

	//
	// Fields
	//

	//
	// Constructors
	//

	// TODO
	public DebugCommand(String args) {
		this(args.split("\\s"));
	}

	// TODO
	public DebugCommand(String... args) {
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("Debug command executed.");
	}
}
