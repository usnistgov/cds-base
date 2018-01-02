package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.enumeration.DatePosition;

public interface DateService {

	public ResolvedDates resolveDates(TestCase tc, java.util.Date today);
	public void toFixed(TestCase tc, java.util.Date today);
	public java.util.Date fix(ResolvedDates rds, Date d);
	public boolean same(java.util.Date d1, java.util.Date d2);
//	public java.util.Date from(int years, int months, int days, DatePosition p, java.util.Date ref);
	java.util.Date from(int years, int months, int weeks, int days, DatePosition p, java.util.Date ref);

}
