package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.IOException;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.Index;
import edu.vt.jowilcox.cs3114.p4.gis.GISRecord;

/**
 * Class WhatIsCommand
 */
public class WhatIsCommand extends AbstractCommand {
	//
	// Constructors
	//
	private String name;
	private String state;

	public WhatIsCommand(String args) {
		String[] input = args.split("\\t");
		this.name = input[0];
		this.state = input[1];

		// "^\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$" // what_is_in 364201S
		// 1063259E 60 60
		// "^\d{6,7}(N|S)\t?\d{6,7}(E|W)\t\d{1,}\t\d{1,}$" what_is_in 364201S
		// 1063259E 60 60
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
		Index item = this.database.getNameIndex().get(this.name + ":" + this.state);
		StringBuilder o = new StringBuilder();
		if (item == null) {
			o.append("No result");
		}
		else {
			GISRecord record;
			try {
				record = this.database.select(item.getOffset());
				// @formatter:off
				o.append(String.format("%8d", item.getOffset()))
				 .append(":\t")
				 .append(record.getCounty())
				 .append(" ")
				 .append(GIS.toDMS(record.getLatitude(), false))
				 .append(" ")
				 .append(GIS.toDMS(record.getLongitude(), true))
				 .append("\n");
				// @formatter:on
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("What is command run.");
		}
		this.logfile.log(o.toString());
	}
}
