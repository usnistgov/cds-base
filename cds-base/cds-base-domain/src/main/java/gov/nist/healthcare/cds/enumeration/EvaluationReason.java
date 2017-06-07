package gov.nist.healthcare.cds.enumeration;

public enum EvaluationReason {
	A("Vaccine Dose Administered was administered after the Lot Number Expiration Date."), 
	B("Vaccine Dose Administered was deemed to be ineffective or sub-potent. (e.g. recalled, cold-chain break, partially administered)."),
	C("Vaccine Dose Administered was administered at too young of an age."),
	D("Vaccine Dose Administered was administered too soon following a previous dose."),
	E("Vaccine Dose Administered was administered too close to another vaccine (e.g. live virus conflict)."),
	F("Vaccine Dose Administered amount was less than the recommended amount."),
	G("Vaccine Product was administered outside of an acceptable age range."),
	H("Vaccine Dose Administered was administered at too old of an age."),
	I("Patient has already completed the vaccination schedule.");
	
	private String details;
	
	private EvaluationReason(String d){
		this.details = d;
	}
	
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
