package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;

public class EvaluationPair {
	
	private ExpectedEvaluation expected;
	private ActualEvaluation actual;
	private boolean earliest;
	private boolean recommended;
	private boolean pastDue;
	private boolean complete;
	

	public ExpectedEvaluation getExpected() {
		return expected;
	}
	public void setExpected(ExpectedEvaluation expected) {
		this.expected = expected;
	}
	public ActualEvaluation getActual() {
		return actual;
	}
	public void setActual(ActualEvaluation actual) {
		this.actual = actual;
	}
	public boolean isEarliest() {
		return earliest;
	}
	public void setEarliest(boolean earliest) {
		this.earliest = earliest;
	}
	public boolean isRecommended() {
		return recommended;
	}
	public void setRecommended(boolean recommended) {
		this.recommended = recommended;
	}
	public boolean isPastDue() {
		return pastDue;
	}
	public void setPastDue(boolean pastDue) {
		this.pastDue = pastDue;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	
}
