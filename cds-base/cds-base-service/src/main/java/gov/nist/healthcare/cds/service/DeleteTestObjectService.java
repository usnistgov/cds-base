package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;

public interface DeleteTestObjectService {

	public boolean deleteTestCase(TestCase tc);
	public boolean deleteTestPlan(TestPlan tp);
	public boolean deleteTestCaseGroup(TestPlan tp, String tcg);
	
}
