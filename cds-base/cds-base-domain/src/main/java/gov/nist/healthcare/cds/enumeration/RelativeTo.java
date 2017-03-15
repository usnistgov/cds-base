package gov.nist.healthcare.cds.enumeration;

public enum RelativeTo {
	DOB("Birth"), EVALDATE("Assessment Date"), VACCINATION("Vaccination Event");
	private String details;
	private RelativeTo(String x){
		this.details = x;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
