package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.TestCase;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ValidationRequest {
    List<ActualForecast> forecasts;
    List<ResponseVaccinationEvent> events;
    TestCase testCase;
    LocalDate evaluationDate;

    public ValidationRequest(List<ActualForecast> forecasts, List<ResponseVaccinationEvent> events, TestCase testCase, LocalDate evaluationDate) {
        this.forecasts = forecasts;
        this.events = events;
        this.testCase = testCase;
        this.evaluationDate = evaluationDate;
    }

    public ValidationRequest() {
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

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public LocalDate getEvaluationDate() {
        return evaluationDate;
    }

    public void setEvaluationDate(LocalDate evaluationDate) {
        this.evaluationDate = evaluationDate;
    }
}
