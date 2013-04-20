package edu.vt.jowilcox.cs3114.p4.gis;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * @author jonavon
 */
@SuppressWarnings("unused")
public class CommandSteps {
	private GISDatabaseFile db;
	private CommandsFile cmd;
	private GISLogFile log;
	private static final String DB_FILE_PATH = "/tmp/db.txt";
	private static final String CMD_FILE_PATH = "/tmp/cmd.txt";
	private static final String LOG_FILE_PATH = "/tmp/log.txt";
	private static final String IMPORT_FILE_PATH = "/tmp/import.txt";

	@Given("^a database file$")
	public void a_database_file() throws Throwable {
		this.db = new GISDatabaseFile(DB_FILE_PATH);
		System.out.println(this.db.getFile().getFD().toString());
	}

	@Given("^the database file exists$")
	public void the_database_file_exists() throws Throwable {
		assertTrue(this.db.exists());
	}

	@When("^the system is run$")
	public void the_system_is_run() throws Throwable {
		GIS.main(new String[] { DB_FILE_PATH, CMD_FILE_PATH, LOG_FILE_PATH });
		throw new PendingException();
	}

	@Then("^the existing (database|log) file should be truncated.$")
	public void the_existing_file_should_be_truncated(String file)
	    throws Throwable {
		if (file.equals("log")) {
			assertTrue(this.log.getFile().length() == 0);
		}
		else {
			assertTrue(this.db.getFile().length() == 0);
		}
	}

	@Given("^a command file$")
	public void a_command_file() throws Throwable {
		this.cmd = new CommandsFile(CMD_FILE_PATH);
	}

	@Given("^the command file does not exist$")
	public void the_command_file_does_not_exists() throws Throwable {
		// Express the Regexp above with the code you wish you had
		throw new PendingException();
		// assertFalse(this.cmd.exists());
	}

	@Then("^an error message should be written to the console:$")
	public void an_error_message_should_be_written_to_the_console(String msg)
	    throws Throwable {
		// Express the Regexp above with the code you wish you had
		throw new PendingException();
	}

	@Given("^a log file$")
	public void a_log_file() throws Throwable {
		this.log = new GISLogFile(LOG_FILE_PATH);
	}

	@Given("^the log file exist$")
	public void the_log_file_exists() throws Throwable {
		this.log.exists();
	}

	@Given("^I copy import file from \"([^\"]*)\"$")
	public void I_copy_import_file_from(String file) throws Throwable {
		this.copyFile(file, IMPORT_FILE_PATH);
	}

	@Given("^I copy command file from \"([^\"]*)\"$")
	public void I_copy_command_file_from(String file) throws Throwable {
		this.copyFile(file, CMD_FILE_PATH);
	}

	private void copyFile(String file, String path) throws IOException {
		String ffn = file.substring(file.lastIndexOf(File.separatorChar) + 1);
		String fdir = file.substring(0, file.lastIndexOf(File.separatorChar) + 1);
		File fobj = this.getAbsolutePathFile(fdir, ffn);

		String pfn = path.substring(path.lastIndexOf(File.separatorChar) + 1);
		String pdir = path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
		File pobj = this.getAbsolutePathFile(pdir, pfn);
		RandomAccessFile f = new RandomAccessFile(fobj, "r");
		RandomAccessFile p = new RandomAccessFile(pobj, "rw");
		p.setLength(0);
		while (f.getFilePointer() < f.length()) {
			p.write(f.read());
		}
		p.close();
		f.close();
	}

	/**
	 * Converts a path relative to a given directory to an absolute path.
	 */
	private File getAbsolutePathFile(String dir, String fn) {
		File f = new File(fn);
		return f.isAbsolute() ? f : new File(dir, fn);
	}
}
