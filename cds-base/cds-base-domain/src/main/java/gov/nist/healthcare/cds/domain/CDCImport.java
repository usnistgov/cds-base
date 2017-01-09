package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.domain.xml.ErrorModel;

import java.util.ArrayList;
import java.util.List;

public class CDCImport {
	private List<TestCase> testcases;
	private List<ErrorModel> exceptions;
	
	public List<TestCase> getTestcases() {
		if(testcases == null){
			testcases = new ArrayList<TestCase>();
		}
		return testcases;
	}
	public void setTestcases(List<TestCase> testcases) {
		this.testcases = testcases;
	}
	public List<ErrorModel> getExceptions() {
		if(exceptions == null){
			exceptions = new ArrayList<ErrorModel>();
		}
		return exceptions;
	}
	public void setExceptions(List<ErrorModel> exceptions) {
		this.exceptions = exceptions;
	}
}
