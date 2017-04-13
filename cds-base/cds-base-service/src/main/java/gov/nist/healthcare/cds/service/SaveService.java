package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.IllegalSave;

public interface SaveService {

	public TestCase saveTC(TestCase testCase, String user) throws IllegalSave;
	public TestPlan saveTP(TestPlan testPlan, String user) throws IllegalSave;
	public TestCaseGroup saveTG(TestCaseGroup testCaseGroup, String user) throws IllegalSave;
	
}
