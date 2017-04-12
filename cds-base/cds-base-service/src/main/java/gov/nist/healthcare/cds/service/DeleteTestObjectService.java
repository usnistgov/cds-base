package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.exception.IllegalDelete;

public interface DeleteTestObjectService {

	public abstract boolean deleteTestCase(String tc, String user) throws IllegalDelete;
	public abstract boolean deleteTestPlan(String tc, String user) throws IllegalDelete;
	public abstract boolean deleteTestCaseGroup(String tc, String user) throws IllegalDelete;
	public abstract boolean deleteReport(String report, String user) throws IllegalDelete;
	
}
