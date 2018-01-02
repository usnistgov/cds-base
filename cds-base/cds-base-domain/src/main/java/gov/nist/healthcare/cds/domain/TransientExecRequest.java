package gov.nist.healthcare.cds.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class TransientExecRequest {
	
	private SoftwareConfig software;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;
	private List<String> testCases;
	
	
	
	public SoftwareConfig getSoftware() {
		return software;
	}
	public void setSoftware(SoftwareConfig software) {
		this.software = software;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
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
