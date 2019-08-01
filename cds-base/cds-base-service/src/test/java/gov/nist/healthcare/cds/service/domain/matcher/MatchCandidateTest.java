package gov.nist.healthcare.cds.service.domain.matcher;

import gov.nist.healthcare.cds.service.impl.validation.SimpleMatchCandidateSelector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MatchCandidateTest {

    SimpleMatchCandidateSelector selector = new SimpleMatchCandidateSelector();

    @Test
    public void selectTheOneWithBetterExpectation() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 1, 2);
        candidate_1.getScores().put("A", 1L);
        candidate_1.getScores().put("B", 1L);

        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 1, 2);
        candidate_2.getScores().put("A", 2L);
        candidate_2.getScores().put("B", 1L);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_1);
    }

    @Test
    public void selectTheOneWithBetterConfidence() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 1, 2);
        candidate_1.getScores().put("A", 1L);
        candidate_1.getScores().put("B", 1L);

        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 2, 2);
        candidate_2.getScores().put("A", 1L);
        candidate_2.getScores().put("B", 1L);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_1);
    }

    @Test
    public void selectTheOneWithBetterConfidenceNoScore() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 1, 2);
        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 2, 2);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_1);
    }

    @Test
    public void selectTheOneWithBetterCompleteness() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 2, 2);
        candidate_1.getScores().put("A", 1L);
        candidate_1.getScores().put("B", 1L);

        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 2, 1);
        candidate_2.getScores().put("A", 1L);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_1);
    }

    @Test
    public void selectTheOneWithBetterExpectationOverlap() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 1, 2);
        candidate_1.getScores().put("A", 1L);

        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 1, 2);
        candidate_2.getScores().put("A", 0L);
        candidate_2.getScores().put("B", 3L);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_2);
    }

    @Test
    public void selectTheOneWithBetterConfidenceNoOverlap() {
        MatchCandidate<String> candidate_1 = new MatchCandidate<>("", 2, 2);
        candidate_1.getScores().put("A", 1L);

        MatchCandidate<String> candidate_2 = new MatchCandidate<>("", 1, 2);
        candidate_2.getScores().put("B", 3L);

        MatchCandidate<String> bestMatch = this.selector.getBestMatch(Arrays.asList(candidate_1, candidate_2)).getBestMatch();
        Assert.assertTrue(bestMatch == candidate_2);
    }


}
