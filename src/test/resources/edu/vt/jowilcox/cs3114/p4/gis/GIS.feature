Feature: The system will run based on three files. A database file, a commands file, and a log file.

Scenario: Database file should be created as an empty file.
	Given a database file
		And the database file exists
	When the system is run
	Then the existing file should be truncated.

Scenario: The commands file should exist
	Given a command file
		And the command file does not exists
	When the system is run
	Then an error message should be written to the console:
	"""
	Command file not found
	"""

Scenario: Log file should be created as an empty file.
	Given a log file
		And the log file exists
	When the system is run
	Then the existing file should be truncated.

# The controller should validate the command line arguments
# Command Processor
# GIS record object

