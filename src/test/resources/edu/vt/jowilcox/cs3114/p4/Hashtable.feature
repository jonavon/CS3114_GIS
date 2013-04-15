Feature: Hash table.


Scenario: Put elements in.
	Given I have a hash table
	When I put several values:
		| key           | value |
		| thirty-four   | 34    |
		| forty         | 40    |
		| thirty-five   | 35    |
		| twenty-two    | 22    |
		| seventy-six   | 76    |
		| thirty-eight  | 38    |
		| thirty-two    | 32    |
		| thirty-nine   | 39    |
		| nine          | 9     |
		| thirteen      | 13    |
		| thirty-three  | 33    |
		| seventy-seven | 77    |
		| eight         | 8     |
		| sixty-five    | 65    |
		| eighty-nine   | 89    |
		| nineteen      | 19    |
		| thirty-seven  | 37    |
		| twelve        | 12    |
		| fifty-eight   | 58    |
		| twenty-five   | 25    |
		| seventy-one   | 71    |
		| one           | 1     |
		| ninety-eight  | 98    |
		| thirty-three  | 33    |
		| eight-six     | 86    |
		| eighty-five   | 85    |
		| seven         | 7     |
		| forty-three   | 43    |
		| sixty-two     | 62    |
		| fifty-one     | 51    |
		| eighty-one    | 81    |
	Then I should find these values:
		| key           | value |
		| thirty-four   | 34    |
		| forty         | 40    |
		| thirty-five   | 35    |
		| twenty-two    | 22    |
		| seventy-six   | 76    |
		| thirty-eight  | 38    |
		| thirty-two    | 32    |
		| thirty-nine   | 39    |
		| nine          | 9     |
		| thirteen      | 13    |
		| thirty-three  | 33    |
		| seventy-seven | 77    |
		| eight         | 8     |
		| sixty-five    | 65    |
		| eighty-nine   | 89    |
		| nineteen      | 19    |
		| thirty-seven  | 37    |
		| twelve        | 12    |
		| fifty-eight   | 58    |
		| twenty-five   | 25    |
		| seventy-one   | 71    |
		| one           | 1     |
		| ninety-eight  | 98    |
		| thirty-three  | 33    |
		| eight-six     | 86    |
		| eighty-five   | 85    |
		| seven         | 7     |
		| forty-three   | 43    |
		| sixty-two     | 62    |
		| fifty-one     | 51    |
		| eighty-one    | 81    |

Scenario: Increase table size.
	Given I have a hash table
	When I put several values:
		| key           | value |
		| thirty-four   | 34    |
		| forty         | 40    |
		| thirty-five   | 35    |
		| twenty-two    | 22    |
		| seventy-six   | 76    |
		| thirty-eight  | 38    |
		| thirty-two    | 32    |
		| thirty-nine   | 39    |
		| nine          | 9     |
		| thirteen      | 13    |
		| thirty-three  | 33    |
		| seventy-seven | 77    |
		| eight         | 8     |
		| sixty-five    | 65    |
		| eighty-nine   | 89    |
		| nineteen      | 19    |
		| thirty-seven  | 37    |
		| twelve        | 12    |
		| fifty-eight   | 58    |
		| twenty-five   | 25    |
		| seventy-one   | 71    |
		And when I put a value 1 with key "one" 
	Then the internal table size should increase

Scenario: Remove item from table.
	Given I have a hash table
	When I put several values:
		| key           | value |
		| thirty-four   | 34    |
		| forty         | 40    |
		| thirty-five   | 35    |
		| twenty-two    | 22    |
		| seventy-six   | 76    |
		| thirty-eight  | 38    |
		| thirty-two    | 32    |
		| thirty-nine   | 39    |
		| nine          | 9     |
		| thirteen      | 13    |
		| thirty-three  | 33    |
		| seventy-seven | 77    |
		| eight         | 8     |
		| sixty-five    | 65    |
		| eighty-nine   | 89    |
		| nineteen      | 19    |
		| thirty-seven  | 37    |
		| twelve        | 12    |
		| fifty-eight   | 58    |
		| twenty-five   | 25    |
		| seventy-one   | 71    |
		And when I remove  values:
			| key           | value |
			| thirty-four   | 34    |
			| forty         | 40    |
			| thirty-five   | 35    |
			| twenty-two    | 22    |
			| seventy-six   | 76    |
			| thirty-eight  | 38    |
			| thirty-nine   | 39    |
			| thirteen      | 13    |
			| seventy-seven | 77    |
			| sixty-five    | 65    |
			| eighty-nine   | 89    |
			| nineteen      | 19    |
			| thirty-seven  | 37    |
			| twelve        | 12    |
			| fifty-eight   | 58    |
			| twenty-five   | 25    |
			| seventy-one   | 71    |
	Then the internal table size should increase
	Then I should not find these values:
		| key           | value |
		| thirty-two    | 32    |
		| eight         | 8     |
		| thirty-three  | 33    |
		| nine          | 9     |
		And I should find these values:
			| key           | value |
			| thirty-four   | 34    |
			| forty         | 40    |
			| thirty-five   | 35    |
			| twenty-two    | 22    |
			| seventy-six   | 76    |
			| thirty-eight  | 38    |
			| thirty-nine   | 39    |
			| thirteen      | 13    |
			| seventy-seven | 77    |
			| sixty-five    | 65    |
			| eighty-nine   | 89    |
			| nineteen      | 19    |
			| thirty-seven  | 37    |
			| twelve        | 12    |
			| fifty-eight   | 58    |
			| twenty-five   | 25    |
			| seventy-one   | 71    |

#  HASH TABLE Display
#  
#  ┏━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━┓
#  ┃          ┃           ┃                  ┃
#  ┣━━━━━━━━━━╋━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━┫
#  ┗━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━┛
#  ┌──────┬───────────┬──────────────────┐
#  │      │           │                  │
#  ├──────┼───────────┼──────────────────┤
#  └──────┴───────────┴──────────────────┘
#  
#           TABLE SIZE: 7
#       TABLE CAPACITY: 31
#  TABLE RESIZE FACTOR: 0.70
#  ┏━━━━━━━━━━┳━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━┓
#  ┃          ┃ KEY       ┃ VALUE            ┃
#  ┗━━━━━━━━━━┻━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━┛
#  ┌──────────┬───────────┬──────────────────┐
#  │ 00000000 │  <NULL>   │ value            │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000001 │  key      │ value            │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000002 │           │                  │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000003 │ **************************** │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000004 │  key      │ value            │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000005 │           │                  │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000006 │  key      │ value            │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000007 │ **************************** │
#  ├──────────┼───────────┼──────────────────┤
#  │ 00000008 │           │                  │
#  └──────────┴───────────┴──────────────────┘
