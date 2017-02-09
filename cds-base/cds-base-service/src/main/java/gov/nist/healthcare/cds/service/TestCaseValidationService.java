package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.ValidationResponse;

public interface TestCaseValidationService {

	ValidationResponse validate(SoftwareConfig config, TestCase tc);
	
}
