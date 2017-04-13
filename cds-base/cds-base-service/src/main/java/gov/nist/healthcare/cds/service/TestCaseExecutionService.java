package gov.nist.healthcare.cds.service;

import java.util.Date;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.Report;

public interface TestCaseExecutionService {

	public Report execute(SoftwareConfig conf, TestCase tc, Date date) throws UnresolvableDate, ConnectionException;
	
}
