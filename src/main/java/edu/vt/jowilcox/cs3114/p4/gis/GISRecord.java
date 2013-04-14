package edu.vt.jowilcox.cs3114.p4.gis;

import java.util.Date;

import edu.vt.jowilcox.cs3114.p4.USStateAbbreviation;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Compare2D;
import edu.vt.jowilcox.cs3114.p4.prquadtree.Direction;

/**
 * Class GISRecord Geographic Information System Record; Contains geographic
 * data. Each field maps to a field in the record.
 */
public class GISRecord implements Compare2D<GISRecord> {

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
	private edu.vt.jowilcox.cs3114.p4.USStateAbbreviation state;
	/**
	 * Non negative integer that represents the numeric code for the state.
	 */
	private int stateCode;
	/**
	 * Record's latitude in decimal format or unknown.
	 */
	private double latitude;
	/**
	 * Record's longitude in decimal format or unknown.
	 */
	private double longitude;
	/**
	 * Latitude of the record's source in decimal format.
	 */
	private double slatitude;
	/**
	 * Longitude of record's source in decimal format.
	 */
	private double slongitude;
	/**
	 * Record's elevation in meters
	 */
	private int elevation;
	/**
	 * Name of USGS topographic map including record.
	 */
	private String mapname;
	/**
	 * Date record was initially committed to the database, optional.
	 */
	private Date created;
	/**
	 * Date record was last updated, optional.
	 */
	private Date edited;

	//
	// Constructors
	//
	/**
	 * Constructor.
	 */
	public GISRecord() {}

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
	public edu.vt.jowilcox.cs3114.p4.USStateAbbreviation getState() {
		return this.state;
	}

	/**
	 * Set the value of stateCode Non negative integer that represents the
	 * numeric code for the state.
	 * 
	 * @param stateCode
	 *            the new value of stateCode
	 */
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * Get the value of stateCode Non negative integer that represents the
	 * numeric code for the state.
	 * 
	 * @return the value of stateCode
	 */
	public int getStateCode() {
		return this.stateCode;
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
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Get the value of created Date record was initially committed to the
	 * database, optional.
	 * 
	 * @return the value of created
	 */
	public Date getCreated() {
		return this.created;
	}

	/**
	 * Set the value of edited Date record was last updated, optional.
	 * 
	 * @param edited
	 *            the new value of edited
	 */
	public void setEdited(Date edited) {
		this.edited = edited;
	}

	/**
	 * Get the value of edited Date record was last updated, optional.
	 * 
	 * @return the value of edited
	 */
	public Date getEdited() {
		return this.edited;
	}

	//
	// Other methods
	//

	/**
	 * Convert a decimal longitude or latitude value to DMS format.
	 * 
	 * @return String
	 * @param degrees
	 *            A latitude or longitude value.
	 */
	protected String toDMS(double degrees) {
		return null;
	}

	@Override
	public long getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Direction directionFrom(long X, long Y) {
		// TODO Auto-generated method stub
		int[] a; 
		a = new int[Integer.MAX_VALUE];
		return null;
	}

	@Override
	public Direction inQuadrant(double xLo, double xHi, double yLo, double yHi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inBox(double xLo, double xHi, double yLo, double yHi) {
		// TODO Auto-generated method stub
		return false;
	}
}
