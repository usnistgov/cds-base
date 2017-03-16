package gov.nist.healthcare.cds.service;

import java.util.List;
import java.util.Set;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.RelativeDate;
import gov.nist.healthcare.cds.domain.RelativeDateRule;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.enumeration.DatePosition;

public interface DateService {

	public ResolvedDates resolveDates(TestCase tc, java.util.Date today);
	public java.util.Date fix(ResolvedDates rds, Date d);
	public boolean same(java.util.Date d1, java.util.Date d2);
	public java.util.Date from(int years, int months, int days, DatePosition p, java.util.Date ref);

}
