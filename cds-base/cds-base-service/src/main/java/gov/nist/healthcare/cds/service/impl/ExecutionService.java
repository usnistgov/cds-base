package gov.nist.healthcare.cds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.ValidationReport;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.TestCaseExecutionService;
import gov.nist.healthcare.cds.service.TestRunnerService;
import gov.nist.healthcare.cds.service.ValidationService;

public class ExecutionService implements TestCaseExecutionService {

	@Autowired
	private ValidationService validation;
	@Autowired
	private DateService dates;
	@Autowired
	private TestRunnerService runner;
	
	@Override
	public ValidationReport execute(SoftwareConfig conf, TestCase tc) {
		
		TestCase fixed = dates.fixDates(tc);
		EngineResponse response = runner.run(conf, fixed);
		ValidationReport report = validation.validate(response, fixed);

		return report;
	}

}
