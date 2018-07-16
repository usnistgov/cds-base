package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.enumeration.SerieStatus;

public class ForecastRequirement {

	private ExpectedForecast expForecast;
	private FixedDate earliest;
	private FixedDate recommended;
	private FixedDate pastDue;
	private FixedDate complete;
	private SerieStatus seriesStatus;
	
	public ExpectedForecast getExpForecast() {
		return expForecast;
	}
	public void setExpForecast(ExpectedForecast expForecast) {
		this.expForecast = expForecast;
	}
	public FixedDate getEarliest() {
		return earliest;
	}
	public void setEarliest(FixedDate earliest) {
		this.earliest = earliest;
	}
	public FixedDate getRecommended() {
		return recommended;
	}
	public void setRecommended(FixedDate recommended) {
		this.recommended = recommended;
	}
	public FixedDate getPastDue() {
		return pastDue;
	}
	public void setPastDue(FixedDate pastDue) {
		this.pastDue = pastDue;
	}
	public FixedDate getComplete() {
		return complete;
	}
	public void setComplete(FixedDate complete) {
		this.complete = complete;
	}
	public SerieStatus getSeriesStatus() {
		return seriesStatus;
	}
	public void setSeriesStatus(SerieStatus seriesStatus) {
		this.seriesStatus = seriesStatus;
	}
	
	
	
}
