package gov.nist.healthcare.cds.domain;

import java.util.List;

import gov.nist.healthcare.cds.domain.wrapper.EvaluatedEvent;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;

import javax.persistence.Entity;

public class ValidationReport {
	
	private ForecastValidation forecasts;
	private List<EvaluatedEvent> events;
	
	public ForecastValidation getForecasts() {
		return forecasts;
	}
	public void setForecasts(ForecastValidation forecasts) {
		this.forecasts = forecasts;
	}
	public List<EvaluatedEvent> getEvents() {
		return events;
	}
	public void setEvents(List<EvaluatedEvent> events) {
		this.events = events;
	}

}
