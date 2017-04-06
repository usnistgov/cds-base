package gov.nist.healthcare.cds.service.impl;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.service.FHIRParser;

@Service
public class SimpleFHIRParser implements FHIRParser {

	@Override
	public EngineResponse parse(String xml) {
		return new EngineResponse();
	}

}
