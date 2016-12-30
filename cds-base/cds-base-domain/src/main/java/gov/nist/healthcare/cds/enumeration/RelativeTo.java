package gov.nist.healthcare.cds.enumeration;

public enum RelativeTo {
	DOB("Date Of Birth"), EVALDATE("Assessment Date"), TODAY("Today");
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
