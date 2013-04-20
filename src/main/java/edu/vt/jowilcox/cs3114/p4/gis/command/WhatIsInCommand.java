package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.IOException;
import java.util.Vector;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.CoordIndex;
import edu.vt.jowilcox.cs3114.p4.gis.GISRecord;

/**
 * Class WhatIsCommand
 */
public class WhatIsInCommand extends AbstractCommand {
	//
	// Constructors
	//
	// TODO
	private String latitude;
	private String longitude;
	private String name;
	private String state;

	public WhatIsInCommand(String args) {
		Pattern what_is_at = Pattern.compile("^\\d{6,7}(N|S)\\t?\\d{6,7}(E|W)$"); // what_is_at  364201N  1063259W
		String[] input = args.split("\\t");
		if (what_is_at.matcher(args).matches()) {
			this.latitude = input[0];
			this.longitude = input[1];
		}
		Pattern what_is = Pattern.compile("^(\\d+ )?([a-zA-Z]+ ?)+(\\t[A-Z]{2}){1}$"); // what_is 11476 Water Well NM
		if(what_is.matcher(args).matches()) {
			 this.name = input[0];
			 this.state = input[1];
		}
		
		// "^\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$" // what_is_in 364201S 1063259E 60 60
		// "^\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$" what_is_in 364201S 1063259E 60 60
		// this.latitude;
		// this.longitude;
		// this.hheight;
		// this.hwidth;
		// "^-c\t\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$"
		// this.count
		// "^-l\t\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$"
		// this.isLong
	}

	@Override
	public void execute() {
		if (this.latitude != null) {
			// 364201N = 132121
			// 1063259W = -383579
			long lat = GIS.DMStoTotalSeconds(this.latitude);
			long lon = GIS.DMStoTotalSeconds(this.longitude);
			long h = Long.valueOf(this.hheight);
			long w = Long.valueOf(this.hwidth);

			long xLo = lon - w;
			long xHi = lon + w;
			long yHi = lat + h;
			long yLo = lat - h;
			Vector<CoordIndex> results = this.database.getCoordIndex().find(xLo, xHi,
			    yLo, yHi);
			int count = 0;
			StringBuilder output = new StringBuilder();
			for (CoordIndex result : results) {

				count = count + result.getOffsets().size();
				for (Long offset : result.getOffsets()) {
					try {
						GISRecord record = this.database.select(offset);
						output = new StringBuilder(); // reset
						if (this.flag == FLAG.VERBOSE) {
							output.append(record.print());
						}
						else if (this.flag == null) {
							//@formatter:off
							output.append(String.format("%8d", offset))
							      .append(":\t")
							      .append(record.getName())
							      .append(" ")
							      .append(record.getState())
							      .append(" ")
							      .append(GIS.toDMS(record.getLatitude(), false))
							      .append(" ")
							      .append(GIS.toDMS(record.getLongitude(), true))
							      .append("\n");
						//@formatter:on
						}
					}
					catch (IOException e) {
						System.err.println("System error when finding offset:\t" + offset);
						e.printStackTrace();
					}
				}
			}
			if (this.flag == FLAG.COUNT) {
				output.append("Found ").append(count).append(" items.\n");
			}
			this.logfile.log(output.toString());
		}
		System.out.println("What is in command run.");
	}
}
