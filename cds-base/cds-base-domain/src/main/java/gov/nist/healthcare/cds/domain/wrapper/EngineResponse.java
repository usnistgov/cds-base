package gov.nist.healthcare.cds.domain.wrapper;

import java.util.List;

public class EngineResponse {
	
	private String response;
	private List<ActualForecast> forecasts;
	private List<ResponseVaccinationEvent> events;
	
	public List<ActualForecast> getForecasts() {
		return forecasts;
	}
	public void setForecasts(List<ActualForecast> forecasts) {
		this.forecasts = forecasts;
	}
	public List<ResponseVaccinationEvent> getEvents() {
		return events;
	}
	public void setEvents(List<ResponseVaccinationEvent> evaluatedEvents) {
		this.events = evaluatedEvents;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
