package gov.nist.healthcare.cds.service.domain;

import java.util.Date;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;

public class ForecastMatchCandidate extends ResultMatch<ActualForecast> {
	
	private int earliestDateAvailable = 0;
	private int recommendedDateAvailable = 0;
	private int earliestDateReq = 0;
	private int recommendedDateReq = 0;
	private long earliestDateScore = 0L;
	private long recommendedDateScore = 0L;

	public ForecastMatchCandidate(ActualForecast candidate, int confidence, FixedDate earliestFixed, FixedDate recommendedFixed) {
		super();
		
		Date earliest = earliestFixed == null ? null : earliestFixed.asDate();
		Date recommended = recommendedFixed == null ? null : recommendedFixed.asDate();
		
		this.candidate = candidate;
		this.confidence = confidence;
		this.earliestDateReq = integer(earliest != null);
		this.recommendedDateReq = integer(recommended != null);
		this.earliestDateAvailable = integer(candidate.getEarliest() != null);
		this.recommendedDateAvailable = integer(candidate.getRecommended() != null);
		this.earliestDateScore = this.dateScore(earliest, candidate.getEarliest(), !bool(earliestDateReq) || !bool(earliestDateAvailable));
		this.recommendedDateScore = this.dateScore(recommended, candidate.getRecommended(), !bool(recommendedDateReq) || !bool(recommendedDateAvailable));
		this.completeness = this.completion(earliestDateAvailable + recommendedDateAvailable, earliestDateReq + recommendedDateReq);
	}

	public int getEarliestDateAvailable() {
		return earliestDateAvailable;
	}

	public int getRecommendedDateAvailable() {
		return recommendedDateAvailable;
	}

	public int getEarliestDateReq() {
		return earliestDateReq;
	}

	public int getRecommendedDateReq() {
		return recommendedDateReq;
	}

	public long getEarliestDateScore() {
		return earliestDateScore;
	}

	public long getRecommendedDateScore() {
		return recommendedDateScore;
	}

	private long completion(int available, int required) {
		return available > required ? required : available;
	}

	private boolean bool(int i) {
		return i != 0;
	}

	private int integer(boolean i) {
		return i ? 1 : 0;
	}

	private long dateScore(Date actual, Date expected, boolean zero) {
		if(zero) return 0;
		else return Math.abs(actual.getTime() - expected.getTime());
	}
}
