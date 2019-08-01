package gov.nist.healthcare.cds.service.domain.matcher;

import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.service.domain.ResultMatch;

public class EvaluationMatchCandidate extends MatchCandidate<ActualEvaluation> {

	public EvaluationMatchCandidate(ActualEvaluation candidate, int confidence, EvaluationStatus status) {
		super();
		this.payload = candidate;
		this.confidence = confidence;
		if(candidate.getStatus() != null) {
			this.scores.put("EVALUATION", candidate.getStatus().equals(status) ? 0L : 1L);
			this.completeness++;
		}
	}
	
}
