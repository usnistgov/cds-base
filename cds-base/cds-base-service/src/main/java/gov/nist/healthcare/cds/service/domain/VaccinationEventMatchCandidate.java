package gov.nist.healthcare.cds.service.domain;

import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;

public class VaccinationEventMatchCandidate extends ResultMatch<ResponseVaccinationEvent>{

	public VaccinationEventMatchCandidate(ResponseVaccinationEvent candidate, long canfidence) {
		super();
		this.candidate = candidate;
		this.confidence = canfidence;
		this.expectation = 0;
		this.completeness = 0;
	}

}
