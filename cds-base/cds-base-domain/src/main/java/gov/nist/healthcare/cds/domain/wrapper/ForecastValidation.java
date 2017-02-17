package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedForecast;

import java.util.List;

public class ForecastValidation {
	private List<ForecastPair> fpairs;
	private List<ExpectedForecast> eUnmatched;
	private List<ActualForecast> aUnmatched;
	
	public List<ForecastPair> getFpairs() {
		return fpairs;
	}
	public void setFpairs(List<ForecastPair> fpairs) {
		this.fpairs = fpairs;
	}
	public List<ExpectedForecast> geteUnmatched() {
		return eUnmatched;
	}
	public void seteUnmatched(List<ExpectedForecast> eUnmatched) {
		this.eUnmatched = eUnmatched;
	}
	public List<ActualForecast> getaUnmatched() {
		return aUnmatched;
	}
	public void setaUnmatched(List<ActualForecast> aUnmatched) {
		this.aUnmatched = aUnmatched;
	}
	
}
