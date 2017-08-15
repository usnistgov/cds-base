package gov.nist.healthcare.cds.domain;

public class ExecutionFault {

	private String testCaseId;
	private Exception exception;
	
	
	public ExecutionFault() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExecutionFault(String testCaseId, Exception exception) {
		super();
		this.testCaseId = testCaseId;
		this.exception = exception;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

	
	
}
