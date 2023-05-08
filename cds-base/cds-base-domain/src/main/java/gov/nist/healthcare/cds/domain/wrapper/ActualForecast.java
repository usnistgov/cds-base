package gov.nist.healthcare.cds.domain.wrapper;

import java.time.LocalDate;
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
	private LocalDate earliest;
	private LocalDate recommended;
	private LocalDate pastDue;
	private LocalDate complete;
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
	public LocalDate getEarliest() {
		return earliest;
	}
	public void setEarliest(LocalDate earliest) {
		this.earliest = earliest;
	}
	public LocalDate getRecommended() {
		return recommended;
	}
	public void setRecommended(LocalDate recommended) {
		this.recommended = recommended;
	}
	public LocalDate getPastDue() {
		return pastDue;
	}
	public void setPastDue(LocalDate pastDue) {
		this.pastDue = pastDue;
	}
	public LocalDate getComplete() {
		return complete;
	}
	public void setComplete(LocalDate complete) {
		this.complete = complete;
	}
	public SerieStatus getSerieStatus() {
		return serieStatus;
	}
	public void setSerieStatus(SerieStatus serieStatus) {
		this.serieStatus = serieStatus;
	}
	
	
}
