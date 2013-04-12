Feature: Delete a node from the quad tree.


Scenario: Delete one item tree
	Given I have an empty quadtree
	When I insert points:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
	And I delete point(s):
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
	Then I should have an empty tree.

Scenario: delete
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
	And I delete point(s):
		| name | xcoord | ycoord |
		| ward | 125    | -60    |
	Then I should not find:
		| name | xcoord | ycoord |
		| ward | 125    | -60    |
	And I should find:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| turf | 80     | 80     |
		| area | 88     | 39     |
		| city | 188    | 203    |

Scenario: delete simply
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
	Then I should be able to delete them all:
		| name | xcoord | ycoord |
		| burg | 100    | 125    |
		| town | 25     | -30    |
		| zone | -55    | 80     |
		| ward | 125    | -60    |
		| turf | 80     | 80     |
		| area | 88     | 39     |
		| city | 188    | 203    |

Scenario: Delete all from root
	Given I have an empty quadtree with world of (0,0), (32768,32768)
	When I insert points:
			| xcoord | ycoord |
			| 26192  | 26456  |
			| 10904  | 5884   |
			| 4231   | 23441  |
			| 1441   | 24359  |
			| 21934  | 12363  |
			| 6717   | 24700  |
			| 18247  | 30899  |
			| 27252  | 26981  |
			| 18142  | 30106  |
			| 2241   | 13160  |
			| 25450  | 5795   |
			| 27865  | 26023  |
			| 10079  | 27862  |
			| 13141  | 27996  |
			| 4204   | 25331  |
			| 17560  | 6628   |
			| 18686  | 13895  |
			| 15051  | 29229  |
			| 2088   | 30714  |
			| 12146  | 25740  |
			| 28650  | 21096  |
			| 32543  | 5539   |
			| 14260  | 30671  |
			| 5523   | 19981  |
			| 11666  | 25420  |
			| 2779   | 22141  |
			| 14558  | 30644  |
			| 22580  | 3833   |
			| 12585  | 29773  |
			| 31004  | 19855  |
			| 23884  | 15037  |
			| 6878   | 13611  |
			| 26316  | 9682   |
			| 27051  | 19302  |
			| 30202  | 11055  |
			| 29313  | 13819  |
			| 5787   | 24866  |
			| 5890   | 8868   |
			| 8016   | 6669   |
			| 9622   | 29275  |
	Then I should be able to delete them all:
			| xcoord | ycoord |
			| 26192  | 26456  |
			| 10904  | 5884   |
			| 4231   | 23441  |
			| 1441   | 24359  |
			| 21934  | 12363  |
			| 6717   | 24700  |
			| 18247  | 30899  |
			| 27252  | 26981  |
			| 18142  | 30106  |
			| 2241   | 13160  |
			| 25450  | 5795   |
			| 27865  | 26023  |
			| 10079  | 27862  |
			| 13141  | 27996  |
			| 4204   | 25331  |
			| 17560  | 6628   |
			| 18686  | 13895  |
			| 15051  | 29229  |
			| 2088   | 30714  |
			| 12146  | 25740  |
			| 28650  | 21096  |
			| 32543  | 5539   |
			| 14260  | 30671  |
			| 5523   | 19981  |
			| 11666  | 25420  |
			| 2779   | 22141  |
			| 14558  | 30644  |
			| 22580  | 3833   |
			| 12585  | 29773  |
			| 31004  | 19855  |
			| 23884  | 15037  |
			| 6878   | 13611  |
			| 26316  | 9682   |
			| 27051  | 19302  |
			| 30202  | 11055  |
			| 29313  | 13819  |
			| 5787   | 24866  |
			| 5890   | 8868   |
			| 8016   | 6669   |
			| 9622   | 29275  |
	And I should have an empty tree.

