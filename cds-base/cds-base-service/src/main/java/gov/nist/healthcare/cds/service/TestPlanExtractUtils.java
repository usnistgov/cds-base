package gov.nist.healthcare.cds.service;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;

public interface TestPlanExtractUtils {
	
	public List<TestCase> extract(TestPlan tp, String[] ids);

}
