package gov.nist.healthcare.cds.domain.wrapper;


import gov.nist.healthcare.cds.domain.TestCase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Report implements Serializable {

	private TestCase tc;
	private java.util.Date evaluationDate;
	private java.util.Date dob;
	private List<VaccinationEventValidation> veValidation;
	private List<ForecastValidation> fcValidation;
	private String response;
	private ResultCounts events;
	private ResultCounts forecasts;
	
	public List<ForecastValidation> getFcValidation() {
		if(fcValidation == null){
			fcValidation = new ArrayList<>();
		}
		return fcValidation;
	}
	public void setFcValidation(List<ForecastValidation> fValidation) {
		this.fcValidation = fValidation;
	}
	public List<VaccinationEventValidation> getVeValidation() {
		if(veValidation == null){
			veValidation = new ArrayList<>();
		}
		return veValidation;
	}
	public void setVeValidation(List<VaccinationEventValidation> veValidation) {
		this.veValidation = veValidation;
	}
	public ResultCounts getEvents() {
		return events;
	}
	public void setEvents(ResultCounts events) {
		this.events = events;
	}
	public ResultCounts getForecasts() {
		return forecasts;
	}
	public void setForecasts(ResultCounts forecasts) {
		this.forecasts = forecasts;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public TestCase getTc() {
		return tc;
	}
	public void setTc(TestCase tc) {
		this.tc = tc;
	}
	public java.util.Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(java.util.Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public java.util.Date getDob() {
		return dob;
	}
	public void setDob(java.util.Date dob) {
		this.dob = dob;
	}
	
	
}
