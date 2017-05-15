package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class EngineResponse {
	
	private String response;
	private String request;
	private List<ActualForecast> forecasts;
	private List<ResponseVaccinationEvent> events;
	
	
	public EngineResponse() {
		super();
		forecasts = new ArrayList<ActualForecast>();
		events = new ArrayList<ResponseVaccinationEvent>();
	}
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
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
	
	
}
