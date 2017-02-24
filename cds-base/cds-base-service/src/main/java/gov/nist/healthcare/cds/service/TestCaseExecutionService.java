package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.ValidationReport;

public interface TestCaseExecutionService {

	public ValidationReport execute(SoftwareConfig conf, TestCase tc);
	
}
