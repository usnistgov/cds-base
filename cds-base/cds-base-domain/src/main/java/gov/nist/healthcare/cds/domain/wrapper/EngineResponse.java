package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import gov.nist.healthcare.cds.enumeration.EngineResponseStatus;

public class EngineResponse {

	private List<ExecutionIssue> issues;
	private String response;
	private String logs;
	private List<ActualForecast> forecasts;
	private List<ResponseVaccinationEvent> events;
	private EngineResponseStatus engineResponseStatus = null;
	
	public EngineResponse() {
		super();
		forecasts = new ArrayList<ActualForecast>();
		events = new ArrayList<ResponseVaccinationEvent>();
                issues = new ArrayList<>();
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
	public String getLogs() {
		return logs;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}
	
	public List<ExecutionIssue> getIssues() {
		return issues;
	}
	public void setIssues(List<ExecutionIssue> issues) {
		this.issues = issues;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	public EngineResponseStatus getEngineResponseStatus() {
		return engineResponseStatus;
	}
	public void setEngineResponseStatus(EngineResponseStatus engineResponseStatus) {
		this.engineResponseStatus = engineResponseStatus;
	}
	
}
