package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.domain.wrapper.ModelError;
import gov.nist.healthcare.cds.enumeration.DateType;
import gov.nist.healthcare.cds.enumeration.WorkflowTag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class TestCase extends Entity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3584107174102855533L;
	
	@NotBlank(message = "TestCase name is required and can't be empty")
	private String name;
	private String uid;
	private String description;
	private String evaluationType;
	private String forecastType;
	@NotNull(message = "Dates Type is required and can't be empty")
	private DateType dateType;
	@NotNull(message = "Patient information are required")
	@Valid
	private Patient patient;
	@NotNull(message = "Assessment Date is required")
	@Valid
	private Date evalDate;
	@Valid
	private List<Event> events;
	@Valid
	private List<ExpectedForecast> forecast;
	private String testPlan;
	@JsonProperty("group")
	private String groupTag;
	private List<Tag> tags;
	private WorkflowTag workflowTag;
	private boolean runnable;
	private List<ModelError> errors;
	private String user;
	
	public TestCase(){
		this.runnable = true;
	}
	
	public List<ModelError> getErrors() {
		return errors;
	}
	
	public String getEvaluationType() {
		return evaluationType;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public void setEvaluationType(String evaluationType) {
		this.evaluationType = evaluationType;
	}

	public String getForecastType() {
		return forecastType;
	}

	public void setForecastType(String forecastType) {
		this.forecastType = forecastType;
	}

	public void setErrors(List<ModelError> errors) {
		this.errors = errors;
	}

	public boolean isRunnable() {
		return runnable;
	}

	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}

	public String getGroupTag() {
		return groupTag;
	}

	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getEvalDate() {
		return evalDate;
	}

	public void setEvalDate(Date evalDate) {
		this.evalDate = evalDate;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	public void addEvent(Event e){
		if(events == null)
			events = new ArrayList<Event>();
		events.add(e);
	}

	public List<ExpectedForecast> getForecast() {
		if(forecast == null){
			return new ArrayList<ExpectedForecast>();
		}
		return forecast;
	}

	public void setForecast(List<ExpectedForecast> forecast) {
		this.forecast = forecast;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	public DateType getDateType() {
		return dateType;
	}

	public void setDateType(DateType dateType) {
		this.dateType = dateType;
	}

	public WorkflowTag getWorkflowTag() {
		return workflowTag;
	}

	public void setWorkflowTag(WorkflowTag workflowTag) {
		this.workflowTag = workflowTag;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		TestCase that = (TestCase) obj;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this);
	}
}
