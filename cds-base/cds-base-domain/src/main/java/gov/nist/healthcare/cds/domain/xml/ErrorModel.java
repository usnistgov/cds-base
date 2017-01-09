package gov.nist.healthcare.cds.domain.xml;

import org.xml.sax.SAXParseException;

public class ErrorModel {
	private int line;
	private int column;
	private String message;
	
	
	
	public ErrorModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ErrorModel(int line, int column, String message) {
		super();
		this.line = line;
		this.column = column;
		this.message = message;
	}

	public ErrorModel(SAXParseException e) {
		super();
		this.line = e.getLineNumber();
		this.column = e.getColumnNumber();
		String msg = e.getMessage();
		this.message = msg.substring(msg.indexOf(":") + 2);
	}

	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
