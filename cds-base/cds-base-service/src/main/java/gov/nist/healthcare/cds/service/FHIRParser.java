package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;

public interface FHIRParser {

	public  EngineResponse parse(String xml) throws ConnectionException;
	
}
