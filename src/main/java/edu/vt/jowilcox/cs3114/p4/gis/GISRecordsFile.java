package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * GIS record database.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISRecordsFile {
	/** Internal file object. */
	private RandomAccessFile file;

	/**
	 * Constructor.
	 * 
	 * @param filename path to the file.
	 * @throws FileNotFoundException
	 */
	public GISRecordsFile(String filename) throws FileNotFoundException {
		this.setFile(new RandomAccessFile(filename, "r"));
	}

	/**
	 * Return the internal file object.
	 * 
	 * @return the file
	 */
	public RandomAccessFile getFile() {
		return file;
	}

	/**
	 * Set the internal file object.
	 * 
	 * @param file
	 *          the file to set
	 */
	private void setFile(RandomAccessFile file) {
		this.file = file;
	}

	/**
	 * Read a line of data after offset.
	 * 
	 * @param offset
	 *          byte offset to begin reading
	 * @return String containing the data after offset.
	 * @throws IOException
	 *           thrown if offset is less than 0 or other IOException .
	 */
	public String select(long offset) throws IOException {
		// set file pointer to record offset
		this.file.seek(offset);
		// return the next line
		return this.file.readLine();
	}
}
