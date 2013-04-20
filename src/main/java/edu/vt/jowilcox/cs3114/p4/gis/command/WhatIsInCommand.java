package edu.vt.jowilcox.cs3114.p4.gis.command;

import java.io.IOException;
import java.util.regex.Pattern;

import edu.vt.jowilcox.cs3114.p4.gis.GIS;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.CoordIndex;
import edu.vt.jowilcox.cs3114.p4.gis.GISDatabaseFile.Index;
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
		}
		else if(this.name != null) {
			Index item = this.database.getNameIndex().get(this.name + ":" + this.state);
      StringBuilder output = new StringBuilder();
      if(item == null) {
      	output.append("No result");
      }
      else {
			try {
	      GISRecord record = this.database.select(item.getOffset());
	      output.append(String.format("%8d",item.getOffset()))
	            .append(":\t")
	            .append(record.getCounty())
	            .append(" ")
	            .append(GIS.toDMS(record.getLatitude(), false))
	            .append(" ")
	            .append(GIS.toDMS(record.getLongitude(), true))
	            .append("\n")
	      ;
      }
      catch (IOException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
			finally {
				try {
	        this.logfile.log(output.toString());
        }
        catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
			}
      }
		}
		else {
			System.out.println("What is command run.");
		}
	}

	class SearchCoord extends CoordIndex {
		public SearchCoord(long xcoord, long ycoord) {
			this(WhatIsInCommand.this.database, -1, "@@SEARCH@@", xcoord, ycoord);
		}

		private SearchCoord(GISDatabaseFile gisDatabaseFile, long offset,
		    String name, long xcoord, long ycoord) {
			gisDatabaseFile.super(offset, name, xcoord, ycoord);
			// TODO Auto-generated constructor stub
		}

	}
}
