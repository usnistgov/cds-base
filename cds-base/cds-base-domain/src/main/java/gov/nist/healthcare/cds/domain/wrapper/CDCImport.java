package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

import java.util.ArrayList;
import java.util.List;

public class CDCImport {
	private List<TestCaseGroup> testcases;
	private List<ErrorModel> exceptions;
	
	public List<TestCaseGroup> getTestcases() {
		if(testcases == null){
			testcases = new ArrayList<TestCaseGroup>();
		}
		return testcases;
	}
	public void setTestcases(List<TestCaseGroup> testcases) {
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
