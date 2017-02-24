package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.TestCase;

public interface DateService {

	public TestCase fixDates(TestCase tc);
	public boolean same(Date d1, Date d2);
	
}
