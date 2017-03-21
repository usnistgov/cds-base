package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;

import gov.nist.healthcare.cds.domain.Forecast;
import gov.nist.healthcare.cds.enumeration.SerieStatus;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ActualForecast extends Forecast {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VaccineRef vaccine;
	private Date earliest;
	private Date recommended;
	private Date pastDue;
	private Date complete;
	private SerieStatus serieStatus;
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	public VaccineRef getVaccine() {
		return vaccine;
	}

	public void setVaccine(VaccineRef vaccine) {
		this.vaccine = vaccine;
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
	public SerieStatus getSerieStatus() {
		return serieStatus;
	}
	public void setSerieStatus(SerieStatus serieStatus) {
		this.serieStatus = serieStatus;
	}
	
	
}
