package edu.vt.jowilcox.cs3114.p4.gis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * GIS record database.
 * 
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public class CommandsFile extends AbstractGISFile {

	/**
	 * Constructor.
	 * 
	 * @param filename
	 *          file path.
	 * @throws FileNotFoundException
	 *           if the file does not exist.
	 */
	public CommandsFile(String filename) throws FileNotFoundException {
		this.file = new RandomAccessFile(filename, GIS_FILE_READONLY);
	}

	/**
	 * Read all the commands.
	 * 
	 * @return an array containing all the commands.
	 * @throws IOException
	 */
	public List<String> readAll() throws IOException {
		ArrayList<String> list = new ArrayList<>();
		long eof = this.file.length();
		while (this.file.getFilePointer() != eof) {
			list.add(this.read());
		}
		this.file.seek(0);
		return list;
	}
}
