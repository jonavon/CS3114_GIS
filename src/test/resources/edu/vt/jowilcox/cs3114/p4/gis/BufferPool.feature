Feature: Buffer pool feature.
	In order to improve search speed the system will include a buffer pool.

# Must be capable of buffering up to 20 records
# must use LRU replacement
# must be able to display in human readable manner

Scenario: Test buffer pool input.
	Given I have a new buffer pool
	When I load these records:
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
	Then I should find these pooled records:
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
