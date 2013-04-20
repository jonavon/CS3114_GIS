package edu.vt.jowilcox.cs3114.p4.gis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import edu.vt.jowilcox.cs3114.p4.USStateAbbreviation;

/**
 * Class GISRecord Geographic Information System Record; Contains geographic
 * data. Each field maps to a field in the record.
 */
public class GISRecord {

	//
	// Fields
	//

	/**
	 * Non negative integer. Unique identifier for this geographic record.
	 */
	private int fid;
	/**
	 * Standard name of this record.
	 */
	private String name;
	/**
	 * Descriptive class of this record
	 */
	private String classifier;
	/**
	 * Two Characters that represent the US postal code abbreviation.
	 */
	private USStateAbbreviation state;
	/**
	 * Record's latitude in decimal format or unknown.
	 */
	private Double latitude;

	private String county;
	private String countyNumeric;
	/**
	 * Record's longitude in decimal format or unknown.
	 */
	private Double longitude;
	/**
	 * Latitude of the record's source in decimal format.
	 */
	private Double slatitude;
	/**
	 * Longitude of record's source in decimal format.
	 */
	private Double slongitude;
	/**
	 * Record's elevation in meters
	 */
	private Integer elevation;
	/**
	 * Name of USGS topographic map including record.
	 */
	private String mapname;
	/**
	 * Date record was initially committed to the database, optional.
	 */
	private Calendar created;
	/**
	 * Date record was last updated, optional.
	 */
	private Calendar edited;
	private SimpleDateFormat sdf;

	//
	// Constructors
	//
	/**
	 * Constructor.
	 */
	public GISRecord() {
	}

	public GISRecord(String fields) {
		this(fields.split("\\|"));
	}

	public GISRecord(String... fields) {
		// @formatter:off
		// 0 FEATURE_ID|1 FEATURE_NAME|2 FEATURE_CLASS|3 STATE_ALPHA|4 STATE_NUMERIC|5 COUNTY_NAME|6 COUNTY_NUMERIC|7 PRIMARY_LAT_DMS|8 PRIM_LONG_DMS|9 PRIM_LAT_DEC
		// | 10 PRIM_LONG_DEC|11 SOURCE_LAT_DMS|12 SOURCE_LONG_DMS|13 SOURCE_LAT_DEC|14 SOURCE_LONG_DEC|15 ELEV_IN_M|16 ELEV_IN_FT|17 MAP_NAME|18 DATE_CREATED|19 DATE_EDITED
		
		this.fid = Integer.valueOf(fields[0]);
		this.name = (fields[1].isEmpty()) ? null : fields[1];
		this.classifier = (fields[2].isEmpty()) ? null : fields[2];
		this.state = (fields[3].isEmpty()) ? null : USStateAbbreviation.valueOf(fields[3]);
		this.county = (fields[5].isEmpty())? null : fields[5];
		this.countyNumeric = (fields[6].isEmpty())? null : fields[6];
		this.latitude = (fields[9].isEmpty()) ? null : Double.valueOf(fields[9]);
		this.longitude = (fields[10].isEmpty()) ? null : Double.valueOf(fields[10]);
		this.slatitude = (fields[13].isEmpty()) ? null : Double.valueOf(fields[13]);
		this.slongitude = (fields[14].isEmpty()) ? null : Double.valueOf(fields[14]);
		this.elevation = (fields[15].isEmpty()) ? null : Integer.valueOf(fields[15]);
		this.mapname = (fields[17].isEmpty()) ? null : fields[17];

		this.sdf = new SimpleDateFormat("MM/dd/yyyy");
		this.created = (fields[18].isEmpty()) ? null : Calendar.getInstance();
		if((fields.length) > 19) {
			this.edited = (fields[19].isEmpty()) ? null : Calendar.getInstance();
		}
		try {
			if(this.created != null) { 
				this.created.setTime(sdf.parse(fields[18]));
			}
			if(this.edited != null) { 
				this.edited.setTime(sdf.parse(fields[fields.length - 1]));
			}
		}
		catch (ParseException e) {
			System.err.println(fields[1]);
			e.printStackTrace();
		}
	}
	// @formatter:on

	/**
	 * Set the value of fid Non negative integer. Unique identifier for this
	 * geographic record.
	 * 
	 * @param fid
	 *          the new value of fid
	 */
	public void setFid(int fid) {
		this.fid = fid;
	}

	/**
	 * Get the value of fid Non negative integer. Unique identifier for this
	 * geographic record.
	 * 
	 * @return the value of fid
	 */
	public int getFid() {
		return this.fid;
	}

	/**
	 * Set the value of name Standard name of this record.
	 * 
	 * @param name
	 *          the new value of name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the value of name Standard name of this record.
	 * 
	 * @return the value of name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the value of classifier Descriptive class of this record
	 * 
	 * @param clazz
	 *          the new value of classifier
	 */
	public void setClassifier(String clazz) {
		this.classifier = clazz;
	}

	/**
	 * Get the value of classifier Descriptive class of this record
	 * 
	 * @return the value of classifier
	 */
	public String getClassifier() {
		return this.classifier;
	}

	/**
	 * Set the value of state Two Characters that represent the US postal code
	 * abbreviation.
	 * 
	 * @param abbrev
	 *          the new value of state
	 */
	public void setState(USStateAbbreviation abbrev) {
		this.state = abbrev;
	}

	/**
	 * Get the value of state Two Characters that represent the US postal code
	 * abbreviation.
	 * 
	 * @return the value of state
	 */
	public USStateAbbreviation getState() {
		return this.state;
	}

	/**
	 * Get the value of stateCode Non negative integer that represents the numeric
	 * code for the state.
	 * 
	 * @return the value of stateCode
	 */
	public int getStateCode() {
		return this.state.getNumeric();
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county
	 *          the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @return the countyNumeric
	 */
	public String getCountyNumeric() {
		return countyNumeric;
	}

	/**
	 * @param countyNumeric
	 *          the countyNumeric to set
	 */
	public void setCountyNumeric(String countyNumeric) {
		this.countyNumeric = countyNumeric;
	}

	/**
	 * Set the value of latitude Record's latitude in decimal format or unknown.
	 * 
	 * @param latitude
	 *          the new value of latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Get the value of latitude Record's latitude in decimal format or unknown.
	 * 
	 * @return the value of latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * Set the value of longitude Record's longitude in decimal format or unknown.
	 * 
	 * @param longitude
	 *          the new value of longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Get the value of longitude Record's longitude in decimal format or unknown.
	 * 
	 * @return the value of longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * Set the value of slatitude Latitude of the record's source in decimal
	 * format.
	 * 
	 * @param slatitude
	 *          the new value of slatitude
	 */
	public void setSlatitude(double slatitude) {
		this.slatitude = slatitude;
	}

	/**
	 * Get the value of slatitude Latitude of the record's source in decimal
	 * format.
	 * 
	 * @return the value of slatitude
	 */
	public Double getSlatitude() {
		return this.slatitude;
	}

	/**
	 * Set the value of slongitude Longitude of record's source in decimal format.
	 * 
	 * @param slongitude
	 *          the new value of slongitude
	 */
	public void setSlongitude(double slongitude) {
		this.slongitude = slongitude;
	}

	/**
	 * Get the value of slongitude Longitude of record's source in decimal format.
	 * 
	 * @return the value of slongitude
	 */
	public Double getSlongitude() {
		return this.slongitude;
	}

	/**
	 * Set the value of elevation Record's elevation in meters
	 * 
	 * @param elevation
	 *          the new value of elevation
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	/**
	 * Get the value of elevation Record's elevation in meters
	 * 
	 * @return the value of elevation
	 */
	public Integer getElevation() {
		return this.elevation;
	}

	/**
	 * Set the value of mapname Name of USGS topographic map including record.
	 * 
	 * @param mapname
	 *          the new value of mapname
	 */
	public void setMapname(String mapname) {
		this.mapname = mapname;
	}

	/**
	 * Get the value of mapname Name of USGS topographic map including record.
	 * 
	 * @return the value of mapname
	 */
	public String getMapname() {
		return this.mapname;
	}

	/**
	 * Set the value of created Date record was initially committed to the
	 * database, optional.
	 * 
	 * @param created
	 *          the new value of created
	 */
	public void setCreated(Calendar created) {
		this.created = created;
	}

	/**
	 * Get the value of created Date record was initially committed to the
	 * database, optional.
	 * 
	 * @return the value of created
	 */
	public Calendar getCreated() {
		return this.created;
	}

	/**
	 * Set the value of edited Date record was last updated, optional.
	 * 
	 * @param edited
	 *          the new value of edited
	 */
	public void setEdited(Calendar edited) {
		this.edited = edited;
	}

	/**
	 * Get the value of edited Date record was last updated, optional.
	 * 
	 * @return the value of edited
	 */
	public Calendar getEdited() {
		return this.edited;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// @formatter:off
		// 0 FEATURE_ID|1 FEATURE_NAME|2 FEATURE_CLASS|3 STATE_ALPHA|4 STATE_NUMERIC|5 COUNTY_NAME|6 COUNTY_NUMERIC|7 PRIMARY_LAT_DMS|8 PRIM_LONG_DMS|9 PRIM_LAT_DEC
		// | 10 PRIM_LONG_DEC|11 SOURCE_LAT_DMS|12 SOURCE_LONG_DMS|13 SOURCE_LAT_DEC|14 SOURCE_LONG_DEC|15 ELEV_IN_M|16 ELEV_IN_FT|17 MAP_NAME|18 DATE_CREATED|19 DATE_EDITED
		String[] data;
		data = new String[20];
		data[0] = String.valueOf(this.getFid());
		data[1] = this.getName();
		data[2] = this.getClassifier();
		data[3] = this.getState().toString();
		data[4] = String.valueOf(this.getStateCode());
		data[5] = this.getCounty();
		data[6] = this.getCountyNumeric();
		data[7] = GIS.toDMS(this.getLatitude() , false);
		data[8] = GIS.toDMS(this.getLongitude(), true);
		data[9] = String.valueOf(this.getLatitude());
		data[10] = String.valueOf(this.getLongitude());
		data[11] = (this.getSlatitude() == null)? null : GIS.toDMS(this.getSlatitude() , false);
		data[12] = (this.getSlongitude() == null)? null : GIS.toDMS(this.getSlongitude() , true);
		data[13] = (this.getSlatitude() == null)? null : String.valueOf(this.getSlatitude());
		data[14] = (this.getSlongitude() == null)? null : String.valueOf(this.getSlongitude());
		data[15] = (this.getElevation() == null)? null : String.valueOf(this.getElevation());
		data[16] = (this.getElevation() == null)? null : String.valueOf(Math.round(this.getElevation() * 3.28084)); // Convert to feet long.
		data[17] = this.getMapname();
		data[18] = (this.getCreated() == null)? null : this.sdf.format(this.getCreated().getTime());
		data[19] = (this.getEdited() == null)? null : this.sdf.format(this.getEdited().getTime());
		return GIS.implode("|", data);
	}

	public String print() {
		StringBuilder o = new StringBuilder();
		o.append("\n")
		 .append("     FEATURE ID:\t").append(String.valueOf(this.getFid())).append("\n")
		 .append("   FEATURE NAME:\t").append(this.getName()).append("\n")
		 .append("  FEATURE CLASS:\t").append(this.getClassifier()).append("\n")
		 .append("    STATE ALPHA:\t").append(this.getState().toString()).append("\n")
		// .append("  STATE NUMERIC:\t").append(String.valueOf(this.getStateCode())).append("\n")
		 .append("    COUNTY NAME:\t").append(this.getCounty()).append("\n")
		// .append(" COUNTY NUMERIC:\t").append(this.getCountyNumeric()).append("\n")
		 .append("PRIMARY LAT DMS:\t").append(GIS.toDMS(this.getLatitude() , false)).append("\n")
		 .append("  PRIM LONG DMS:\t").append(GIS.toDMS(this.getLongitude(), true)).append("\n")
		// .append("   PRIM LAT DEC:\t").append(String.valueOf(this.getLatitude())).append("\n")
		// .append("  PRIM LONG DEC:\t").append(String.valueOf(this.getLongitude())).append("\n")
		 ;
	if(this.getSlatitude() != null)
		o.append(" SOURCE LAT DMS:\t").append(GIS.toDMS(this.getSlatitude() , false)).append("\n");
	if(this.getSlongitude() != null)
		o.append("SOURCE LONG DMS:\t").append(GIS.toDMS(this.getSlongitude() , true)).append("\n");
	if(this.getSlatitude() != null)
	//	o.append(" SOURCE LAT DEC:\t").append(String.valueOf(this.getSlatitude())).append("\n");
	//if(this.getSlongitude() != null)
	//	o.append("SOURCE LONG DEC:\t").append(String.valueOf(this.getSlongitude())).append("\n");
	//if(this.getElevation() != null)
	//	o.append("      ELEV IN M:\t").append(String.valueOf(this.getElevation())).append("\n");
	if(this.getElevation() != null)
		o.append("     ELEV IN FT:\t").append(String.valueOf(Math.round(this.getElevation() * 3.28084))).append("\n");
		o.append("       MAP NAME:\t").append(this.getMapname()).append("\n");
	if(this.getCreated() != null)
		o.append("   DATE CREATED:\t").append(this.sdf.format(this.getCreated().getTime())).append("\n");
	if(this.getEdited() != null)
		o.append("    DATE EDITED:\t").append(this.sdf.format(this.getEdited().getTime())).append("\n");
		o.append("\n");
		
	  return o.toString();
  }
	// @formatter:on
}
