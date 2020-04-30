/**** Author: Satya Kumar Itekela, Banner Id: B00839907 ****/

import java.util.ArrayList;
import java.util.HashMap;

/**** class that stores the word constraints of the puzzle ****/
public class PuzzleWordConstraint {
	
	private int wordKey;
	private int columnNumber;
	private int rowNumber;
	private int totalNumberOfLetters;
	private String wordDirection;
	
	/**** data structures that stores the path, intersection slots and grids of the puzzle ****/
	private ArrayList<CharGrid> SlotPath=new ArrayList<CharGrid>();
	private ArrayList<PuzzleWordConstraint> intersectedSlots = new ArrayList<PuzzleWordConstraint>();
	private ArrayList<String> possibleWords = new ArrayList<String>();
	private HashMap<PuzzleWordConstraint, CharGrid> intesectionGrids = new HashMap<PuzzleWordConstraint, CharGrid>();
	private HashMap<Integer, Integer> intesectionLength = new HashMap<Integer, Integer>();

	public PuzzleWordConstraint(int wordKey, int columnNumber, int rowNumber, int totalNumberOfLetters,	String wordDirection) {
		this.wordKey = wordKey;
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
		this.totalNumberOfLetters = totalNumberOfLetters;
		this.wordDirection = wordDirection;
		slotPath();
	}

	public int getWordKey() {
		return wordKey;
	}

	public void setWordKey(int wordKey) {
		this.wordKey = wordKey;
	}

	public int getRowNumber() {
		return rowNumber;
	}
	
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	public int getColumnNumber() {
		return columnNumber;
	}
	
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public int getTotalNumberOfLetters() {
		return totalNumberOfLetters;
	}
	
	public void setTotalNumberOfLetters(int totalNumberOfLetters) {
		this.totalNumberOfLetters = totalNumberOfLetters;
	}
	
	public String getWordDirection() {
		return wordDirection;
	}
	
	public void setWordDirection(String wordDirection) {
		this.wordDirection = wordDirection;
	}
	
	public ArrayList<CharGrid> getSlotPath() {
		return SlotPath;
	}

	public void setSlotPath(ArrayList<CharGrid> slotPath) {
		SlotPath = slotPath;
	}

	public ArrayList<PuzzleWordConstraint> getIntersectedSlots() {
		return intersectedSlots;
	}

	public void setIntersectedSlots(ArrayList<PuzzleWordConstraint> intersectedSlots) {
		this.intersectedSlots = intersectedSlots;
	}

	public ArrayList<String> getPossibleWords() {
		return possibleWords;
	}

	public void setPossibleWords(ArrayList<String> possibleWords) {
		this.possibleWords = possibleWords;
	}

	/** method that stores the path of the each word slot in the puzzle **/
	public void slotPath() {
		int totalNumberOfLetters = getTotalNumberOfLetters();
		if(getWordDirection().equalsIgnoreCase("h")) {
			for(int i = columnNumber; totalNumberOfLetters > 0; i++) {
				CharGrid ch = new CharGrid(i, getRowNumber());
				SlotPath.add(ch);
				totalNumberOfLetters--;
			}			
		}
		else if(getWordDirection().equalsIgnoreCase("v")){
			for(int i = rowNumber; totalNumberOfLetters > 0; i--) {
				CharGrid ch = new CharGrid(getColumnNumber(), i);
				SlotPath.add(ch);
				totalNumberOfLetters--;
			}
		}
	}

	public HashMap<PuzzleWordConstraint, CharGrid> getIntesectionGrids() {
		return intesectionGrids;
	}

	public void setIntesectionGrids(HashMap<PuzzleWordConstraint, CharGrid> intesectionGrids) {
		this.intesectionGrids = intesectionGrids;
	}

	public HashMap<Integer, Integer> getIntesectionLength() {
		return intesectionLength;
	}

	public void setIntesectionLength(HashMap<Integer, Integer> intesectionLength) {
		this.intesectionLength = intesectionLength;
	}	
	
}
