package gov.nist.healthcare.cds.domain.wrapper;

public class PerformanceTimestamps {

    private long requestReceived;
    private long requestSentToAdapter;
    private long responseReceivedFromAdapter;
    private long responseValidated;
    private long responseSent;

    public long getRequestReceived() {
        return requestReceived;
    }

    public void setRequestReceived(long requestReceived) {
        this.requestReceived = requestReceived;
    }

    public long getRequestSentToAdapter() {
        return requestSentToAdapter;
    }

    public void setRequestSentToAdapter(long requestSentToAdapter) {
        this.requestSentToAdapter = requestSentToAdapter;
    }

    public long getResponseReceivedFromAdapter() {
        return responseReceivedFromAdapter;
    }

    public void setResponseReceivedFromAdapter(long responseReceivedFromAdapter) {
        this.responseReceivedFromAdapter = responseReceivedFromAdapter;
    }

    public long getResponseValidated() {
        return responseValidated;
    }

    public void setResponseValidated(long responseValidated) {
        this.responseValidated = responseValidated;
    }

    public long getResponseSent() {
        return responseSent;
    }

    public void setResponseSent(long responseSent) {
        this.responseSent = responseSent;
    }
}
