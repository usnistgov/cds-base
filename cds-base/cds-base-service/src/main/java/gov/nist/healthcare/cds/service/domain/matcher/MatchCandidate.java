package gov.nist.healthcare.cds.service.domain.matcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MatchCandidate<T> {
    protected T payload;
    protected HashMap<String, Long> scores;
    protected int confidence;
    protected int completeness;

    public MatchCandidate() {
        this.scores = new HashMap<>();
    }

    public MatchCandidate(T payload, int confidence, int completeness) {
        this();
        this.payload = payload;
        this.confidence = confidence;
        this.completeness = completeness;
    }

    public Set<String> provided() {
        return this.scores.keySet();
    }

    public long getScore(Set<String> values) {
        return scores.entrySet()
                .stream()
                .filter(e -> values.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .reduce(0L, (a, b) -> a + b);
    }

    public int getConfidence() {
        return confidence;
    }

    public int getCompleteness() {
        return completeness;
    }

    public HashMap<String, Long> getScores() {
        return scores;
    }

    public T getPayload() {
        return payload;
    }
}
