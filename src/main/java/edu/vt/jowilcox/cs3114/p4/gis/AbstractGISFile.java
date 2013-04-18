package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 *
 */
public abstract class AbstractGISFile {
	
	static final String GIS_FILE_READONLY = "r";
	static final String GIS_FILE_READWRITE= "rw";
	/** Internal file object. */
	protected RandomAccessFile file;

	/**
	 * Return the internal file object.
	 * 
	 * @return the file
	 */
	public RandomAccessFile getFile() {
		return file;
	}

	/**
	 * Read a line from this file.
	 * @return a string representing the next command.
	 */
	public String read() {
		try {
			return this.file.readLine();
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/** 
	 * Not to be confused with the File.exists() method. Implements the
	 * File.valid method.
	 * @return boolean if file is open.
	 */
	public boolean exists() { 
		try {
			return this.file.getFD().valid();
		} catch
		(IOException e) {
			return false;
		} 
	}

}
