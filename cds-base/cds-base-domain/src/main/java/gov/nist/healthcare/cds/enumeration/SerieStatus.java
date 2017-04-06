package gov.nist.healthcare.cds.enumeration;

public enum SerieStatus {
	A("Assumed complete or immune"),
	C("Complete"),
	D("Due"),
	F("Finished"),
	G("Aged out"),
	
	I("Immune"),
	L("Due later"),
	N("Not complete"),
	O("Overdue"),
	
	S("Complete for season"),
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
	
	public static SerieStatus  getSerieStatus(String details) {
		String lookup = details.toLowerCase();
		switch(lookup.trim()){
		case "assumed complete or immune" : return SerieStatus.A;
		case "complete" : return SerieStatus.C;
		case "due" : return SerieStatus.D;
		case "finished" : return SerieStatus.F;
		case "aged out" : return SerieStatus.G;
		case "immune" : return SerieStatus.I;
		case "due later" : return SerieStatus.L;
		case "not complete" : return SerieStatus.N;
		case "overdue" : return SerieStatus.O;
		case "complete for season" : return SerieStatus.S;
		case "consider" : return SerieStatus.V;
		case "waivered" : return SerieStatus.W;
		case "contraindicated" : return SerieStatus.X;
		case "recommended but not required" : return SerieStatus.Z;
		default : return null;
		}
	}
}
