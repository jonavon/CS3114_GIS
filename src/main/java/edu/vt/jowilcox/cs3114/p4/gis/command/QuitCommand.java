package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class QuitCommand
 */
public class QuitCommand extends AbstractCommand {

	//
	// Constructors
	//
	// TODO
	public QuitCommand(){}
	// TODO
	public QuitCommand(String args) {
		this(args.split("\\s"));
	}
	// TODO
	public QuitCommand(String... args) {
	}

	@Override
	public void execute() {
		System.out.println("Quit command given! Exiting.");
		System.exit(0);
	}
}
