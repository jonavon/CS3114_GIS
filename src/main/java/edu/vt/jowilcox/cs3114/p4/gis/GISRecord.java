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

	//
	// Constructors
	//
	/**
	 * Constructor.
	 */
	public GISRecord() {}
	public GISRecord(String fields) {
		this(fields.split("\\|"));
	}
	public GISRecord(String... fields) {
		// 0 FEATURE_ID|1 FEATURE_NAME|2 FEATURE_CLASS|3 STATE_ALPHA|4 STATE_NUMERIC|5 COUNTY_NAME|6 COUNTY_NUMERIC|7 PRIMARY_LAT_DMS|8 PRIM_LONG_DMS|9 PRIM_LAT_DEC|
		// 10 PRIM_LONG_DEC|11 SOURCE_LAT_DMS|12 SOURCE_LONG_DMS|13 SOURCE_LAT_DEC|14 SOURCE_LONG_DEC|15 ELEV_IN_M|16 ELEV_IN_FT|17 MAP_NAME|18 DATE_CREATED|19 DATE_EDITED
		this.fid = Integer.valueOf(fields[0]);
		this.name = (fields[1].isEmpty())? null : fields[1];
		this.classifier = (fields[2].isEmpty())? null : fields[2];
		this.state = (fields[3].isEmpty())? null : USStateAbbreviation.valueOf(fields[3]);
		this.latitude = (fields[9].isEmpty())? null : Double.valueOf(fields[9]);
		this.longitude = (fields[10].isEmpty())? null : Double.valueOf(fields[10]);
		this.slatitude = (fields[13].isEmpty())? null : Double.valueOf(fields[13]);
		this.slongitude = (fields[14].isEmpty())? null : Double.valueOf(fields[14]);
		this.elevation = (fields[15].isEmpty())? null : Integer.valueOf(fields[15]);
		this.mapname = (fields[17].isEmpty())? null : fields[17];
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		this.created = Calendar.getInstance();
		this.edited = Calendar.getInstance();
		try {
	    this.created.setTime(sdf.parse(fields[18]));
			this.edited.setTime(sdf.parse(fields[18]));
    }
    catch (ParseException e) {
	    e.printStackTrace();
    }
	}
	
	public GISRecord(int fid, String name, String classifier,
      USStateAbbreviation state, double latitude,
      double longitude, double slatitude, double slongitude, int elevation,
      String mapname, Calendar created, Calendar edited) {
	  super();
	  this.fid = fid;
	  this.name = name;
	  this.classifier = classifier;
	  this.state = state;
	  this.latitude = latitude;
	  this.longitude = longitude;
	  this.slatitude = slatitude;
	  this.slongitude = slongitude;
	  this.elevation = elevation;
	  this.mapname = mapname;
	  this.created = created;
	  this.edited = edited;
  }

	//
	// Methods
	//

	//
	// Accessor methods
	//

	/**
	 * Set the value of fid Non negative integer. Unique identifier for this
	 * geographic record.
	 * 
	 * @param fid
	 *            the new value of fid
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
	 *            the new value of name
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
	 *            the new value of classifier
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
	 *            the new value of state
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
	 * Get the value of stateCode Non negative integer that represents the
	 * numeric code for the state.
	 * 
	 * @return the value of stateCode
	 */
	public int getStateCode() {
		return this.state.getNumeric();
	}

	/**
	 * Set the value of latitude Record's latitude in decimal format or unknown.
	 * 
	 * @param latitude
	 *            the new value of latitude
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
	 * Set the value of longitude Record's longitude in decimal format or
	 * unknown.
	 * 
	 * @param longitude
	 *            the new value of longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Get the value of longitude Record's longitude in decimal format or
	 * unknown.
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
	 *            the new value of slatitude
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
	public double getSlatitude() {
		return this.slatitude;
	}

	/**
	 * Set the value of slongitude Longitude of record's source in decimal
	 * format.
	 * 
	 * @param slongitude
	 *            the new value of slongitude
	 */
	public void setSlongitude(double slongitude) {
		this.slongitude = slongitude;
	}

	/**
	 * Get the value of slongitude Longitude of record's source in decimal
	 * format.
	 * 
	 * @return the value of slongitude
	 */
	public double getSlongitude() {
		return this.slongitude;
	}

	/**
	 * Set the value of elevation Record's elevation in meters
	 * 
	 * @param elevation
	 *            the new value of elevation
	 */
	public void setElevation(int elevation) {
		this.elevation = elevation;
	}

	/**
	 * Get the value of elevation Record's elevation in meters
	 * 
	 * @return the value of elevation
	 */
	public int getElevation() {
		return this.elevation;
	}

	/**
	 * Set the value of mapname Name of USGS topographic map including record.
	 * 
	 * @param mapname
	 *            the new value of mapname
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
	 *            the new value of created
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
	 *            the new value of edited
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
}
