package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedForecast;

import java.util.ArrayList;
import java.util.List;

public class ForecastsReport {
	
	private List<ForecastPair> pairs;
	private List<ExpectedForecast> unmatched;
	
	public List<ForecastPair> getPairs() {
		if(pairs == null){
			pairs = new ArrayList<ForecastPair>();
		}
		return pairs;
	}
	public void setPairs(List<ForecastPair> pairs) {
		this.pairs = pairs;
	}
	public List<ExpectedForecast> getUnmatched() {
		if(unmatched == null){
			unmatched = new ArrayList<ExpectedForecast>();
		}
		return unmatched;
	}
	public void setUnmatched(List<ExpectedForecast> unmatched) {
		this.unmatched = unmatched;
	}
}
