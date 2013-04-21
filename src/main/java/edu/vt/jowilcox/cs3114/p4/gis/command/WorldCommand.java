package edu.vt.jowilcox.cs3114.p4.gis.command;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;

/**
 * Class WorldCommand. This will be the first command in the file, and will
 * occur once. It specifies the boundaries of the coordinate space to be
 * modeled. The four parameters will be longitude and latitudes expressed in DMS
 * format, representing the vertical and horizontal boundaries of the coordinate
 * space. It is possible that the GIS record file will contain records for
 * features that lie outside the specified coordinate space. Such records should
 * be ignored; i.e., they will not be indexed.
 */
public class WorldCommand extends AbstractCommand {
	private long yMax;
	private long yMin;
	private long xMin;
	private long xMax;

	//
	// Constructors
	//
	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
	public WorldCommand(String args) {
		this(args.split("\\s"));
	}

	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
	public WorldCommand(String... args) {
		this.xMin = GIS.DMStoTotalSeconds(args[0]);
		this.xMax = GIS.DMStoTotalSeconds(args[1]);
		this.yMin = GIS.DMStoTotalSeconds(args[2]);
		this.yMax = GIS.DMStoTotalSeconds(args[3]);
	}

	@Override
	public void execute() {
		this.database.configCoordIndex(this.xMin, this.xMax, this.yMin, this.yMax);
		this.logfile.log(this.toString());
		System.out.println("World command executed");
	}


	@Override
	public String toString() {
		String output = "\n";
		output += "WORLD DIMENSIONS:\n";
		output += "           " + this.yMax + "\n";
		output += this.xMin + "               " + this.xMax + "\n";
		output += "           " + this.yMin + "\n";
		return output;
	}
}
