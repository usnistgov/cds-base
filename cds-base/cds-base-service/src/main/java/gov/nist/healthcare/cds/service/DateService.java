package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.RelativeDate;

public interface DateService {

	public FixedDate interpretDate(FixedDate context, RelativeDate date);
	
}
