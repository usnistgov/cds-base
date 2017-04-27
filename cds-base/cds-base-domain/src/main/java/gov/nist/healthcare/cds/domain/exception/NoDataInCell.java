package gov.nist.healthcare.cds.domain.exception;

public class NoDataInCell extends Exception {

	private int cell;

	
	public NoDataInCell(int cell) {
		super();
		this.cell = cell;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}
	
	
}
