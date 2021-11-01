package gov.nist.healthcare.cds.service.impl.validation;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.base.Strings;
import gov.nist.healthcare.cds.service.*;
import gov.nist.healthcare.cds.service.domain.matcher.ForecastMatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.ScoredMatches;
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
import gov.nist.healthcare.cds.service.domain.matcher.EvaluationMatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.VaccinationEventMatchCandidate;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private VaccineMatcherService matcher;
	@Autowired
	private DateService dates;
	@Autowired
	private ValidationConfigService config;
	@Autowired
	private MatchCandidateSelector matchCandidateSelector;

	@Override
	public Report validate(EngineResponse response, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast) {
		Report vr = new Report();
		List<VaccinationEventValidation> veValidation = vr.getVeValidation();
		List<ForecastValidation> fValidation = vr.getFcValidation();
		StringBuilder logs = new StringBuilder();
		ResultCounts fCounts = this.validateForecasts(response.getForecasts(), expForecast, fValidation,vr, logs);
		ResultCounts eCounts = this.validateEvents(response.getEvents(), events, veValidation,vr, logs);
		vr.setEvents(eCounts);
		vr.setForecasts(fCounts);
		vr.setMatcherLogs(logs.toString());
		return vr;
	}

	@Override
	public Report validate(List<ActualForecast> forecasts, List<ResponseVaccinationEvent> evaluations, List<VaccinationEventRequirement> events, List<ForecastRequirement> expForecast) {
		Report vr = new Report();
		List<VaccinationEventValidation> veValidation = vr.getVeValidation();
		List<ForecastValidation> fValidation = vr.getFcValidation();
		StringBuilder logs = new StringBuilder();
		ResultCounts fCounts = this.validateForecasts(forecasts, expForecast, fValidation,vr, logs);
		ResultCounts eCounts = this.validateEvents(evaluations, events, veValidation,vr, logs);
		vr.setEvents(eCounts);
		vr.setForecasts(fCounts);
		vr.setMatcherLogs(logs.toString());
		return vr;
	}

	public ResultCounts validateForecasts(List<ActualForecast> afL, List<ForecastRequirement> efL, List<ForecastValidation> fValidation,Report vr, StringBuilder logs){
		ResultCounts counts = new ResultCounts();
		ForecastValidation tmp;
		LoggerService.separator(logs);
		LoggerService.text("VALIDATING FORECASTS", logs, true, 6);
		LoggerService.separator(logs);
		for(ForecastRequirement ef : efL){
			
			LoggerService.banner("RESOLVING MATCH FOR FORECAST", logs, false, 0);
			LoggerService.vaccine(ef.getExpForecast().getTarget(), logs, true, 0);
			
			ActualForecast af = this.findMatch(afL, ef, logs);
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
	
	public ResultCounts validateEvents(List<ResponseVaccinationEvent> rveL, List<VaccinationEventRequirement> evL, List<VaccinationEventValidation> veValidation,Report vr, StringBuilder logs) {
		ResultCounts counts = new ResultCounts();
		VaccinationEventValidation tmp;
		LoggerService.separator(logs);
		LoggerService.text("VALIDATING EVENTS", logs, true, 6);
		LoggerService.separator(logs);
		for(VaccinationEventRequirement ev : evL){
			logs.append("\n");
			LoggerService.banner("RESOLVING MATCH FOR", logs, false, 0);
			LoggerService.event(ev.getvEvent().getAdministred(), ev.getDateAdministred().getDateString(), logs, 0);

			ResponseVaccinationEvent rve = this.findMatch(rveL, ev, logs);
			
			if(rve == null){
				LoggerService.result("No match found", logs, true, 1);
				tmp = VaccinationEventValidation.unMatched(ev);
			}
			else {
				LoggerService.result("match found", logs, false, 1);
				LoggerService.vaccineRef(rve.getAdministred(), logs, true, 0);

				tmp = this.validate(ev, rve, vr, logs);
			}
			LoggerService.banner("DONE FOR", logs, false, 0);
			LoggerService.event(ev.getvEvent().getAdministred(), ev.getDateAdministred().getDateString(), logs, 0);

			veValidation.add(tmp);	
			counts.addCounts(tmp.getCounts());
		}
		return counts;
	}
	
	private VaccinationEventValidation validate(VaccinationEventRequirement ve, ResponseVaccinationEvent rve,Report vr, StringBuilder logs) {
		VaccinationEventValidation vev = new VaccinationEventValidation();
		ResultCounts counts = new ResultCounts();
		vev.setCounts(counts);
		vev.setVeRequirement(ve);
		for(ExpectedEvaluation ee : ve.getvEvent().getEvaluations()){
			logs.append("\n");
			LoggerService.banner("LOOKING FOR EVALUATION THAT MATCHES EXPECTED CVX", logs, false, 1);
			LoggerService.comment("Expected Evaluation CVX "+ee.getRelatedTo().getCvx(), logs, true, 0);
			ActualEvaluation ae = this.findMatch(rve.getEvaluations(), ee, logs);
			EvaluationCriterion cr = null;
			if(ae != null){
				LoggerService.result("match found as ", logs, false, 1);
				LoggerService.vaccineRef(ae.getVaccine(), logs, true, 0);
			}
			
			if(ae == null){
				LoggerService.result("match not found ", logs, true, 1);
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

	public ActualForecast findMatch(List<ActualForecast> afL, ForecastRequirement fr, StringBuilder logs){
		ExpectedForecast ef = fr.getExpForecast();
		
		List<ForecastMatchCandidate> matches = new ArrayList<>();
		for(ActualForecast af : afL){
			int confidence = matcher.match(af.getVaccine(), ef.getTarget(), logs);
			if(confidence > 0) {
				matches.add(new ForecastMatchCandidate(af, confidence, fr.getEarliest(), fr.getRecommended()));
			}
		}

		ScoredMatches<ForecastMatchCandidate> scoredMatches = this.matchCandidateSelector.getBestMatch(matches);
		LoggerService.printScoredForecasts(scoredMatches, logs);

		if(scoredMatches.getBestMatch() != null) {
			return scoredMatches.getBestMatch().getPayload();
		}
		return null;
	}
	
	public ActualEvaluation findMatch(Set<ActualEvaluation> aeL, ExpectedEvaluation ee, StringBuilder logs){
		
		List<EvaluationMatchCandidate> matches = new ArrayList<>();
		for(ActualEvaluation ae : aeL){
			int confidence = matcher.match(ae.getVaccine(), ee.getRelatedTo(), logs);
			if(confidence > 0) {
				matches.add(new EvaluationMatchCandidate(ae, confidence, ee.getStatus()));
			}
		}

		ScoredMatches<EvaluationMatchCandidate> scoredMatches = this.matchCandidateSelector.getBestMatch(matches);
		LoggerService.printScoredEvaluations(scoredMatches, logs);

		if(scoredMatches.getBestMatch() != null) {
			return scoredMatches.getBestMatch().getPayload();
		}
		return null;
	}
	
	public ResponseVaccinationEvent findMatch(List<ResponseVaccinationEvent> rveL, VaccinationEventRequirement ve, StringBuilder logs){
		List<VaccinationEventMatchCandidate> matches = new ArrayList<>();
		for(ResponseVaccinationEvent rve : rveL){
			if(dates.same(((FixedDate) rve.getDate()).asDate(), ve.getDateAdministred().asDate())){
				int confidence =  matcher.match(rve.getAdministred(), ve.getvEvent().getAdministred(), logs);
				if(confidence > 0) {
					matches.add(new VaccinationEventMatchCandidate(rve, confidence));
				}
			}
		}

		ScoredMatches<VaccinationEventMatchCandidate> scoredMatches = this.matchCandidateSelector.getBestMatch(matches);
		LoggerService.printScoredVaccinationEvent(scoredMatches, logs);

		if(scoredMatches.getBestMatch() != null) {
			return scoredMatches.getBestMatch().getPayload();
		}
		return null;
	}
	
	public boolean doseEqual(String d1, String d2){
		List<String> none = new ArrayList<String>();
		none.add("0");
		none.add("-");
		
		return d1.toLowerCase().equals(d2.toLowerCase()) || (none.contains(d1) && none.contains(d2));
	}
	
	public ForecastValidation validate(ForecastRequirement fr, ActualForecast af, Report vr){
		ForecastValidation fv = new ForecastValidation();
		ResultCounts counts = new ResultCounts();
		ExpectedForecast ef = fr.getExpForecast();
		fv.setForecastRequirement(fr);
		fv.setCounts(counts);
		
		// Dose
		StringCriterion dose = null;
		if(Strings.isNullOrEmpty(ef.getDoseNumber())){
			dose = new StringCriterion(ValidationStatus.N);
		}
		else if(Strings.isNullOrEmpty(af.getDoseNumber())){
			dose = new StringCriterion(ValidationStatus.U);
		}
		else if(doseEqual(ef.getDoseNumber(),af.getDoseNumber())) {
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
		else if(dates.same(fr.getEarliest().asDate(),af.getEarliest())){
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
		else if(dates.same(fr.getRecommended().asDate(),af.getRecommended())){
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
		else if(dates.same(fr.getPastDue().asDate(),af.getPastDue())){
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
		else if(dates.same(fr.getComplete().asDate(),af.getComplete())){
			complete = new DateCriterion(ValidationStatus.P, af.getComplete());
		}
		else {
			complete =  new DateCriterion(config.failed(ValidationCriterion.CompleteDate), af.getComplete());
			vr.put(ValidationCriterion.CompleteDate,true);
		}
		fv.setComplete(complete);
		counts.consider(complete);
		
		// Series Status
		StringCriterion seriesStatus;
		if(fr.getSeriesStatus() == null){
			seriesStatus = new StringCriterion(ValidationStatus.N);
		}
		else if(af.getSerieStatus() == null){
			seriesStatus = new StringCriterion(ValidationStatus.U);
		}
		else if(af.getSerieStatus().equals(fr.getSeriesStatus())){
			seriesStatus = new StringCriterion(ValidationStatus.P, af.getSerieStatus().toString());
		}
		else {
			seriesStatus =  new StringCriterion(config.failed(ValidationCriterion.SeriesStatus), af.getSerieStatus().toString());
			vr.put(ValidationCriterion.SeriesStatus,true);
		}
		fv.setSerieStatus(seriesStatus);
		counts.consider(seriesStatus);
		
		return fv;
	}


}
