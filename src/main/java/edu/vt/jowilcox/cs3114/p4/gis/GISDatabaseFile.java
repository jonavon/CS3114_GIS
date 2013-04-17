package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * GIS record database.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISDatabaseFile extends AbstractGISFile {
	/**
	 * Constructor.
	 * 
	 * @param filename path to the file.
	 * @throws FileNotFoundException
	 */
	public GISDatabaseFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename, AbstractGISFile.GIS_FILE_READWRITE);
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
		return this.read();
	}

	public void insert(GISRecordsFile document) {
	  // TODO Auto-generated method stub
	  
  }
}
