# CSCI-3901-A4
Software Development Concepts - Puzzle similar to - https://api.razzlepuzzles.com/fillin

Overview
---------

This program lets you solve the puzzle, which accepts the puzzle constraints which accepts the stream from the input stream. It defines the summary of the formatting requirements is in the CSCI 3901 course assignment #4 information in the course's bright space.

This program uses several implementations to create slots and fits the word and further solves the puzzle 

	-	The puzzle has horizontal and vertical slots to fill the choice of words.
	-	The puzzle checks the choice of words from the list and fills the data in the puzzle using recursion and 			backtracking.

Each of these cases have their own implementation using array lists for words to store and hash Maps and Queues using linked lists for storing and processing the word slots for recursion and back tracking of the puzzle.

Files and external data
-----------------------

  - FillInPuzzle.java -- class that helps to have multiple functions to load data and solve the puzzle
  -	PuzzleWordConstraint.java -- class that stores the word constraints of the puzzle, intersections slots and their 								paths in the puzzle
  - PuzzleConstraint.java -- Class that holds the total number of columns, rows and words in the word puzzle 
  - CharGrid.java -- class that helps to store the char at each grid in the file

Data structures and their relations to each other
-------------------------------------------------

The program uses takes the input stream of the data and reads the data line by line breaking into puzzle constraints which provides the slots for the puzzle and returns the list of words. The list of words are iterated and check the slots of the puzzle recusively using back tracking of the data and returns true if the data is filled.

	hashMap, Queue - Stores all the data slots in the puzzle for filling the vertical and horizontal directed words.

	recursion and back tracking - checks the choice of words recursively and fits the data if correctly fits the data in the slot else back track the data and check it recursively until it fills the data

Key algorithms and design elements
----------------------------------

	loadPuzzle
		-	reads the data from a input stream a puzzle and sends to data formatter for getting the puzzle constraints.
		-	Returns true if the puzzle is read and ready to be solved. Returns false if some any error occurs.

	solve
		- 	that takes the hash map of all the choices, word slots, choice of the words and other parameters to fill 		the data.
		-	the function takes the data from the input stream and checks to fill horizontally or verically
		-	it recursively checks the data from the choice of words and clones the data at each state and back tracks 		the data if it does not fit the data.
		-	Returns true if the puzzle is solved and false if the puzzle cannot be solved with the given set of words. 

	print
		-	prints the data as a same structure of the puzzle using output stream

	choices
		-	returns the number of choices that the program helps to solve the puzzle using back tracking.


Modules of Program development
-------------------------------
	-	Read the input data from the BufferReader stream.
	-	Parsing all the lines of the data and get the constraints of the puzzle.
	-	iterate through all the choice of the words and back track the data if does not fill the data.
	-	if data is filled it will return true

Assumptions and Limitations
---------------------------

	-	No word is repeated in the set of input words.
	-	The puzzle is case invariant.
	-	The words in the puzzle all fill from left to right or from top to bottom. 

Ideas followed for program efficiency
--------------------------------------
	-	The data is retrevied to the 2D array for better manipulations of the puzzle.
	-	Scanning all the slots to fit in horizontally or verically checking that it does not conflict the other data.
	-	The data words are iterated recusively and back tracked the data using stack memory until it finds the solution.
