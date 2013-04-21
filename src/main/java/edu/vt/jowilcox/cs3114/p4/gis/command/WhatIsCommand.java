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
	}

	@Override
	public void execute() {
		Index item = this.database.getNameIndex().get(this.name + ":" + this.state);
		StringBuilder o = new StringBuilder();
		if (item == null) {
			o.append("No results");
		}
		else {
			try {
				int count = item.getOffsets().size();
				o.append("Found ").append(count).append(" item(s).\n");
				for (Long offset : item.getOffsets()) {
					GISRecord record = this.database.select(offset);
					// @formatter:off
					o.append(String.format("%8d", offset))
					 .append(":\t")
					 .append(record.getCounty())
					 .append(" ")
					 .append(GIS.toDMS(record.getLatitude(), false))
					 .append(" ")
					 .append(GIS.toDMS(record.getLongitude(), true))
					 .append("\n");
					// @formatter:on
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("What is command run.");
		}
		this.logfile.log(o.toString());
	}
}
