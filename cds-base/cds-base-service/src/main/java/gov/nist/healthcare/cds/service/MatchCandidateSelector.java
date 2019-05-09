package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.service.domain.matcher.MatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.ScoredMatches;

import java.util.List;

public interface MatchCandidateSelector {

    <T, E extends MatchCandidate<T>> ScoredMatches<E> getBestMatch(List<E> candidates);

}
