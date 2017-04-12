package gov.nist.healthcare.cds.service.impl.validation;


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
import gov.nist.healthcare.cds.enumeration.ValidationCriterion;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.ValidationConfigService;
import gov.nist.healthcare.cds.service.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private VaccineMatcherService matcher;
	@Autowired
	private DateService dates;
	@Autowired
	private ValidationConfigService config;

	@Override
	public Report validate(EngineResponse response, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast) {
		Report vr = new Report();
		List<VaccinationEventValidation> veValidation = vr.getVeValidation();
		List<ForecastValidation> fValidation = vr.getFcValidation();
		ResultCounts fCounts = this.validateForecasts(response.getForecasts(), expForecast, fValidation,vr);
		ResultCounts eCounts = this.validateEvents(response.getEvents(), events, veValidation,vr);
		vr.setEvents(eCounts);
		vr.setForecasts(fCounts);
		return vr;
	}
	
	public ResultCounts validateForecasts(List<ActualForecast> afL, List<ForecastRequirement> efL, List<ForecastValidation> fValidation,Report vr){
		ResultCounts counts = new ResultCounts();
		ForecastValidation tmp;
		
		for(ForecastRequirement ef : efL){
			ActualForecast af = this.findMatch(afL, ef.getExpForecast());
			if(af == null){
				tmp = ForecastValidation.unMatched(ef);
			}
			else {
				tmp = this.validate(ef, af, vr);	
			}
			fValidation.add(tmp);	
			counts.addCounts(tmp.getCounts());
		}
		return counts;
	}
	
	public ResultCounts validateEvents(List<ResponseVaccinationEvent> rveL, List<VaccinationEventRequirement> evL, List<VaccinationEventValidation> veValidation,Report vr){
		ResultCounts counts = new ResultCounts();
		VaccinationEventValidation tmp;
		
		for(VaccinationEventRequirement ev : evL){
			ResponseVaccinationEvent rve = this.findMatch(rveL, ev);
			if(rve == null){
				tmp = VaccinationEventValidation.unMatched(ev);
			}
			else {
				tmp = this.validate(ev, rve, vr);
			}
			veValidation.add(tmp);	
			counts.addCounts(tmp.getCounts());
		}
		return counts;
	}
	
	private VaccinationEventValidation validate(VaccinationEventRequirement ve, ResponseVaccinationEvent rve,Report vr) {
		VaccinationEventValidation vev = new VaccinationEventValidation();
		ResultCounts counts = new ResultCounts();
		vev.setCounts(counts);
		vev.setVeRequirement(ve);
		for(ExpectedEvaluation ee : ve.getvEvent().getEvaluations()){
			ActualEvaluation ae = this.findMatch(rve.getEvaluations(), ee);
			EvaluationCriterion cr = null;
			if(ae == null){
				cr = new EvaluationCriterion(ValidationStatus.U);
			}
			else if (ae.getStatus().equals(ee.getStatus())){
				cr = new EvaluationCriterion(ValidationStatus.P,ae.getStatus());
			}
			else {
				cr = new EvaluationCriterion(config.failed(ValidationCriterion.EvaluationStatus), ae.getStatus());
				vr.put(ValidationCriterion.EvaluationStatus, true);
			}
			vev.geteValidation().add(new EvaluationValidation(ee, cr));
			counts.consider(cr);
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
	
	
	public ForecastValidation validate(ForecastRequirement fr, ActualForecast af, Report vr){
		ForecastValidation fv = new ForecastValidation();
		ResultCounts counts = new ResultCounts();
		ExpectedForecast ef = fr.getExpForecast();
		fv.setForecastRequirement(fr);
		fv.setCounts(counts);
		
		// Dose
		StringCriterion dose = null;
		if(ef.getDoseNumber().isEmpty()){
			dose = new StringCriterion(ValidationStatus.N);
		}
		else if(af.getDoseNumber().isEmpty()){
			dose = new StringCriterion(ValidationStatus.U);
		}
		else if(ef.getDoseNumber().equals(af.getDoseNumber())) {
			dose = new StringCriterion(ValidationStatus.P, af.getDoseNumber());
		}
		else {
			
			dose = new StringCriterion(config.failed(ValidationCriterion.DoseNumber), af.getDoseNumber());
			vr.put(ValidationCriterion.DoseNumber, true);
		}
		fv.setDose(dose);
		counts.consider(dose);
		
		// Earliest
		DateCriterion earliest;
		if(fr.getEarliest() == null){
			earliest = new DateCriterion(ValidationStatus.N);
		}
		else if(af.getEarliest() == null) {
			earliest = new DateCriterion(ValidationStatus.U);
		}
		else if(dates.same(fr.getEarliest(),af.getEarliest())){
			earliest = new DateCriterion(ValidationStatus.P, af.getEarliest());
		}
		else {
			earliest = new DateCriterion(config.failed(ValidationCriterion.EarliestDate), af.getEarliest());
			vr.put(ValidationCriterion.EarliestDate, true);
		}
		fv.setEarliest(earliest);
		counts.consider(earliest);

		
		// Recommended
		DateCriterion recommended;
		if(fr.getRecommended() == null){
			recommended = new DateCriterion(ValidationStatus.N);
		}
		else if(af.getRecommended() == null){
			recommended = new DateCriterion(ValidationStatus.U);
		}
		else if(dates.same(fr.getRecommended(),af.getRecommended())){
			recommended = new DateCriterion(ValidationStatus.P, af.getRecommended());
		}
		else {
			recommended = new DateCriterion(config.failed(ValidationCriterion.RecommendedDate), af.getRecommended());
			vr.put(ValidationCriterion.RecommendedDate, true);
		}
		fv.setRecommended(recommended);
		counts.consider(recommended);
		
		// pastDue
		DateCriterion pastDue;
		if(fr.getPastDue() == null){
			pastDue = new DateCriterion(ValidationStatus.N);
		}
		else if(af.getPastDue() == null){
			pastDue = new DateCriterion(ValidationStatus.U);
		}
		else if(dates.same(fr.getPastDue(),af.getPastDue())){
			pastDue = new DateCriterion(ValidationStatus.P, af.getPastDue());
		}
		else {
			pastDue = new DateCriterion(config.failed(ValidationCriterion.PastDueDate), af.getPastDue());
			vr.put(ValidationCriterion.PastDueDate, true);
		}
		fv.setPastDue(pastDue);
		counts.consider(pastDue);
		
		// Complete
		DateCriterion complete;
		if(fr.getComplete() == null){
			complete = new DateCriterion(ValidationStatus.N);
		}
		else if(af.getComplete() == null){
			complete = new DateCriterion(ValidationStatus.U);
		}
		else if(dates.same(fr.getComplete(),af.getComplete())){
			complete = new DateCriterion(ValidationStatus.P, af.getComplete());
		}
		else {
			complete =  new DateCriterion(config.failed(ValidationCriterion.CompleteDate), af.getComplete());
			vr.put(ValidationCriterion.CompleteDate,true);
		}
		fv.setComplete(complete);
		counts.consider(complete);
		
		return fv;
	}


}
