package gov.nist.healthcare.cds.service.impl.validation.simulation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.ForecastRequirement;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.domain.wrapper.SimulationMap;
import gov.nist.healthcare.cds.domain.wrapper.TestCaseInformation;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventRequirement;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.TestCaseExecutionService;
import gov.nist.healthcare.cds.service.ValidationService;

public class TestExecutionSimulation implements TestCaseExecutionService {

	@Autowired
	private SimulationMap simulationMap;
	
	@Autowired
	private ValidationService validation;
	
	@Autowired
	private DateService dates;
	
	@Override
	public Report execute(SoftwareConfig conf, TestCase tc, Date reference) throws UnresolvableDate, ConnectionException {
		java.util.Date today = Calendar.getInstance().getTime();
		// Fix Eval, DOB, Events
		ResolvedDates rds = dates.resolveDates(tc, reference);
		
		// Create PayLoad and Send request
		TestCasePayLoad tcP = this.payLoad(tc, rds);

//		EngineResponse response = FHIRParse.parseResponseFromXml(simulationMap.getResult(tc.getUid()));
		EngineResponse response = new EngineResponse();
		
		// Compute Requirements
		List<VaccinationEventRequirement> veRequirements = this.veRequirements(tc, rds);
		List<ForecastRequirement> fcRequirements = this.fcRequirements(tc, rds);
		
		// Validate 
		Report rp = validation.validate(response, veRequirements, fcRequirements);
		
		// Set Report Properties
		TestCaseInformation tcInfo = new TestCaseInformation();
		tcInfo.setMetaData(tc.getMetaData());
		tcInfo.setName(tc.getName());
		tcInfo.setUID(tc.getUid());
		tcInfo.setDescription(tc.getDescription());
		
		rp.setTcInfo(tcInfo);
		rp.setEvaluationDate(tcP.getEvaluationDate());
		rp.setDob(tcP.getDateOfBirth());
		rp.setTc(tc.getId());
		rp.setSoftwareConfig(conf);
		rp.setGender(tcP.getGender());
		rp.setResponse(response.getResponse());
		rp.setExecutionDate(today);
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
	
	public TestCasePayLoad payLoad(TestCase tc, ResolvedDates rds) throws UnresolvableDate{
		TestCasePayLoad tcP = new TestCasePayLoad();
		tcP.setGender(tc.getPatient().getGender());
		tcP.setEvaluationDate(rds.getEval());
		tcP.setDateOfBirth(rds.getDob());
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				VaccineRef vr = this.vaccineRef(ve.getAdministred());
				java.util.Date dA = rds.getEvents().get(ve.getPosition());
				tcP.addImmunization(vr, dA);
			}
		}
		return tcP;
	}
	
	public VaccineRef vaccineRef(Injection i){
		if(i instanceof Product){
			Product p = (Product) i;
			return new VaccineRef(p.getVx().getCvx(), p.getMx().getMvx());
		}
		else {
			Vaccine v = (Vaccine) i;
			return new VaccineRef(v.getCvx(), "");
		}
	}



}
