package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineMapping;

public interface CodeRemapService {
    void remap(Vaccine source, Vaccine target);
}
