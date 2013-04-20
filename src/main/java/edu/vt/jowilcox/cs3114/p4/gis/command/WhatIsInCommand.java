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
	private String latitude;
	private String longitude;
	private String hheight;
	private String hwidth;
	private FLAG flag;

	enum FLAG {
		COUNT, VERBOSE;
	}

	public WhatIsInCommand(String args) {
		String[] input = args.split("\\t");
		int f = (args.charAt(0) == '-') ? 1 : 0;
		if (f == 1) {
			this.flag = (args.charAt(1) == 'c') ? FLAG.COUNT : FLAG.VERBOSE;
		}
		this.latitude = input[0 + f];
		this.longitude = input[1 + f];
		this.hheight = input[2 + f];
		this.hwidth = input[3 + f];
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
			StringBuilder o = new StringBuilder();
			for (CoordIndex result : results) {

				count = count + result.getOffsets().size();
				for (Long offset : result.getOffsets()) {
					try {
						GISRecord record = this.database.select(offset);
						if (this.flag == FLAG.VERBOSE) {
							output.append(record.print());
						}
						else if (this.flag == null) {
							//@formatter:off
							o.append(String.format("%8d", offset))
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
			output.append("Found ").append(count).append(" item(s).\n").append(o);
			this.logfile.log(output.toString());
		}
		System.out.println("What is in command run.");
	}
}
