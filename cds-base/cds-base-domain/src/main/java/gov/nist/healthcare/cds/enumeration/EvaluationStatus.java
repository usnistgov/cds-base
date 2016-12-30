package gov.nist.healthcare.cds.enumeration;

public enum EvaluationStatus {
	VALID("Valid Vaccination"), 
	INVALID("Invalid Vaccination"),
	EXTRANEOUS("Extraneous"),
	SUBSTANDARD("Sub-standard");
	
	private String details;
	private EvaluationStatus(String d){
		this.details = d;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
