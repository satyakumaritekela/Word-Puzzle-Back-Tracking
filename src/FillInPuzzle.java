/**** Author: Satya Kumar Itekela, Banner Id: B00839907 ****/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

/** Class that helps to read the puzzle data and solve the problem **/
public class FillInPuzzle {
	
	/** Linked Hash Map for storing all the puzzle constraints **/
	LinkedHashMap<Integer, PuzzleWordConstraint> puzzleWordConst = new LinkedHashMap<Integer, PuzzleWordConstraint>();
	/** For storing list of words **/
	ArrayList<String> listOfWords = new ArrayList<String>();
	PuzzleConstraint fillInpuzzleConst;
	/** For storing puzzle grid**/
	char[][] puzzleGrid;
	int numberOfChoices = 0;

	/** Attributes for storing the puzzle solving **/
	HashMap<String, Boolean> visitedWord = new HashMap<String, Boolean>();
	HashMap<Integer, ArrayList<String>> choicesLeft = new HashMap<Integer, ArrayList<String>>();	
	ArrayList<PuzzleWordConstraint> wordSlots = new ArrayList<PuzzleWordConstraint>();
	Stack<PuzzleWordConstraint> nextWordSlots = new Stack<PuzzleWordConstraint>();
	
	HashMap<PuzzleWordConstraint, ArrayList<PuzzleWordConstraint>> intersectionSlots = new HashMap<PuzzleWordConstraint, ArrayList<PuzzleWordConstraint>>();
	
	/**** Takes the input as a buffered reader that takes line by line ****/
	
	public boolean loadPuzzle(BufferedReader stream) {
		String puzzleConst;
		int numberOfPosConst = 0;
		int numberOfWords = 0;
		String[] puzzleGridConst;
		
		try {
			// reading first line to get puzzle constraints
			puzzleConst = stream.readLine();
			puzzleGridConst = puzzleConst.trim().split("\\s+");	// retrieving puzzle constraints rows, columns, words using split function

			if(puzzleGridConst.length != 3) {
				System.out.println("Please enter valid details. Should contain 3 integers for number of rows, columns and words");
				return false;
			}
			
			try {	
				fillInpuzzleConst = new PuzzleConstraint(Integer.parseInt(puzzleGridConst[0]), Integer.parseInt(puzzleGridConst[1]), Integer.parseInt(puzzleGridConst[2]));	
				puzzleGrid = new char[fillInpuzzleConst.getTotalNumberOfRows()][fillInpuzzleConst.getTotalNumberOfColumns()];
				for(char[] puzzleGridRow : puzzleGrid) {
		            Arrays.fill(puzzleGridRow, '*');
		        }
			}
	    	// Handle the number of constraints to 3 integers and with proper spacing between them
			catch(IndexOutOfBoundsException ie) {
				System.out.println("Please enter valid number of rows or columns or words");
				return false;			
			}
	    	// If the data is not in number format, handle using these catch statements		
			catch(NumberFormatException ne) {
				System.out.println("Please enter integers only. Should be valid number of rows or columns or words");
				return false;
			}

			puzzleConst = stream.readLine();
			
			// reading all the lines from the second line until it reaches all the positions of words present
			while((puzzleConst != null) && (numberOfPosConst + 1 <= fillInpuzzleConst.getTotalNumberOfWords())) {
				puzzleGridConst = puzzleConst.trim().split("\\s+");	// retrieving puzzle word constraints position and number of words and direction of a word using split function
				
				if(puzzleGridConst.length != 4) {
					System.out.println("Please enter valid details. Should contain 3 integers for position of a word, number of letters in a word and string as word direction");
					return false;
				}
				
				try {
					if(!(puzzleGridConst[3].equalsIgnoreCase("h") || puzzleGridConst[3].equalsIgnoreCase("v"))) {
						System.out.println("Please enter valid word direction. Either h or v");
						return false;
					}
					puzzleWordConst.put(numberOfPosConst, new PuzzleWordConstraint(numberOfPosConst,Integer.parseInt(puzzleGridConst[0]), Integer.parseInt(puzzleGridConst[1]), Integer.parseInt(puzzleGridConst[2]),puzzleGridConst[3]));
					wordSlots.add(numberOfPosConst,puzzleWordConst.get(numberOfPosConst));
					puzzleConst = stream.readLine();
					numberOfPosConst++;
				}
		    	// Handle the number of constraints to 3 integers and word direction with proper spacing between them
				catch(IndexOutOfBoundsException ie) {
					System.out.println("Please enter valid position of the word, number of letters and its direction");
					return false;			
				}
		    	// If the data is not in number format, handle using these catch statements		
				catch(NumberFormatException ne) {
					System.out.println("Please enter integers and string format correctly. Should be valid postion, number of letters and word direction");
					return false;
				}
			}
			// Parsing through all the words from the list and saving in the hash map created
			while((puzzleConst != null) && (numberOfWords + 1 <= fillInpuzzleConst.getTotalNumberOfWords())) {
				if(puzzleConst.trim().contains(" ") || !puzzleConst.trim().matches("^[a-zA-Z]*$")) {					
					System.out.println("Please enter valid details. Should not contain space in between / Should contain only strings without integers");
					return false;
				};
				if(listOfWords.stream().anyMatch(puzzleConst.trim()::equalsIgnoreCase)) {
					System.out.println("Given word '"+ puzzleConst + "' already exists. Please enter Unique names");
					return false;
				}
				listOfWords.add(puzzleConst.trim());
				puzzleConst = stream.readLine();
				numberOfWords++;
			}
			
			if(numberOfWords != wordSlots.size()) {
				return false;
			}
			addChoices();
			getIntersections();
			return true;
		}
    	// if the input is null, handle the case by IO exceptionS   	
		catch(IOException ioe) {
			System.out.println("Please check the input file. Please give puzzle details properly");
			return false;
		}
    	// if the data is null or empty, handle the case by not allowing the data to read    	
		catch(NullPointerException ne) {
			System.out.println("Stream input cannot be null. Please check the input");
			return false;
		}		
		
	}
	
	/** method that adds the list of choices into the array list **/
	public void addChoices() {
		for(String word : listOfWords) {
			if(choicesLeft.get(word.length()) == null) {
				choicesLeft.put(word.length(), new ArrayList<String>());
			}
			choicesLeft.get(word.length()).add(word);
			for(PuzzleWordConstraint slot : wordSlots) {
				if(word.length() == slot.getTotalNumberOfLetters()) {
					slot.getPossibleWords().add(word);
				}
			}
		}
	}
	
	/** methods that gets the intersections of each word slot with the other slot **/
	public void getIntersections() {
		for(int i = 0; i < wordSlots.size(); i++) {
			ArrayList<CharGrid> slotPath= wordSlots.get(i).getSlotPath();
			if(!nextWordSlots.contains(wordSlots.get(i)))
				nextWordSlots.add(wordSlots.get(i));
			for(int j = 0; j < wordSlots.size(); j++) {
				if(wordSlots.get(i) != wordSlots.get(j)) {
					ArrayList<CharGrid> checkPath= wordSlots.get(j).getSlotPath();
					for(CharGrid cf : checkPath) {
						int numberOfLetter = 0;
						for(CharGrid cg : slotPath ) {
							numberOfLetter++;
							if((cf.getColumnNumber() == cg.getColumnNumber()) && (cf.getRowNumber() == cg.getRowNumber())) {
								if(intersectionSlots.get(wordSlots.get(i)) == null) {
									intersectionSlots.put(wordSlots.get(i), new ArrayList<PuzzleWordConstraint>());
								}
								wordSlots.get(i).getIntesectionLength().put(wordSlots.get(j).getWordKey(), numberOfLetter - 1);
								intersectionSlots.get(wordSlots.get(i)).add(wordSlots.get(j));
								puzzleWordConst.get(i).getIntersectedSlots().add(wordSlots.get(j));		
								wordSlots.get(i).getIntesectionGrids().put(wordSlots.get(j), cg);
								if(!nextWordSlots.contains(wordSlots.get(j)))
									nextWordSlots.add(wordSlots.get(j));
							}
						}
					}
				}
				
			}
		}
	}
	
	/**** method that helps to solve the puzzle ****/
	
	public boolean solve() {			
		boolean flag = false;
		
		/** passing the constraints to the solve puzzle method **/
		flag = solvePuzzle(nextWordSlots, choicesLeft, puzzleGrid, visitedWord);
		
		return flag;
	}
	
	
	/** puzzle that helps to iterate over the loop to solve the puzzle by iterating through the loop**/
	private boolean solvePuzzle(Stack<PuzzleWordConstraint> solveNextWordSlots, HashMap<Integer, ArrayList<String>> solveChoicesLeft, char[][] solvePuzzleGrid, HashMap<String, Boolean> solveVisitedWord) {

		copyState(solveNextWordSlots, solveChoicesLeft, solvePuzzleGrid, solveVisitedWord);
		
		if(solveNextWordSlots.size() == 0) {
			return true;
		}
		
		PuzzleWordConstraint wordSlot = solveNextWordSlots.peek();
		
		ArrayList<String> visitedWords = new ArrayList<String>();
		
		/** Iterate over the words in the list***/
		for(String choiceword : listOfWords) {
			/** check and iterate over the loop until word finds to fit**/
			if(choiceword.length() != wordSlot.getTotalNumberOfLetters())  {
				continue;
			}
			if(!solveChoicesLeft.get(wordSlot.getTotalNumberOfLetters()).contains(choiceword)) {
				continue;
			}
			if (!wordSlot.getPossibleWords().contains(choiceword)) {
				continue;
			}
			/** checking whether the word can be filled or not **/
			if(!checkToFill(wordSlot, choiceword, solvePuzzleGrid)) {
				solveVisitedWord.replace(choiceword, false);
				visitedWords.add(choiceword);
				String repeat = null;
				if(visitedWords.size() == solveChoicesLeft.get(wordSlot.getTotalNumberOfLetters()).size()) {
					visitedWords.clear();
					return false;
				}
				for(String word : solveChoicesLeft.get(wordSlot.getTotalNumberOfLetters())) {
					if(visitedWords.contains(word)) {
						continue;
					}
					else {
						repeat = word;
						break;
					}
				}
				if(repeat != null) {
					continue;
				}
			}
			else {
				solveNextWordSlots.pop();
				solveChoicesLeft.get(wordSlot.getTotalNumberOfLetters()).remove(choiceword);
				fillTheWord(wordSlot, choiceword, solvePuzzleGrid);
				solveVisitedWord.replace(choiceword, true);
				
				if(solvePuzzle(solveNextWordSlots, solveChoicesLeft, solvePuzzleGrid, solveVisitedWord))
					return true;
				else {	// back tracking the state at each puzzle state of the puzzle
					wordSlot.getPossibleWords().remove(choiceword);
					numberOfChoices++;	
					if(wordSlot.getWordKey() == 1) {
						for(char[] PuzzleGrid : solvePuzzleGrid) {
				            Arrays.fill(PuzzleGrid, '*');
				        }
						solveChoicesLeft.get(wordSlot.getTotalNumberOfLetters()).add(choiceword);
						solveNextWordSlots.push(wordSlot);
					}
					else {
						solvePuzzleGrid = puzzleGrid;
						solveNextWordSlots = nextWordSlots;
						solveChoicesLeft = choicesLeft;
						solveVisitedWord = visitedWord;	
					}				
				}
			}	
		}
		
		return false;
	}
	
	/*** Copying the state at each iteration into the loop ****/
	public void copyState(Stack<PuzzleWordConstraint> solveNextWordSlots, HashMap<Integer, ArrayList<String>> solveChoicesLeft, char[][] solvePuzzleGrid, HashMap<String, Boolean> solveVisitedWord) {
		
		Stack<PuzzleWordConstraint> newNextWordSlots = new Stack<PuzzleWordConstraint>();
		
		newNextWordSlots.addAll(solveNextWordSlots);
		
		nextWordSlots = newNextWordSlots;
		
		HashMap<Integer, ArrayList<String>> newChoicesLeft = new HashMap<Integer, ArrayList<String>>();
		
		for(Map.Entry<Integer, ArrayList<String>> entry : solveChoicesLeft.entrySet()) {
			if(newChoicesLeft.isEmpty() || newChoicesLeft.get(entry.getKey()) == null) {
				newChoicesLeft.put(entry.getKey(), new ArrayList<String>());
			}
			newChoicesLeft.get(entry.getKey()).addAll(entry.getValue());
		}
		
		choicesLeft = newChoicesLeft;
		
		char[][] newPuzzleGrid = new char[solvePuzzleGrid.length][solvePuzzleGrid[0].length];
		
		for(int i = 0; i < solvePuzzleGrid.length; i++) {
			for(int j = 0; j < solvePuzzleGrid[i].length; j++) {
				newPuzzleGrid[i][j] = solvePuzzleGrid[i][j];
			}			
		}
		puzzleGrid = newPuzzleGrid;
		
		HashMap<String, Boolean> newVisitedWord= new HashMap<String, Boolean>();
		
		for(Map.Entry<String, Boolean> entry : solveVisitedWord.entrySet()) {
			newVisitedWord.put(entry.getKey(),entry.getValue());
		}
		
		visitedWord = newVisitedWord;
	}
		
	
	/** method that helps to check whether to fill horizontally or vertically in the slot  without any conflict **/	
	private boolean checkToFill(PuzzleWordConstraint firstSlot, String word,  char[][] puzzleGrid) {
		boolean fill = false;
		 
		/** checks with the condition if it can able to fill in the slot or not and fills it ***/
		for(Map.Entry<PuzzleWordConstraint,CharGrid> entry : firstSlot.getIntesectionGrids().entrySet()) {
			if(firstSlot.getWordDirection().equalsIgnoreCase("h")) {
				if((puzzleGrid[entry.getValue().getRowNumber()][entry.getValue().getColumnNumber()] == '*') 
						|| (puzzleGrid[entry.getValue().getRowNumber()][entry.getValue().getColumnNumber()] == word.charAt(firstSlot.getIntesectionLength().get(entry.getKey().getWordKey())))) {
					fill = true;
				}
				else {
					return false;
				}
			}
			if(firstSlot.getWordDirection().equalsIgnoreCase("v")) {
				if((puzzleGrid[entry.getValue().getRowNumber()][entry.getValue().getColumnNumber()] == '*') 
						|| (puzzleGrid[entry.getValue().getRowNumber()][entry.getValue().getColumnNumber()] == word.charAt(firstSlot.getIntesectionLength().get(entry.getKey().getWordKey())))) {
					fill = true;
				}
				else {
					return false;
				}
			}							
		}	
		
		/**** if not able to fill it will return false else true ****/
		return fill;
	}
	
	/** method that helps to fill horizontally or vertically in the slot **/	
	public void fillTheWord(PuzzleWordConstraint wordSlot, String word, char[][] puzzleGrid) {
		if(wordSlot.getWordDirection().equalsIgnoreCase("v")) {
			fillVertical(wordSlot.getColumnNumber(), wordSlot.getRowNumber(), wordSlot.getTotalNumberOfLetters(), word, puzzleGrid);
		}
		else {
			fillHorizontal(wordSlot.getColumnNumber(), wordSlot.getRowNumber(), wordSlot.getTotalNumberOfLetters(), word, puzzleGrid);
		}
	}

	/** method that helps to fill the words in the slots vertically in the puzzle **/
	private void fillVertical(int columnNumber, int rowNumber, int numberOfLetters, String word,  char[][] puzzleGrid) {
		char[] wordCharArray = word.toCharArray();
		int wordLength = 0;
		for(int i = rowNumber; numberOfLetters > 0; i--) {
			puzzleGrid[i][columnNumber] = wordCharArray[wordLength];
			numberOfLetters--;
			wordLength++;	
		}	
	}
	
	/** method that helps to fill the words in the slots horizontally in the puzzle **/
	private void fillHorizontal(int columnNumber, int rowNumber, int numberOfLetters, String word,  char[][] puzzleGrid) {
		char[] wordCharArray = word.toCharArray();
		int wordLength = 0;
		for(int i = columnNumber; numberOfLetters > 0; i++) {
			puzzleGrid[rowNumber][i] = wordCharArray[wordLength];
			numberOfLetters--;
			wordLength++;
		}	
	}

	/** method that prints the puzzle to the out put stream **/
	public void print(PrintWriter outstream) {
		String displayString = "";
		for(int i = fillInpuzzleConst.getTotalNumberOfRows() - 1; i >= 0; i--) {
			for(int j = 0; j <= fillInpuzzleConst.getTotalNumberOfColumns() - 1 ; j++) {
				if(puzzleGrid[i][j] == '*')
					puzzleGrid[i][j] = ' ';
				displayString = displayString + puzzleGrid[i][j] + " ";
			}	
			displayString = displayString + "\n";
		}		
		outstream.print(displayString);
		outstream.close();
	}
	
	/** method that returns the number of choices made in the puzzle **/
	public int choices() {
		return numberOfChoices;
	}
}
