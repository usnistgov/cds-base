package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedForecast;

public class ForecastPair {
	
	private ExpectedForecast expected;
	private ActualForecast actual;
	private boolean dose;
	private boolean earliest;
	private boolean recommended;
	private boolean pastDue;
	private boolean complete;
	
	public ExpectedForecast getExpected() {
		return expected;
	}
	public void setExpected(ExpectedForecast expected) {
		this.expected = expected;
	}
	public ActualForecast getActual() {
		return actual;
	}
	public void setActual(ActualForecast actual) {
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
	public boolean isDose() {
		return dose;
	}
	public void setDose(boolean dose) {
		this.dose = dose;
	}
	
	
	
}
