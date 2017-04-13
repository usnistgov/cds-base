package gov.nist.healthcare.cds.domain.wrapper;


import gov.nist.healthcare.cds.domain.Entity;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.ValidationCriterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Report extends Entity implements Serializable {

	private String tc;
	private TestCaseInformation tcInfo;
	private Gender gender;
	private java.util.Date executionDate;
	private SoftwareConfig softwareConfig;
	private java.util.Date evaluationDate;
	private java.util.Date dob;
	private List<VaccinationEventValidation> veValidation;
	private List<ForecastValidation> fcValidation;
	private Map<ValidationCriterion,Boolean> failures;
	private String response;
	private ResultCounts events;
	private ResultCounts forecasts;
	
	
	
	public Report() {
		super();
		failures = new HashMap<ValidationCriterion,Boolean>();
	}



	public List<ForecastValidation> getFcValidation() {
		if(fcValidation == null){
			fcValidation = new ArrayList<>();
		}
		return fcValidation;
	}
	
	
	
	public Boolean put(ValidationCriterion key, Boolean value) {
		return failures.put(key, value);
	}



	public Map<ValidationCriterion, Boolean> getFailures() {
		return failures;
	}



	public void setFailures(Map<ValidationCriterion, Boolean> failures) {
		this.failures = failures;
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
	public String getTc() {
		return tc;
	}
	public void setTc(String tc) {
		this.tc = tc;
	}	
	public java.util.Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(java.util.Date executionDate) {
		this.executionDate = executionDate;
	}
	public SoftwareConfig getSoftwareConfig() {
		return softwareConfig;
	}
	public void setSoftwareConfig(SoftwareConfig softwareConfig) {
		this.softwareConfig = softwareConfig;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public TestCaseInformation getTcInfo() {
		return tcInfo;
	}

	public void setTcInfo(TestCaseInformation tcInfo) {
		this.tcInfo = tcInfo;
	}

}
