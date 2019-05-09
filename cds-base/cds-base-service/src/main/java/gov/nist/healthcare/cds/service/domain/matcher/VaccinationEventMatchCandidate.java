package gov.nist.healthcare.cds.service.domain.matcher;

import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;
import gov.nist.healthcare.cds.service.domain.ResultMatch;

public class VaccinationEventMatchCandidate extends MatchCandidate<ResponseVaccinationEvent>{

	public VaccinationEventMatchCandidate(ResponseVaccinationEvent candidate, int confidence) {
		super();
		this.payload = candidate;
		this.confidence = confidence;
		this.completeness = 0;
	}

}
