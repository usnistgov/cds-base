package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;

public interface DateService {

	public boolean same(java.util.Date d1, java.util.Date d2);
	public FixedDate fix(Date d, java.util.Date from);
	public FixedDate evaluationDate(TestCase tc, java.util.Date today) throws UnresolvableDate;
	FixedDate fix(Date d, FixedDate dob, FixedDate eval, java.util.Date today);
	
}
