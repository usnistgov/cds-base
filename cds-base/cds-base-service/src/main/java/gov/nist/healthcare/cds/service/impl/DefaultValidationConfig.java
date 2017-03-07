package gov.nist.healthcare.cds.service.impl;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.enumeration.ValidationCriterion;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;
import gov.nist.healthcare.cds.service.ValidationConfigService;

@Service
public class DefaultValidationConfig implements ValidationConfigService {

	@Override
	public ValidationStatus failed(ValidationCriterion criterion) {
		switch(criterion){
		case DoseNumber : 
		case CompleteDate :
		case PastDueDate :
			return ValidationStatus.W;
		default :
			return ValidationStatus.F;
		}
	}

}
