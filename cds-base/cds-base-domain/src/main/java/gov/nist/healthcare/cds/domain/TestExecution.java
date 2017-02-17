package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
//	@OneToOne(cascade = CascadeType.ALL, optional = false)
//	private ValidationReport report;

	
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
//	public ValidationReport getReport() {
//		return report;
//	}
//	public void setReport(ValidationReport report) {
//		this.report = report;
//	}

}
