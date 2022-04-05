package gov.nist.healthcare.cds.service;

import java.util.Date;
import java.util.List;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;

public interface TestCaseExecutionService {

	Report validateResponse(List<ActualForecast> forecasts, List<ResponseVaccinationEvent> events, TestCase tc, Date evaluationDate);

	public Report execute(SoftwareConfig conf, TestCase tc, Date date) throws UnresolvableDate, ConnectionException;
	
}
