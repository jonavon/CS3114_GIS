/**
 * 
 */
package edu.vt.jowilcox.cs3114.p4;

/**
 * @author "Jonavon Wilcox <jowilcox@vt.edu>"
 */
public enum USStateAbbreviation {
	// @formatter:off
	AL("ALABAMA", 1),
	AK("ALASKA", 2),
	AS("AMERICAN SAMOA", 60),
	AZ("ARIZONA", 4),
	AR("ARKANSAS", 5),
	CA("CALIFORNIA", 6),
	CO("COLORADO", 8),
	CT("CONNECTICUT", 9),
	DE("DELAWARE", 10),
	DC("DISTRICT OF COLUMBIA", 11),
	FM("FEDERATED STATES OF MICRONESIA", 64),
	FL("FLORIDA", 12),
	GA("GEORGIA", 13),
	GU("GUAM", 66),
	HI("HAWAII", 15),
	ID("IDAHO", 16),
	IL("ILLINOIS", 17),
	IN("INDIANA", 18),
	IA("IOWA", 19),
	KS("KANSAS", 20),
	KY("KENTUCKY", 21),
	LA("LOUISIANA", 22),
	ME("MAINE", 23),
	MH("MARSHALL ISLANDS", 68),
	MD("MARYLAND", 24),
	MA("MASSACHUSETTS", 25),
	MI("MICHIGAN", 26),
	MN("MINNESOTA", 27),
	MS("MISSISSIPPI", 28),
	MO("MISSOURI", 29),
	MT("MONTANA", 30),
	NE("NEBRASKA", 31),
	NV("NEVADA", 32),
	NH("NEW HAMPSHIRE", 33),
	NJ("NEW JERSEY", 34),
	NM("NEW MEXICO", 35),
	NY("NEW YORK", 36),
	NC("NORTH CAROLINA", 37),
	ND("NORTH DAKOTA", 38),
	MP("NORTHERN MARIANA ISLANDS", 69),
	OH("OHIO", 39),
	OK("OKLAHOMA", 40),
	OR("OREGON", 41),
	PW("PALAU", 70),
	PA("PENNSYLVANIA", 42),
	PR("PUERTO RICO", 72),
	RI("RHODE ISLAND", 44),
	SC("SOUTH CAROLINA", 45),
	SD("SOUTH DAKOTA", 46),
	TN("TENNESSEE", 47),
	TX("TEXAS", 48),
	UT("UTAH", 49),
	VT("VERMONT", 50),
	VI("VIRGIN ISLANDS", 78),
	VA("VIRGINIA", 51),
	WA("WASHINGTON", 53),
	WV("WEST VIRGINIA", 54),
	WI("WISCONSIN", 55),
	WY("WYOMING", 56);
	// @formatter:on

	/** Full name of the state in all-caps. */
	private String fullname;
	private int numeric;

	/**
	 * Constructor.
	 */
	private USStateAbbreviation(String fullname, int numeric) {
		this.fullname = fullname;
		this.numeric = numeric;
	}

	/**
	 * Returns US State abbreviation's fullname all capitilized.
	 * 
	 * @return String the fullname of the abbreviation.
	 */
	public String getFullname() {
		return this.fullname;
	}
	
	/** Returns US State numeric code.
	 * 
	 * @return int the numeric code of the state.
	 */
	public int getNumeric() {
		return this.numeric;
	}
}
