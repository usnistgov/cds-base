package gov.nist.healthcare.cds.domain.wrapper;

import java.util.List;

public class ValidationResponse {
	
	private List<ActualForecast> forecasts;
	private List<ResponseVaccinationEvent> evaluatedEvents;
	
	public List<ActualForecast> getForecasts() {
		return forecasts;
	}
	public void setForecasts(List<ActualForecast> forecasts) {
		this.forecasts = forecasts;
	}
	public List<ResponseVaccinationEvent> getEvaluatedEvents() {
		return evaluatedEvents;
	}
	public void setEvaluatedEvents(List<ResponseVaccinationEvent> evaluatedEvents) {
		this.evaluatedEvents = evaluatedEvents;
	}
	
	
}
