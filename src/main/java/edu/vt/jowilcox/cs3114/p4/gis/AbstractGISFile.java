package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public abstract class AbstractGISFile {

	static final String GIS_FILE_READONLY = "r";
	static final String GIS_FILE_READWRITE = "rw";
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
	 * 
	 * @return a string representing the next command.
	 * @throws IOException
	 */
	public String read() throws IOException {
		long current = this.file.getFilePointer();
		this.file.readLine();
		long eol = this.file.getFilePointer();
		if((eol-1) > current) {
			this.file.seek(current);
			byte[] b = new byte[(int) (eol - current)-1];
			this.file.read(b);
			this.file.seek(eol);
			String output = new String(b,"UTF-8");
			return output;
		}
		else {
			return null;
		}
	}

	/**
	 * Not to be confused with the File.exists() method. Implements the File.valid
	 * method.
	 * 
	 * @return boolean if file is open.
	 */
	public boolean exists() {
		try {
			return this.file.getFD().valid();
		}
		catch (IOException e) {
			return false;
		}
	}

}
