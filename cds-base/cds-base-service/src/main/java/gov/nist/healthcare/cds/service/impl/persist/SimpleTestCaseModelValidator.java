package gov.nist.healthcare.cds.service.impl.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.ModelError;
import gov.nist.healthcare.cds.service.ErrorPathCleaner;
import gov.nist.healthcare.cds.service.ValidateTestCase;

@Service
public class SimpleTestCaseModelValidator implements ValidateTestCase {

	@Autowired
	private ErrorPathCleaner pathClean;
	
	@Override
	public void validate(TestCase tc) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<TestCase>> violations = validator.validate(tc);
		
		
		if(violations.size() == 0){
			tc.setRunnable(true);
			tc.setErrors(null);
		}
		else {
			tc.setRunnable(false);
			List<ModelError> errors = new ArrayList<ModelError>();
			for(ConstraintViolation<TestCase> v : violations){
				errors.add(new ModelError(pathClean.errorPath(v.getPropertyPath().toString()), v.getMessage()));
			}
			tc.setErrors(errors);
		}		
	}

}
