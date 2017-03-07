package gov.nist.healthcare.cds.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.ForecastRequirement;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventRequirement;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.enumeration.FHIRAdapter;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.TestCaseExecutionService;
import gov.nist.healthcare.cds.service.TestRunnerService;
import gov.nist.healthcare.cds.service.ValidationService;

@Service
public class ExecutionService implements TestCaseExecutionService {

	@Autowired
	private ValidationService validation;
	@Autowired
	private DateService dates;
	@Autowired
	private TestRunnerService runner;
	
	@Override
	public Report execute(SoftwareConfig conf, TestCase tc, java.util.Date dt) throws UnresolvableDate {
		java.util.Date today = dt;
		TestCasePayLoad tcP = this.payLoad(tc, today);
		EngineResponse response = runner.run(conf, tcP);
		List<VaccinationEventRequirement> veRequirements = this.veRequirements(tc, today, tcP.getEvaluationDate(), tcP.getDateOfBirth());
		List<ForecastRequirement> fcRequirements = this.fcRequirements(tc, today,tcP.getEvaluationDate(), tcP.getDateOfBirth());
		Report rp = validation.validate(response, veRequirements, fcRequirements);
		rp.setEvaluationDate(tcP.getEvaluationDate());
		rp.setDob(tcP.getDateOfBirth());
		rp.setTc(tc);
		rp.setResponse(response.getResponse());
		System.out.println("[HTDEB]"+tcP);
		return rp;
	}
	
	
	public List<VaccinationEventRequirement> veRequirements(TestCase tc, java.util.Date today, java.util.Date evalR, java.util.Date dobR){
		FixedDate eval = new FixedDate(evalR);
		FixedDate dob = new FixedDate(dobR);
		List<VaccinationEventRequirement> veRequirements = new ArrayList<VaccinationEventRequirement>();
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				java.util.Date dA = dates.fix(ve.getDate(), dob, eval, today).getDate();
				VaccinationEventRequirement vReq = new VaccinationEventRequirement();
				vReq.setDateAdministred(dA);
				vReq.setvEvent(ve);
				veRequirements.add(vReq);
			}
		}
		return veRequirements;
	}
	
	public List<ForecastRequirement> fcRequirements(TestCase tc, java.util.Date today, java.util.Date evalR, java.util.Date dobR){
		FixedDate eval = new FixedDate(evalR);
		FixedDate dob = new FixedDate(dobR);
		List<ForecastRequirement> fcRequirements = new ArrayList<ForecastRequirement>();
		for(ExpectedForecast fc : tc.getForecast()){
			ForecastRequirement fcReq = new ForecastRequirement();
			
			FixedDate earliest = dates.fix(fc.getEarliest(), dob, eval, today);
			FixedDate rec = dates.fix(fc.getRecommended(), dob, eval, today);
			FixedDate pastDue = dates.fix(fc.getPastDue(), dob, eval, today);
			FixedDate complete = dates.fix(fc.getComplete(), dob, eval, today);
			
			fcReq.setExpForecast(fc);
			if(earliest != null)
				fcReq.setEarliest(earliest.getDate());
			if(rec != null)
				fcReq.setRecommended(rec.getDate());
			if(pastDue != null)
				fcReq.setPastDue(pastDue.getDate());
			if(complete != null)
				fcReq.setComplete(complete.getDate());
			
			fcRequirements.add(fcReq);
		}
		return fcRequirements;
	}
	
	public TestCasePayLoad payLoad(TestCase tc, java.util.Date today) throws UnresolvableDate{
		FixedDate eval = dates.evaluationDate(tc, today);
		FixedDate dob = dates.fix(tc.getPatient().getDob(), null, eval, today);
		
		TestCasePayLoad tcP = new TestCasePayLoad();
		tcP.setGender(tc.getPatient().getGender());
		tcP.setEvaluationDate(eval.getDate());
		tcP.setDateOfBirth(dob.getDate());
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				VaccineRef vr = this.vaccineRef(ve.getAdministred());
				java.util.Date dA = dates.fix(ve.getDate(), dob, eval, today).getDate();
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
