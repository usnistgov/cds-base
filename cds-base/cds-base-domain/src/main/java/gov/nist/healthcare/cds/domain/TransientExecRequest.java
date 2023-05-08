package gov.nist.healthcare.cds.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class TransientExecRequest {
	
	private SoftwareConfig software;
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDate date;
	private List<String> testCases;
	
	
	
	public SoftwareConfig getSoftware() {
		return software;
	}
	public void setSoftware(SoftwareConfig software) {
		this.software = software;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public List<String> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<String> testCases) {
		this.testCases = testCases;
	}
	public void addTestCase(String id){
		if(testCases == null){
			testCases = new ArrayList<String>();
		}
		testCases.add(id);
	}
	
	
	

}
