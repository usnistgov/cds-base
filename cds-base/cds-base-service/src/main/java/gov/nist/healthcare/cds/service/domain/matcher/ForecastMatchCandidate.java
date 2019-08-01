package gov.nist.healthcare.cds.service.domain.matcher;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.service.domain.ResultMatch;

import java.util.Date;

public class ForecastMatchCandidate extends MatchCandidate<ActualForecast> {

	public ForecastMatchCandidate(ActualForecast candidate, int confidence, FixedDate earliestFixed, FixedDate recommendedFixed) {
		super();
		
		Date earliest = earliestFixed == null ? null : earliestFixed.asDate();
		Date recommended = recommendedFixed == null ? null : recommendedFixed.asDate();
		this.payload = candidate;
		this.confidence = confidence;

		boolean earliestDateReq = earliest != null;
		boolean recommendedDateReq = recommended != null;
		boolean earliestDateAvailable = candidate.getEarliest() != null;
		boolean recommendedDateAvailable = candidate.getRecommended() != null;

		if(earliestDateReq && earliestDateAvailable) {
			this.scores.put("EARLIEST", Math.abs(earliest.getTime() - candidate.getEarliest().getTime()));
			this.completeness++;
		}

		if(recommendedDateReq && recommendedDateAvailable) {
			this.scores.put("RECOMMENDED", Math.abs(recommended.getTime() - candidate.getRecommended().getTime()));
			this.completeness++;
		}
	}

}
