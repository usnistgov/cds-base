package gov.nist.healthcare.cds.service.domain.matcher;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScoredMatches<E extends MatchCandidate<?>> {

    E bestMatch;
    Map<Long, List<E>> matchScores;
    Set<String> scoreCriterion;
    int size;
    long score;

    public ScoredMatches(E bestMatch, Map<Long, List<E>> matchScores, Set<String> scoreCriterion, int size, long score) {
        this.bestMatch = bestMatch;
        this.matchScores = matchScores;
        this.scoreCriterion = scoreCriterion;
        this.size = size;
        this.score = score;
    }

    public E getBestMatch() {
        return bestMatch;
    }

    public void setBestMatch(E bestMatch) {
        this.bestMatch = bestMatch;
    }

    public Map<Long, List<E>> getMatchScores() {
        return matchScores;
    }

    public void setMatchScores(Map<Long, List<E>> matchScores) {
        this.matchScores = matchScores;
    }

    public Set<String> getScoreCriterion() {
        return scoreCriterion;
    }

    public void setScoreCriterions(Set<String> scoreCriterion) {
        this.scoreCriterion = scoreCriterion;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getScore() {
        return score;
    }
}
