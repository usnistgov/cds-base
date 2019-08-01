package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;

public interface VaccineMatcherService {

	public int match(VaccineRef ref, Injection i, StringBuilder logs);
}
