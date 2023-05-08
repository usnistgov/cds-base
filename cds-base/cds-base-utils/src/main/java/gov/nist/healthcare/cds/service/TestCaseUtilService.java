package gov.nist.healthcare.cds.service;


import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;

import java.time.LocalDate;

public interface TestCaseUtilService {

    TestCasePayLoad getTestCasePayload(TestCase testCase, LocalDate evaluationDate) throws UnresolvableDate;

    TestCasePayLoad getTestCasePayload(TestCase tc, ResolvedDates rds) throws UnresolvableDate;
}
