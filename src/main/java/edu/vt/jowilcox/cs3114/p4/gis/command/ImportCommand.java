package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GISRecordsFile;

/**
 * Class ImportCommand
 */
public class ImportCommand extends AbstractCommand {

	//
	// Fields
	//
	/** Document to be imported */
	private GISRecordsFile document;

	//
	// Constructors
	//
	// TODO
	public ImportCommand(String args) {
		this(args.split("\\s"));
	}

	// TODO
	public ImportCommand(String... args) {
	}

	public ImportCommand(GISRecordsFile document) {
		this.document = document;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("Import command executed.");
		this.database.insert(this.document);
	}
}
