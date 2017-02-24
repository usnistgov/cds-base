package gov.nist.healthcare.cds.domain;


import gov.nist.healthcare.cds.domain.wrapper.EventsReport;
import gov.nist.healthcare.cds.domain.wrapper.ForecastsReport;


public class ValidationReport {
	
	private ForecastsReport forecasts;
	private EventsReport events;
	
	public ForecastsReport getForecasts() {
		return forecasts;
	}
	public void setForecasts(ForecastsReport forecasts) {
		this.forecasts = forecasts;
	}
	public EventsReport getEvents() {
		return events;
	}
	public void setEvents(EventsReport events) {
		this.events = events;
	}

}
