package gov.nist.healthcare.cds.service.impl.validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gov.nist.healthcare.cds.domain.wrapper.*;
import gov.nist.healthcare.cds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;

public class ExecutionService implements TestCaseExecutionService {

	@Autowired
	private ValidationService validation;
	@Autowired
	private DateService dates;
	@Autowired
	private TestRunnerService runner;
	@Autowired
	private TestCaseUtilService testCaseUtilService;


	@Override
	public Report validateResponse(List<ActualForecast> forecasts, List<ResponseVaccinationEvent> events, TestCase tc, java.util.Date evaluationDate) {
		ResolvedDates rds = dates.resolveDates(tc, evaluationDate);
		List<VaccinationEventRequirement> veRequirements = this.veRequirements(tc, rds);
		List<ForecastRequirement> fcRequirements = this.fcRequirements(tc, rds);
		return validation.validate(forecasts, events, veRequirements, fcRequirements);
	}

	@Override
	public Report execute(SoftwareConfig conf, TestCase tc, java.util.Date reference) throws UnresolvableDate, ConnectionException {
		PerformanceTimestamps performanceBenchmark = new PerformanceTimestamps();

		java.util.Date today = Calendar.getInstance().getTime();
		// Fix Eval, DOB, Events
		ResolvedDates rds = dates.resolveDates(tc, reference);
		
		// Create PayLoad and Send request
		TestCasePayLoad tcP = this.testCaseUtilService.getTestCasePayload(tc, rds);
		tcP.setTestCaseNumber(tc.getUid());
		
		// Send request to adapter
		performanceBenchmark.setRequestSentToAdapter(new Date().getTime());
		EngineResponse response = runner.run(conf, tcP);

		performanceBenchmark.setResponseReceivedFromAdapter(new Date().getTime());

		// Compute Requirements
		List<VaccinationEventRequirement> veRequirements = this.veRequirements(tc, rds);
		List<ForecastRequirement> fcRequirements = this.fcRequirements(tc, rds);
		
		// Validate 
		Report rp = validation.validate(response, veRequirements, fcRequirements);
		performanceBenchmark.setResponseValidated(new Date().getTime());

		// Set Report Properties
		TestCaseInformation tcInfo = new TestCaseInformation();
		tcInfo.setMetaData(tc.getMetaData());
		tcInfo.setName(tc.getName());
		tcInfo.setUID(tc.getUid());
		tcInfo.setDescription(tc.getDescription());
		rp.setEngineResponseStatus(response.getEngineResponseStatus());
		rp.setTcInfo(tcInfo);
		rp.setEvaluationDate(tcP.getEvaluationDate());
		rp.setDob(tcP.getDateOfBirth());
		rp.setTc(tc.getId());
		rp.setSoftwareConfig(conf);
		rp.setGender(tcP.getGender());
		rp.setResponse(response.getResponse());
		rp.setExecutionDate(today);
		rp.setAdapterLogs(response.getLogs());
		rp.setIssues(response.getIssues());
		rp.setTimestamps(performanceBenchmark);
		return rp;
	}
	
	
	public List<VaccinationEventRequirement> veRequirements(TestCase tc, ResolvedDates rds){
		List<VaccinationEventRequirement> veRequirements = new ArrayList<VaccinationEventRequirement>();
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				java.util.Date dA = rds.getEvents().get(ve.getPosition());
				VaccinationEventRequirement vReq = new VaccinationEventRequirement();
				vReq.setDateAdministred(new FixedDate(dA));
				vReq.setvEvent(ve);
				veRequirements.add(vReq);
			}
		}
		return veRequirements;
	}
	
	public List<ForecastRequirement> fcRequirements(TestCase tc, ResolvedDates rds){
		List<ForecastRequirement> fcRequirements = new ArrayList<ForecastRequirement>();
		for(ExpectedForecast fc : tc.getForecast()){
			ForecastRequirement fcReq = new ForecastRequirement();		
			fcReq.setExpForecast(fc);
			if(fc.getEarliest() != null)
				fcReq.setEarliest(new FixedDate(dates.fix(rds, fc.getEarliest())));
			if(fc.getRecommended() != null)
				fcReq.setRecommended(new FixedDate(dates.fix(rds, fc.getRecommended())));
			if(fc.getPastDue() != null)
				fcReq.setPastDue(new FixedDate(dates.fix(rds, fc.getPastDue())));
			if(fc.getComplete() != null)
				fcReq.setComplete(new FixedDate(dates.fix(rds, fc.getComplete())));
			if(fc.getSerieStatus() != null)
				fcReq.setSeriesStatus(fc.getSerieStatus());
			
			fcRequirements.add(fcReq);
		}
		return fcRequirements;
	}

}
