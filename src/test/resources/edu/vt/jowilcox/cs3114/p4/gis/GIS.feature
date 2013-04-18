Feature: The system will run based on three files. A database file, a commands file, and a log file.

	Background:
#		Given I copy import file from "src/test/resources/files/VA_Monterey.txt"
#		And I copy import file from "src/test/resources/files/CO_All.txt"
#		And I copy import file from "src/test/resources/files/NM_All.txt"
		Given I copy command file from "src/test/resources/files/command/Script08.txt"
		
	Scenario: Database file should be created as an empty file.
		Given a database file
			And the database file exists
		When the system is run
		Then the existing database file should be truncated.
	
	Scenario: The commands file should exist
		Given a command file
			And the command file does not exist
		When the system is run
		Then an error message should be written to the console:
		"""
		Command file not found
		"""
	
	Scenario: Log file should be created as an empty file.
		Given a log file
			And the log file exist
		When the system is run
		Then the existing log file should be truncated.

# The controller should validate the command line arguments
# Command Processor
# GIS record object

