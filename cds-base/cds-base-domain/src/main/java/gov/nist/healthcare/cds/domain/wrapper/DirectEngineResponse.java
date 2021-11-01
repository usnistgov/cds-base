package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;
import java.util.List;

public class DirectEngineResponse {
    private Date evaluationDate;
    private String testCaseId;
    private List<ActualForecast> forecasts;
    private List<ResponseVaccinationEvent> events;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public Date getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    public List<ActualForecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ActualForecast> forecasts) {
        this.forecasts = forecasts;
    }

    public List<ResponseVaccinationEvent> getEvents() {
        return events;
    }

    public void setEvents(List<ResponseVaccinationEvent> events) {
        this.events = events;
    }
}
