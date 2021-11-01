package gov.nist.healthcare.cds.service;
import java.util.List;

import gov.nist.healthcare.cds.domain.wrapper.*;

public interface ValidationService {

	Report validate(EngineResponse response, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast);
	Report validate(List<ActualForecast> forecasts, List<ResponseVaccinationEvent> evaluations, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast);

}
