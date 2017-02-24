package gov.nist.healthcare.cds.service;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.ValidationReport;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;

public interface ValidationService {

	ValidationReport validate(EngineResponse response, TestCase tc);
	
}
