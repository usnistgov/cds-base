package gov.nist.healthcare.cds.service;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;

public interface TestPlanFilter {

	boolean elect(TestPlan tp, String user);
	void filterTestPlan(TestPlan x);
	List<TestCase> filter(List<TestCase> tcList);
	boolean conform(TestCase tc); 
	
}
