package gov.nist.healthcare.cds.enumeration;

public enum WorkflowTag {
	SUGGESTED("Suggested"),
	DELETED("Deleted"),
	ONHOLD("On hold"),
	FINAL("Final");
	
	private String details;
	private WorkflowTag(String x){
		this.details = x;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
