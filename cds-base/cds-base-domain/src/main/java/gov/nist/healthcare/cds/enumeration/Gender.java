package gov.nist.healthcare.cds.enumeration;

public enum Gender {
	M("Male"),F("Female");
	private String details;
	private Gender(String x){
		this.details = x;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
