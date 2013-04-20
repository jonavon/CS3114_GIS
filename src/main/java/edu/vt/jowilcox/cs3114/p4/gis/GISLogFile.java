/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Represents the logfile.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISLogFile extends AbstractGISFile {

	/**
	 * Constructor.
	 * 
	 * @param filename
	 *          absolute path of log file.
	 * @throws FileNotFoundException
	 */
	public GISLogFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename,
		    AbstractGISFile.GIS_FILE_READWRITE);
	}

	/**
	 * Write a string as is to the log file.
	 * 
	 * @param data
	 * @throws IOException
	 */
	public void log(String data) {
		try {
			this.file.write(data.getBytes());
		}
		catch (IOException e) {
			System.err.println("Unable to log data:\n\t'" + data + "'");
		}
	}

	/**
	 * Remove the contents of the file.
	 * 
	 * @throws IOException
	 */
	public void truncate() throws IOException {
		this.file.setLength(0);
	}
}