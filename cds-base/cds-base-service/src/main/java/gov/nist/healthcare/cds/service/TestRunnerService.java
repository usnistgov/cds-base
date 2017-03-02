package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;

public interface TestRunnerService {

	EngineResponse run(SoftwareConfig config, TestCasePayLoad tc);
	
}
