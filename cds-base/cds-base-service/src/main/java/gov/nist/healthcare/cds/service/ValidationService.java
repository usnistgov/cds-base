package gov.nist.healthcare.cds.service;
import java.util.List;

import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.ForecastRequirement;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventRequirement;

public interface ValidationService {

	Report validate(EngineResponse response, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast);
	
}
