package edu.vt.jowilcox.cs3114.p4.gis.command;


/**
 * Class QuitCommand
 */
public class QuitCommand implements ICommand {

	//
	// Constructors
	//
  // TODO
	public QuitCommand(String args) { }
  // TODO
	public QuitCommand(String... args) { }

	@Override
  public void execute() {
		System.exit(0);
  }
}
