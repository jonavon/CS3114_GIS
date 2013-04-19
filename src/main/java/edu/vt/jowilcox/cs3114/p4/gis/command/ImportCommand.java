package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	public ImportCommand(String args) throws FileNotFoundException {
		this(args.split("\\s"));
	}

	// TODO
	public ImportCommand(String... args) throws FileNotFoundException {
			this(new GISRecordsFile(args[0]));
	}

	public ImportCommand(GISRecordsFile document) {
		this.document = document;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("Import command executed.");
		try {
			this.database.configNameIndex(1019);
	    this.database.insert(this.document);
	    this.logfile.log(this.database.getCoordIndex().print(true));
	    this.logfile.log(this.database.getNameIndex().toString());
    }
    catch (IOException e) {
			System.err.println("Unable to import document.");
	    e.printStackTrace();
    }
	}
}
