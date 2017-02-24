package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;

public class EvaluationPair {
	
	private ExpectedEvaluation expected;
	private ActualEvaluation actual;
	private boolean match;
	

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
	public boolean isMatch() {
		return match;
	}
	public void setMatch(boolean match) {
		this.match = match;
	}
	
	
	
}
