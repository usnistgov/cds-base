package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.domain.wrapper.ModelError;
import gov.nist.healthcare.cds.enumeration.DateType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class TestCase implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@NotBlank(message = "TestCase name is required and can't be empty")
	private String name;
	private String uid;
	private String description;
	@NotNull(message = "Dates Type is required and can't be empty")
	private DateType dateType;
	@NotNull(message = "Patient information are required")
	@Valid
	private Patient patient;
	private MetaData metaData;
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
	private boolean runnable;
	private List<ModelError> errors;
	
	public List<ModelError> getErrors() {
		return errors;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
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
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	public String getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
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
	public DateType getDateType() {
		return dateType;
	}
	public void setDateType(DateType dateType) {
		this.dateType = dateType;
	}
	
	
	
	
}
