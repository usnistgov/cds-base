package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;


public class EvaluationCriterion extends Criterion {

	private EvaluationStatus value;

	
	public EvaluationCriterion() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public EvaluationCriterion(ValidationStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public EvaluationCriterion(ValidationStatus f, EvaluationStatus status) {
		super(f);
		this.value = status;
		// TODO Auto-generated constructor stub
	}


	public EvaluationStatus getValue() {
		return value;
	}

	public void setValue(EvaluationStatus value) {
		this.value = value;
	}

	
	
}
