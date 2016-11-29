package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TestCase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648219993919989094L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Patient patient;
	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false, orphanRemoval = true)
	private MetaData metaData;
	@OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false, orphanRemoval = true)
	private Date evalDate;
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Set<Event> events;
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private Set<ExpectedForecast> forecast;
	@JsonIgnore
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private TestPlan testPlan;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Set<Event> getEvents() {
		return events;
	}
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	public void addEvent(Event e){
		if(events == null)
			events = new HashSet<Event>();
		events.add(e);
	}
	public Set<ExpectedForecast> getForecast() {
		return forecast;
	}
	public void setForecast(Set<ExpectedForecast> forecast) {
		this.forecast = forecast;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	public TestPlan getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(TestPlan testPlan) {
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
	
	
	
	
}
