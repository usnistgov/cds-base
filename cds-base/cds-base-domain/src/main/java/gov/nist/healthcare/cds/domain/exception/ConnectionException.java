package gov.nist.healthcare.cds.domain.exception;

public class ConnectionException extends Exception {

	private String statusCode;
	private String statusText;
	
	
	public ConnectionException(String statusCode, String statusText) {
		super();
		this.statusCode = statusCode;
		this.statusText = statusText;
	}

	
	public ConnectionException() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	
}
