package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public class TransformResult {
	
	private List<TestCase> testCases;
	private List<ErrorModel> errors;
	private int totalTC;
	
	public TransformResult(){
		testCases = new ArrayList<TestCase>();
		errors = new ArrayList<ErrorModel>();
	}
	
	public boolean add(TestCase e) {
		return testCases.add(e);
	}

	public boolean add(ErrorModel e) {
		return errors.add(e);
	}


	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	public List<ErrorModel> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorModel> errors) {
		this.errors = errors;
	}

	public int getTotalTC() {
		return totalTC;
	}

	public void setTotalTC(int totalTC) {
		this.totalTC = totalTC;
	}
	
	
}
