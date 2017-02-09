package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;

public interface TestRunnerService {

	EngineResponse run(SoftwareConfig config, TestCase tc);
	
}
