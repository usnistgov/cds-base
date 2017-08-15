package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;

import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.enumeration.SerieStatus;

public class ForecastRequirement {

	private ExpectedForecast expForecast;
	private Date earliest;
	private Date recommended;
	private Date pastDue;
	private Date complete;
	private SerieStatus seriesStatus;
	
	public ExpectedForecast getExpForecast() {
		return expForecast;
	}
	public void setExpForecast(ExpectedForecast expForecast) {
		this.expForecast = expForecast;
	}
	public Date getEarliest() {
		return earliest;
	}
	public void setEarliest(Date earliest) {
		this.earliest = earliest;
	}
	public Date getRecommended() {
		return recommended;
	}
	public void setRecommended(Date recommended) {
		this.recommended = recommended;
	}
	public Date getPastDue() {
		return pastDue;
	}
	public void setPastDue(Date pastDue) {
		this.pastDue = pastDue;
	}
	public Date getComplete() {
		return complete;
	}
	public void setComplete(Date complete) {
		this.complete = complete;
	}
	public SerieStatus getSeriesStatus() {
		return seriesStatus;
	}
	public void setSeriesStatus(SerieStatus seriesStatus) {
		this.seriesStatus = seriesStatus;
	}
	
	
	
}
