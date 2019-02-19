package gov.nist.healthcare.cds.service.domain;

public class ResultMatch<T> implements Comparable<ResultMatch<T>> {
	
	protected long confidence;
	protected long expectation;
	protected long completeness;
	protected T candidate;
	
	public long getConfidence() {
		return confidence;
	}
	public void setConfidence(long confidence) {
		this.confidence = confidence;
	}
	public long getExpectation() {
		return expectation;
	}
	public void setExpectation(long expectation) {
		this.expectation = expectation;
	}
	public long getCompleteness() {
		return completeness;
	}
	public void setCompleteness(long completeness) {
		this.completeness = completeness;
	}
	public T getCandidate() {
		return candidate;
	}
	public void setCandidate(T candidate) {
		this.candidate = candidate;
	}
	
	@Override
	public int compareTo(ResultMatch<T> rm) {
		if(this.confidence > rm.confidence) {
			return 1;
		} else if(this.confidence < rm.confidence) {
			return -1;
		} else {
			
			if(this.expectation > rm.expectation) {
				return -1;
			} else if(this.expectation < rm.expectation) {
				return 1;
			} else {
				
				if(this.completeness > rm.completeness) {
					return 1;
				} else if(this.completeness < rm.completeness) {
					return -1;
				} else {
					return 0;
				}
				
			}
		}
	}
}
