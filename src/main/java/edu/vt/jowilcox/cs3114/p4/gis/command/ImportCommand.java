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
	public ImportCommand(String args) throws FileNotFoundException {
		this(args.split("\\s"));
	}

	public ImportCommand(String... args) throws FileNotFoundException {
		this(new GISRecordsFile(args[0]));
	}

	public ImportCommand(GISRecordsFile document) {
		this.document = document;
	}

	@Override
	public void execute() {
		System.out.println("Import command executed.");
		try {
			// @formatter:off
			this.database.configNameIndex(1019);
	    this.database.insert(this.document);
	    this.logfile.log("\n      Number imported into name index: " + this.database.getNameImportCnt());
	    this.logfile.log("\nNumber imported into coordinate index: " + this.database.getCoordImportCnt());
	    this.logfile.log("\n          Total indexed in name index: " + this.database.getNameIndexSize());
	    this.logfile.log("\n    Total indexed in coordinate index: " + this.database.getCoordIndexSize());
	    this.logfile.log("\n               Longest probe sequence: " + this.database.getNameIndex().getLongestProbe() + "\n");
			// @formatter:on
		}
		catch (IOException e) {
			System.err.println("Unable to import document." + this.args);
			e.printStackTrace();
		}
	}
}
