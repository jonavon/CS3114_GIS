package edu.vt.jowilcox.cs3114.p4.gis.command;

/**
 * Class WorldCommand
 */
public class WorldCommand extends AbstractCommand {
	private long yMax;
	private long yMin;
	private long xMin;
	private long xMax;

	//
	// Constructors
	//
	public WorldCommand(String args) {
		this(args.split("\\s"));
	}

	public WorldCommand(String... args) {
		this.xMin = this.DMStoSeconds(args[0]);
		this.xMax = this.DMStoSeconds(args[1]);
		this.yMin = this.DMStoSeconds(args[2]);
		this.yMax = this.DMStoSeconds(args[3]);
	}

	@Override
	public void execute() {
		this.database.configCoordIndex(this.xMin, this.xMax, this.yMin, this.yMax);
		this.logfile.log(this.toString());
		System.out.println("World command executed");
	}

	private long DMStoSeconds(String dms) {
		int length = dms.length() - 1;
		char direction = dms.charAt(length);

		int sign = ((direction == 'W') || (direction == 'S')) ? -1 : 1;
		long seconds = 0;
		seconds += (Long.parseLong(dms.substring(0, length - 4)) * 3600);
		seconds += (Long.parseLong(dms.substring(length - 4, length - 2)) * 60);
		seconds += Long.parseLong(dms.substring(length - 2, length));
		return seconds * sign;
	}

	public String toString() {
		String output = "\n";
		output += "WORLD DIMENSIONS:";
		output += "           " + this.yMax + "\n";
		output += this.xMin + "               " + this.xMax + "\n";
		output += "           " + this.yMin + "\n";
		return output;
	}
}
