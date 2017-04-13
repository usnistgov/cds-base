package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.enumeration.ValidationCriterion;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;

public interface ValidationConfigService {

	ValidationStatus failed(ValidationCriterion criterion);
	
}
