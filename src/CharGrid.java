/**** Author: Satya Kumar Itekela, Banner Id: B00839907 ****/

/*** class that stores the each character in the puzzle grid ***/

public class CharGrid {
	
	private int columnNumber;
	private int rowNumber;
	
	public CharGrid(int columnNumber, int rowNumber) {
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}
	
	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public int getRowNumber() {
		return rowNumber;
	}
	
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	
}
