package gov.nist.healthcare.cds.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class UserMetadata {

    @Id
    private String username;
    private int testCases;
    private int executions;
    private java.util.Date lastApiCall;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExecutions() {
        return executions;
    }

    public void setExecutions(int executions) {
        this.executions = executions;
    }

    public int getTestCases() {
        return testCases;
    }

    public void setTestCases(int testCases) {
        this.testCases = testCases;
    }

    public Date getLastApiCall() {
        return lastApiCall;
    }

    public void setLastApiCall(Date lastApiCall) {
        this.lastApiCall = lastApiCall;
    }
}
