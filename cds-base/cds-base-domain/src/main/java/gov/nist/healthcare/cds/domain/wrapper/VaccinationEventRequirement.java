package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;

import gov.nist.healthcare.cds.domain.VaccinationEvent;

public class VaccinationEventRequirement {

	private VaccinationEvent vEvent;
	private Date dateAdministred;
	
	public VaccinationEvent getvEvent() {
		return vEvent;
	}
	public void setvEvent(VaccinationEvent vEvent) {
		this.vEvent = vEvent;
	}
	public Date getDateAdministred() {
		return dateAdministred;
	}
	public void setDateAdministred(Date dateAdministred) {
		this.dateAdministred = dateAdministred;
	}
	
}
