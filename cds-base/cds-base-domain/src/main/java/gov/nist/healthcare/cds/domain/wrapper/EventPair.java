package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.VaccinationEvent;

public class EventPair {
	
	private VaccinationEvent event;
	private List<EvaluationPair> pairs;
	private List<ExpectedEvaluation> unmatched;
	
	public VaccinationEvent getEvent() {
		return event;
	}
	public void setEvent(VaccinationEvent event) {
		this.event = event;
	}
	public List<EvaluationPair> getPairs() {
		if(pairs == null){
			pairs = new ArrayList<EvaluationPair>();
		}
		return pairs;
	}
	public void setPairs(List<EvaluationPair> pairs) {
		this.pairs = pairs;
	}
	public List<ExpectedEvaluation> getUnmatched() {
		if(unmatched == null){
			unmatched = new ArrayList<ExpectedEvaluation>();
		}
		return unmatched;
	}
	public void setUnmatched(List<ExpectedEvaluation> unmatched) {
		this.unmatched = unmatched;
	}
	
}
