package gov.nist.healthcare.cds.service.impl;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.DateCriterion;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationCriterion;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationValidation;
import gov.nist.healthcare.cds.domain.wrapper.ForecastRequirement;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;
import gov.nist.healthcare.cds.domain.wrapper.ResultCounts;
import gov.nist.healthcare.cds.domain.wrapper.StringCriterion;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventRequirement;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventValidation;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private VaccineMatcherService matcher;
	@Autowired
	private DateService dates;
	

	@Override
	public Report validate(EngineResponse response, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast) {
		Report vr = new Report();
		List<VaccinationEventValidation> veValidation = vr.getVeValidation();
		List<ForecastValidation> fValidation = vr.getFcValidation();
		ResultCounts fCounts = this.validateForecasts(response.getForecasts(), expForecast, fValidation);
		ResultCounts eCounts = this.validateEvents(response.getEvents(), events, veValidation);
		vr.setEvents(eCounts);
		vr.setForecasts(fCounts);
		return vr;
	}
	
	public ResultCounts validateForecasts(List<ActualForecast> afL, List<ForecastRequirement> efL, List<ForecastValidation> fValidation){
		ResultCounts counts = new ResultCounts();
		ForecastValidation tmp;
		
		for(ForecastRequirement ef : efL){
			ActualForecast af = this.findMatch(afL, ef.getExpForecast());
			if(af == null){
				tmp = ForecastValidation.unMatched(ef);
			}
			else {
				tmp = this.validate(ef, af);	
			}
			fValidation.add(tmp);	
			counts.addCounts(tmp.getCounts());
		}
		return counts;
	}
	
	public ResultCounts validateEvents(List<ResponseVaccinationEvent> rveL, List<VaccinationEventRequirement> evL, List<VaccinationEventValidation> veValidation){
		ResultCounts counts = new ResultCounts();
		VaccinationEventValidation tmp;
		
		for(VaccinationEventRequirement ev : evL){
			ResponseVaccinationEvent rve = this.findMatch(rveL, ev);
			if(rve == null){
				tmp = VaccinationEventValidation.unMatched(ev);
			}
			else {
				tmp = this.validate(ev, rve);
			}
			veValidation.add(tmp);	
			counts.addCounts(tmp.getCounts());
		}
		return counts;
	}
	
	private VaccinationEventValidation validate(VaccinationEventRequirement ve, ResponseVaccinationEvent rve) {
		VaccinationEventValidation vev = new VaccinationEventValidation();
		ResultCounts counts = new ResultCounts();
		vev.setCounts(counts);
		vev.setVeRequirement(ve);
		for(ExpectedEvaluation ee : ve.getvEvent().getEvaluations()){
			ActualEvaluation ae = this.findMatch(rve.getEvaluations(), ee);
			if(ae == null){
				vev.geteValidation().add(new EvaluationValidation(ee, new EvaluationCriterion(ValidationStatus.U)));
				counts.addU();
			}
			else if (ae.getStatus().equals(ee.getStatus())){
				vev.geteValidation().add(new EvaluationValidation(ee, new EvaluationCriterion(ValidationStatus.P)));
				counts.addP();
			}
			else {
				vev.geteValidation().add(new EvaluationValidation(ee, new EvaluationCriterion(ValidationStatus.F, ae.getStatus())));
				counts.addF();
			}
		}
		return vev;
	}

	public ActualForecast findMatch(List<ActualForecast> afL, ExpectedForecast ef){
		for(ActualForecast af : afL){
			if(matcher.match(af.getVaccine(), ef.getTarget())){
				return af;
			}
		}
		return null;
	}
	
	public ActualEvaluation findMatch(Set<ActualEvaluation> aeL, ExpectedEvaluation ee){
		for(ActualEvaluation ae : aeL){
			if(matcher.match(ae.getVaccine(), ee.getRelatedTo())){
				return ae;
			}
		}
		return null;
	}
	
	public ResponseVaccinationEvent findMatch(List<ResponseVaccinationEvent> rveL, VaccinationEventRequirement ve){
		for(ResponseVaccinationEvent rve : rveL){
			if(matcher.match(rve.getAdministred(), ve.getvEvent().getAdministred()) && dates.same(((FixedDate) rve.getDate()).getDate(), ve.getDateAdministred())){
				return rve;
			}
		}
		return null;
	}
	
	
	public ForecastValidation validate(ForecastRequirement fr, ActualForecast af){
		ForecastValidation fv = new ForecastValidation();
		ResultCounts counts = new ResultCounts();
		ExpectedForecast ef = fr.getExpForecast();
		fv.setForecastRequirement(fr);
		fv.setCounts(counts);
		
		// Dose
		if(ef.getDoseNumber().isEmpty()){
			fv.setDose(new StringCriterion(ValidationStatus.N));
		}
		else if(af.getDoseNumber().isEmpty()){
			fv.setDose(new StringCriterion(ValidationStatus.U));
			counts.addU();
		}
		else if(ef.getDoseNumber().equals(af.getDoseNumber())) {
			fv.setDose(new StringCriterion(ValidationStatus.P));
			counts.addP();
		}
		else {
			fv.setDose(new StringCriterion(ValidationStatus.F, af.getDoseNumber()));
			counts.addF();
		}
		
		// Earliest
		if(fr.getEarliest() == null){
			fv.setEarliest(new DateCriterion(ValidationStatus.N));
		}
		else if(af.getEarliest() == null) {
			fv.setEarliest(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		else if(dates.same(fr.getEarliest(),af.getEarliest())){
			fv.setEarliest(new DateCriterion(ValidationStatus.P));
			counts.addP();
		}
		else {
			fv.setEarliest(new DateCriterion(ValidationStatus.F, af.getEarliest()));
			counts.addF();
		}
		
		// Recommended
		if(fr.getRecommended() == null){
			fv.setRecommended(new DateCriterion(ValidationStatus.N));
		}
		else if(af.getRecommended() == null){
			fv.setRecommended(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		else if(dates.same(fr.getRecommended(),af.getRecommended())){
			fv.setRecommended(new DateCriterion(ValidationStatus.P));
			counts.addP();
		}
		else {
			fv.setRecommended(new DateCriterion(ValidationStatus.F, af.getRecommended()));
			counts.addF();
		}
		
		// pastDue
		if(fr.getPastDue() == null){
			fv.setPastDue(new DateCriterion(ValidationStatus.N));
		}
		else if(af.getPastDue() == null){
			fv.setPastDue(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		else if(dates.same(fr.getPastDue(),af.getPastDue())){
			fv.setPastDue(new DateCriterion(ValidationStatus.P));
			counts.addP();
		}
		else {
			fv.setPastDue(new DateCriterion(ValidationStatus.F, af.getPastDue()));
			counts.addF();
		}
		
		// Complete
		if(fr.getComplete() == null){
			fv.setComplete(new DateCriterion(ValidationStatus.N));
		}
		else if(af.getComplete() == null){
			fv.setComplete(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		else if(dates.same(fr.getComplete(),af.getComplete())){
			fv.setComplete(new DateCriterion(ValidationStatus.P));
			counts.addP();
		}
		else {
			fv.setComplete(new DateCriterion(ValidationStatus.F, af.getComplete()));
			counts.addF();
		}
		
		return fv;
	}


}
