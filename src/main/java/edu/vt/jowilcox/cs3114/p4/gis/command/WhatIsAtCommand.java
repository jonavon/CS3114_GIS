package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.IOException;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.CoordIndex;
import edu.vt.jowilcox.cs3114.p4.gis.GISRecord;

/**
 * Class WhatIsCommand.
 */
public class WhatIsAtCommand extends AbstractCommand {
	//
	// Constructors
	//
	// TODO
	private String latitude;
	private String longitude;

	/**
	 * Constructor.
	 * 
	 * @param args
	 *          arguments for the command.
	 */
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
		int count = 0;
		StringBuilder output = new StringBuilder();
		StringBuilder o = new StringBuilder();
		if (results != null) {
			count = results.getOffsets().size();
			for (Long offset : results.getOffsets()) {
				try {
					GISRecord record = this.database.select(offset);
					o.append(String.format("%8d", offset)).append(":\t")
					    .append(record.getName()).append(" ").append(record.getCounty())
					    .append(" ").append(record.getState()).append("\n");
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		output.append("Found ").append(count).append(" item(s).\n").append(o);
		this.logfile.log(output.toString());
		System.out.println("What is at command run.");
	}

	/**
	 * Support class that is used to search for CoordIndex objects in the index.
	 * 
	 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
	 */
	class SearchCoord extends CoordIndex {
		
		/**
		 * Coordinate.
		 * 
		 * @param xcoord x coordinate.
		 * @param ycoord y coordinate.
		 */
		public SearchCoord(long xcoord, long ycoord) {
			this(WhatIsAtCommand.this.database, -1, "@@SEARCH@@", xcoord, ycoord);
		}

		/**
		 * @param gisDatabaseFile the database.
		 * @param offset file offset.
		 * @param name Name.
		 * @param xcoord x coordinate.
		 * @param ycoord y coordinate.
		 */
		private SearchCoord(GISDatabaseFile gisDatabaseFile, long offset,
		    String name, long xcoord, long ycoord) {
			gisDatabaseFile.super(offset, name, xcoord, ycoord);
			// TODO Auto-generated constructor stub
		}
	}
}
