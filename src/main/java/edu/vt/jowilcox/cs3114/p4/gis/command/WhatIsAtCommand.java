package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.IOException;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.CoordIndex;
import edu.vt.jowilcox.cs3114.p4.gis.GISRecord;

/**
 * Class WhatIsCommand
 */
public class WhatIsAtCommand extends AbstractCommand {
	//
	// Constructors
	//
	// TODO
	private String latitude;
	private String longitude;

	public WhatIsAtCommand(String args) {
		String[] input = args.split("\\t");
		this.latitude = input[0];
		this.longitude = input[1];
	}

	@Override
	public void execute() {
			long lat = GIS.DMStoTotalSeconds(this.latitude);
			long lon = GIS.DMStoTotalSeconds(this.longitude);
			SearchCoord search = new SearchCoord(lon, lat);
			CoordIndex results = this.database.getCoordIndex().find(search);
			for (Long offset : results.getOffsets()) {
				try {
					GISRecord record = this.database.select(offset);
					StringBuilder output = new StringBuilder();
					output.append(String.format("%8d", offset))
					      .append(":\t")
					      .append(record.getName())
					      .append(" ")
					      .append(record.getCounty())
					      .append(" ")
					      .append(record.getState())
					      .append("\n")
					;
					this.logfile.log(output.toString());
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("What is at command run.");
	}

	class SearchCoord extends CoordIndex {
		public SearchCoord(long xcoord, long ycoord) {
			this(WhatIsAtCommand.this.database, -1, "@@SEARCH@@", xcoord, ycoord);
		}

		private SearchCoord(GISDatabaseFile gisDatabaseFile, long offset,
		    String name, long xcoord, long ycoord) {
			gisDatabaseFile.super(offset, name, xcoord, ycoord);
			// TODO Auto-generated constructor stub
		}

	}
}
