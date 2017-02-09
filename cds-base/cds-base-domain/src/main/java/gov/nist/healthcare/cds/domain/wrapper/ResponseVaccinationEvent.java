package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.Event;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ResponseVaccinationEvent extends Event {

	private int doseNumber = 1;
	private VaccineRef administred;
	private Set<ActualEvaluation> evaluations;
	
	public int getDoseNumber() {
		return doseNumber;
	}
	public void setDoseNumber(int doseNumber) {
		this.doseNumber = doseNumber;
	}
	
	public VaccineRef getAdministred() {
		return administred;
	}
	public void setAdministred(VaccineRef administred) {
		this.administred = administred;
	}
	public Set<ActualEvaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(Set<ActualEvaluation> evaluations) {
		this.evaluations = evaluations;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
