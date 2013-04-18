/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 *
 */
public class GISLogFile extends AbstractGISFile {

	public GISLogFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename, AbstractGISFile.GIS_FILE_READWRITE);
  }
	public void log(String data) throws IOException {
		this.file.write(data.getBytes());
	}
	public void truncate() throws IOException {
		this.file.setLength(0);
  }
}