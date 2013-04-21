Feature: Displaying the in-memory idices in a human-readable manner.

# Name Index Internals
# --------------------
# Must use a hash table
# Hash table must use an array
# Initial size of the table will be 1019
# Use quadratic probing with the quadratic function (n² + n)/2
# use `elfhash` function from course notes.
# must display contents in a readable manner
Scenario Outline: Name index
	Given a name index
	When I search for <feature name> and <state abbreviation>
	Then I should get the file <offset> returned.

Examples:
	| feature name | state abbreviation | offset |


# Coordinate Index Internals
# --------------------------
# Must use a bucket PR Quadtree
# Must support diffent bucket sizes
Scenario Outline: Coordinate Index
	Given a coordinate index
	When I search for primary <latitude> and <longitude>
	Then I should get the file <offset> returned.

Examples:
	| latitude | longitude | offset |
