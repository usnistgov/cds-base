package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

import java.util.List;

public class ImportResult {
	private List<ErrorModel> errors;
	private boolean status;
	private TestPlan testPlan;
	
	public List<ErrorModel> getErrors() {
		return errors;
	}
	
	public TestPlan getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}

	public void setErrors(List<ErrorModel> errors) {
		this.errors = errors;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
