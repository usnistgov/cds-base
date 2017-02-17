package gov.nist.healthcare.cds.domain.wrapper;

import java.util.List;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.VaccinationEvent;

public class EvaluatedEvent {
	
	private VaccinationEvent event;
	private List<EvaluationPair> pairs;
	private List<ActualEvaluation> aUnmatched;
	private List<ExpectedEvaluation> eUnmatched;
	
	public VaccinationEvent getEvent() {
		return event;
	}
	public void setEvent(VaccinationEvent event) {
		this.event = event;
	}
	public List<EvaluationPair> getPairs() {
		return pairs;
	}
	public void setPairs(List<EvaluationPair> pairs) {
		this.pairs = pairs;
	}
	public List<ActualEvaluation> getaUnmatched() {
		return aUnmatched;
	}
	public void setaUnmatched(List<ActualEvaluation> aUnmatched) {
		this.aUnmatched = aUnmatched;
	}
	public List<ExpectedEvaluation> geteUnmatched() {
		return eUnmatched;
	}
	public void seteUnmatched(List<ExpectedEvaluation> eUnmatched) {
		this.eUnmatched = eUnmatched;
	}
}
