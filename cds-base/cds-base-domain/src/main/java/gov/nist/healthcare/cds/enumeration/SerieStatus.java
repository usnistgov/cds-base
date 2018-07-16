package gov.nist.healthcare.cds.enumeration;


public enum SerieStatus {
	A("Assumed complete or immune",true),
	C("Complete",false),
	D("Due",true),
	F("Finished",false),
	G("Aged out",false),
	
	I("Immune",false),
	L("Due later",true),
	N("Not complete",true),
	O("Overdue", true),
	
	S("Complete for season", false),
	V("Consider", false),
	W("Waivered", true),
	X("Contraindicated", false),
	
	Z("Recommended but not required",true),
	U("Other", false),
	R("Not Recommended",false);
	
	private String details;
	private boolean dates;
	
	private SerieStatus(String d, boolean dates){
		this.details = d;
		this.dates = dates;
	}

	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public boolean hasDates() {
		return dates;
	}
	public void setDates(boolean dates) {
		this.dates = dates;
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
		case "not recommended" : return SerieStatus.R;
		default : return SerieStatus.U;
		}
	}
}
