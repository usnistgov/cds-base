package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TestExecution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707759373751607645L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private TestCase testCase;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private FixedDate executionDate;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private FixedDate evalDate;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private FixedDate patientDOB;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private SoftwareConfig software;
	private String textResponse;
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private Set<ExpectedForecast> forecasts;
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private Set<VaccinationEvent> events;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TestCase getTestCase() {
		return testCase;
	}
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
	public FixedDate getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(FixedDate executionDate) {
		this.executionDate = executionDate;
	}
	public FixedDate getEvalDate() {
		return evalDate;
	}
	public void setEvalDate(FixedDate evalDate) {
		this.evalDate = evalDate;
	}
	public FixedDate getPatientDOB() {
		return patientDOB;
	}
	public void setPatientDOB(FixedDate patientDOB) {
		this.patientDOB = patientDOB;
	}
	public SoftwareConfig getSoftware() {
		return software;
	}
	public void setSoftware(SoftwareConfig software) {
		this.software = software;
	}
	public String getTextResponse() {
		return textResponse;
	}
	public void setTextResponse(String textResponse) {
		this.textResponse = textResponse;
	}
	public Set<ExpectedForecast> getForecasts() {
		return forecasts;
	}
	public void setForecasts(Set<ExpectedForecast> forecasts) {
		this.forecasts = forecasts;
	}
	public Set<VaccinationEvent> getEvents() {
		return events;
	}
	public void setEvents(Set<VaccinationEvent> events) {
		this.events = events;
	}
	
	
}
