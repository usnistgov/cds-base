package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.VaccinationEvent;

public class VaccinationEventRequirement {

	private VaccinationEvent vEvent;
	private FixedDate dateAdministred;
	
	public VaccinationEvent getvEvent() {
		return vEvent;
	}
	public void setvEvent(VaccinationEvent vEvent) {
		this.vEvent = vEvent;
	}
	public FixedDate getDateAdministred() {
		return dateAdministred;
	}
	public void setDateAdministred(FixedDate dateAdministred) {
		this.dateAdministred = dateAdministred;
	}
	
}
