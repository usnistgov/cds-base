package gov.nist.healthcare.cds.service.impl.validation;

import com.google.common.collect.Sets;
import gov.nist.healthcare.cds.service.MatchCandidateSelector;
import gov.nist.healthcare.cds.service.domain.matcher.MatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.ScoredMatches;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimpleMatchCandidateSelector implements MatchCandidateSelector {

    @Override
    public <T, E extends MatchCandidate<T>> ScoredMatches<E> getBestMatch(List<E> candidates) {

        if(candidates.size() == 0) {
            return new ScoredMatches<>(null, null, null, 0, 0L);
        }

        Set<String> intersection = candidates
                .stream()
                .map(MatchCandidate::provided)
                .reduce(candidates.get(0).provided(), Sets::intersection);


        Map<Long, List<E>> groupByScore = candidates
                .stream()
                .collect(Collectors.groupingBy(
                        (a) -> a.getScore(intersection)
                ));

        Long bestScore = Collections.min(groupByScore.keySet());
        if(groupByScore.get(bestScore).size() > 1) {

            Map<Integer, List<E>> groupByConfidence = groupByScore
                    .get(bestScore)
                    .stream()
                    .collect(Collectors.groupingBy(MatchCandidate::getConfidence));

            int bestConfidence = Collections.min(groupByConfidence.keySet());

            if(groupByConfidence.get(bestConfidence).size() > 1) {

                Map<Integer, List<E>> groupByCompleteness = groupByConfidence
                        .get(bestConfidence)
                        .stream()
                        .collect(Collectors.groupingBy(MatchCandidate::getCompleteness));

                int bestCompleteness = Collections.max(groupByCompleteness.keySet());

                return new ScoredMatches<>(groupByCompleteness.get(bestCompleteness).get(0), groupByScore, intersection, candidates.size(), bestScore);

            } else {
                return new ScoredMatches<>(groupByConfidence.get(bestConfidence).get(0), groupByScore, intersection, candidates.size(), bestScore);
            }

        } else {
            return new ScoredMatches<>(groupByScore.get(bestScore).get(0), groupByScore, intersection, candidates.size(), bestScore);
        }

    }

}
