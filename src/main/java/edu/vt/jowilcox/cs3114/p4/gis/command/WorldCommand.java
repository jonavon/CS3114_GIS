package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class WorldCommand
 */
public class WorldCommand extends AbstractCommand {
	//
	// Constructors
	//
	// TODO
	public WorldCommand(String args) {
		this(args.split("\\s"));
	}

	// TODO
	public WorldCommand(String... args) {
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("World command executed.");
	}
}
