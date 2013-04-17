package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * GIS record database.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class GISRecordsFile extends AbstractGISFile {
	/**
	 * Constructor.
	 * 
	 * @param filename path to the file.
	 * @throws FileNotFoundException
	 */
	public GISRecordsFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename, AbstractGISFile.GIS_FILE_READONLY);
	}
}
