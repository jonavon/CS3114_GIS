Feature: Point coordinates


Scenario Outline: Direction from another point.
	Given a point x-coordinate <xcoord> and y-coordinate <ycoord>
	Then it should be <direction> from 0, 0

Scenarios:
		| name | xcoord | ycoord | direction  |
		| burg | 100    | 125    | NE         |
		| town | 25     | -30    | SE         |
		| zone | -55    | 80     | NW         |
		| ward | 125    | -60    | SE         |
		| turf | 80     | 80     | NE         |
		| area | -88    | 39     | NW         |
		| city | 188    | 203    | NE         |
		| spot | 0      | 0      | NOQUADRANT |


# Y >= 0 results in N
# X >= 0 results in E

# ((X - P₁) >= 0) E
# ((Y - P₂) >= 0) N

Scenario Outline: In box
	Given a point x-coordinate <xcoord> and y-coordinate <ycoord>
	Then it should return <inbox> for box 25, 25, 100, 100

# (X₁, Y₁) (X₂, Y₂)
# x > X₁ && x <= X₂
# y > Y₁ && y <= Y₂

Scenarios:
		| name | xcoord | ycoord | inbox |
		| burg | 100    | 125    | false |
		| town | 25     | -30    | false |
		| zone | -55    | 80     | false |
		| ward | 125    | -60    | false |
		| turf | 80     | 80     | true  |
		| area | -88    | 39     | false |
		| city | 188    | 203    | false |

Scenario Outline: In quadrant
	Given a point x-coordinate <xcoord> and y-coordinate <ycoord>
	Then it should return <inquadrant> for rectangle (-300, -200) (100, 100)

# Mid point is -100, -50
Scenarios:
		| name | xcoord | ycoord | inquadrant |
		| burg | 100    | 125    | NOQUADRANT |
		| town | 25     | -30    | NE         |
		| zone | -155   | 80     | NW         |
		| ward | -125   | -60    | SW         |
		| turf | 80     | -80    | SE         |
		| area | -88    | 39     | NE         |
		| city | 188    | 203    | NOQUADRANT |
		| spot | -100   | -50    | NE         |


Scenario: Check that two points equal
	Given a point:
		| name  | xcoord | ycoord |
		| bburg | 100    | 125    |
	Then it should equal the point:
		| name       | xcoord | ycoord |
		| blacksburg | 100    | 125    |
		

