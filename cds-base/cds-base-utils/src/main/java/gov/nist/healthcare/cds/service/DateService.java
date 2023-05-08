package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.enumeration.DatePosition;

import java.time.LocalDate;

public interface DateService {

	ResolvedDates resolveDates(TestCase tc, LocalDate today);
	void toFixed(TestCase tc, LocalDate today);
	LocalDate fix(ResolvedDates rds, Date d);
	boolean same(LocalDate d1, LocalDate d2);
	LocalDate from(int years, int months, int weeks, int days, DatePosition p, LocalDate ref);

}
