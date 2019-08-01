package gov.nist.healthcare.cds.service.domain;

import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;

public class EvaluationMatchCandidate extends ResultMatch<ActualEvaluation> {

	public EvaluationMatchCandidate(ActualEvaluation candidate, long confidence, EvaluationStatus status) {
		super();
		this.candidate = candidate;
		this.confidence = confidence;
		this.completeness = candidate.getStatus() != null ? 1 : 0;
		this.expectation  = candidate.getStatus().equals(status) ? 0 : 1;
	}
	
}
