package gov.nist.healthcare.cds.service.domain.matcher;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.service.domain.ResultMatch;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class ForecastMatchCandidate extends MatchCandidate<ActualForecast> {

	public ForecastMatchCandidate(ActualForecast candidate, int confidence, FixedDate earliestFixed, FixedDate recommendedFixed) {
		super();
		
		LocalDate earliest = earliestFixed == null ? null : earliestFixed.asDate();
		LocalDate recommended = recommendedFixed == null ? null : recommendedFixed.asDate();
		this.payload = candidate;
		this.confidence = confidence;

		boolean earliestDateReq = earliest != null;
		boolean recommendedDateReq = recommended != null;
		boolean earliestDateAvailable = candidate.getEarliest() != null;
		boolean recommendedDateAvailable = candidate.getRecommended() != null;

		if(earliestDateReq && earliestDateAvailable) {
			this.scores.put("EARLIEST", Math.abs(earliest.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - candidate.getEarliest().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			this.completeness++;
		}

		if(recommendedDateReq && recommendedDateAvailable) {
			this.scores.put("RECOMMENDED", Math.abs(recommended.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - candidate.getRecommended().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			this.completeness++;
		}
	}

}
