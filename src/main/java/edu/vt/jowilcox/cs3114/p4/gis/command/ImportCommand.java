package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.vt.jowilcox.cs3114.p4.gis.GISRecordsFile;

/**
 * Class ImportCommand. Add all the GIS records in the specified file to the
 * database file. This means that the records will be appended to the existing
 * database file, and that those records will be indexed in the manner described
 * earlier. When the import is completed, log the number of entries added to
 * each index, and the longest probe sequence that was needed when inserting to
 * the hash table.
 */
public class ImportCommand extends AbstractCommand {

	//
	// Fields
	//
	/** Document to be imported. */
	private GISRecordsFile document;

	//
	// Constructors
	//
	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
	public ImportCommand(String args) throws FileNotFoundException {
		this(args.split("\\s"));
	}

	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
	public ImportCommand(String... args) throws FileNotFoundException {
		this(new GISRecordsFile(args[0]));
	}

	/**
	 * Constructor.
	 * 
	 * @param document
	 *          document to be imported.
	 */
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
