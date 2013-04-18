Feature: Insertion of an element


Scenario: Multiple Insert
	Given I have an empty quadtree
	When I insert points:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| ward | 125    | -60    |
		| turf | 80     | 80     |
		| area | 88     | 39     |
		| city | 188    | 203    |
	Then I should find:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| ward | 125    | -60    |
		| turf | 80     | 80     |
		| area | 88     | 39     |
		| city | 188    | 203    |

Scenario: Insert duplicate elements
	Given I have an empty quadtree
		And I insert points:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
	Then inserting the same should fail:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
	


Scenario Outline: Insert into internal node
	Given I have an empty quadtree
		And I insert points:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
	When I insert points:
		| name | xcoord | ycoord |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| ward | -125   | -60    |
	Then I should find <name> at the <cardinal> field.

	Examples:
		| name | cardinal |
		| burg | NE       |
		| town | SE       |
		| zone | NW       |
		| ward | SW       |


Scenario: Find set of elements
	Given I have an empty quadtree
	When I insert points:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| ward | 125    | -60    |
		| turf | 80     | 80     |
		| area | 88     | 39     |
		| city | 188    | 203    |
	Then I should find these points in the area (-100, -100), (100, 100):
		| name | xcoord | ycoord |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| turf | 80     | 80     |
		| area | 88     | 39     |

Scenario: Insert more than the bucket
	Given I have an empty quadtree
	When I insert points:
		| name  | xcoord | ycoord |
		| burg  | 100    | 125    |
		| burg1 | 100    | 125    |
		| burg3 | 100    | 125    |
		| burg4 | 100    | 125    |
		| burg5 | 100    | 125    |
	Then I should find:
		| name  | xcoord | ycoord |
		| burg  | 100    | 125    |
		| burg1 | 100    | 125    |
		| burg3 | 100    | 125    |
		| burg4 | 100    | 125    |
		| burg5 | 100    | 125    |