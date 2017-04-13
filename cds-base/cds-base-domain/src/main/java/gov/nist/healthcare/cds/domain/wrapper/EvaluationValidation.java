package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;

public class EvaluationValidation {
	
	private ExpectedEvaluation expEval;
	private EvaluationCriterion validation;
	
	
	public EvaluationValidation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EvaluationValidation(ExpectedEvaluation expEval,
			EvaluationCriterion status) {
		super();
		this.expEval = expEval;
		this.validation = status;
	}
	public ExpectedEvaluation getExpEval() {
		return expEval;
	}
	public void setExpEval(ExpectedEvaluation expEval) {
		this.expEval = expEval;
	}
	public EvaluationCriterion getValidation() {
		return validation;
	}
	public void setValidation(EvaluationCriterion validation) {
		this.validation = validation;
	}

	
	
	
}
