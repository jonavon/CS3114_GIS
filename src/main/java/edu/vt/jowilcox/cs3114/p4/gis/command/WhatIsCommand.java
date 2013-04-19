package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class WhatIsCommand
 */
public class WhatIsCommand extends AbstractCommand {
	//
	// Constructors
	//
	// TODO
	public WhatIsCommand(String args) {
		this(args.split("\\s"));
	}

	// TODO
	public WhatIsCommand(String... args) {
	}

	public WhatIsCommand() {
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("What Is command executed.");
	}
}
