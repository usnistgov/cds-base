package gov.nist.healthcare.cds.enumeration;

public enum SerieStatus {
	A("Assumed complete or immune"),
	C("Complete"),
	D("Due"),
	E("Error"),
	F("Finished"),
	G("Aged out"),
	
	I("Immune"),
	L("Due later"),
	N("Not complete"),
	O("Overdue"),
	
	R("No results"),
	S("Complete for season"),
	U("Unknown"),
	V("Consider"),
	W("Waivered"),
	X("Contraindicated"),
	
	Z("Recommended but not required");
	
	private String details;
	private SerieStatus(String d){
		this.details = d;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
