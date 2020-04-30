/**** Author: Satya Kumar Itekela, Banner Id: B00839907 ****/

/*** Class that holds the total number of columns, rows and words in the word puzzle ***/
public class PuzzleConstraint {

	private int totalNumberOfColumns;
	private int totalNumberOfRows;
	private int totalNumberOfWords;
	
	public PuzzleConstraint( int totalNumberOfColumns, int totalNumberOfRows, int totalNumberOfWords) {
		this.totalNumberOfColumns = totalNumberOfColumns;
		this.totalNumberOfRows = totalNumberOfRows;
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public int getTotalNumberOfRows() {
		return totalNumberOfRows;
	}

	public void setTotalNumberOfRows(int totalNumberOfRows) {
		this.totalNumberOfRows = totalNumberOfRows;
	}

	public int getTotalNumberOfColumns() {
		return totalNumberOfColumns;
	}

	public void setTotalNumberOfColumns(int totalNumberOfColumns) {
		this.totalNumberOfColumns = totalNumberOfColumns;
	}

	public int getTotalNumberOfWords() {
		return totalNumberOfWords;
	}

	public void setTotalNumberOfWords(int totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}
	
}
