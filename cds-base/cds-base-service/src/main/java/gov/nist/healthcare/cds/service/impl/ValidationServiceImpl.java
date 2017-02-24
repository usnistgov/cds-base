package gov.nist.healthcare.cds.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.ValidationReport;
import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.EventPair;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationPair;
import gov.nist.healthcare.cds.domain.wrapper.EventsReport;
import gov.nist.healthcare.cds.domain.wrapper.ForecastPair;
import gov.nist.healthcare.cds.domain.wrapper.ForecastsReport;
import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.ValidationService;

public class ValidationServiceImpl implements ValidationService {

	@Autowired
	private VaccineMatcherService matcher;
	@Autowired
	private DateService dates;
	
	@Override
	public ValidationReport validate(EngineResponse response, TestCase tc) {
		ValidationReport vr = new ValidationReport();
		vr.setEvents(this.browseEvents(response, tc));
		vr.setForecasts(this.browseForecasts(response, tc));
		return vr;
	}
	
	public EventPair validateEvent(ResponseVaccinationEvent rve, VaccinationEvent ve){
		EventPair evaluatedEv = new EventPair();
		evaluatedEv.setEvent(ve);
		Set<ExpectedEvaluation> eE = new HashSet<ExpectedEvaluation>(ve.getEvaluations());
		Set<ActualEvaluation> aE = new HashSet<ActualEvaluation>(rve.getEvaluations());
		
		for(ExpectedEvaluation ee : eE){
			for(ActualEvaluation ae : aE){
				if(matcher.match(ae.getVaccine(), ee.getRelatedTo())){
					evaluatedEv.getPairs().add(validateEvaluation(ee,ae));
					eE.remove(ee);
				}
			}
		}
		
		evaluatedEv.getUnmatched().addAll(eE);
		return evaluatedEv;
	}
	
	public ForecastsReport browseForecasts(EngineResponse er, TestCase tc){
		ForecastsReport forecastVal = new ForecastsReport();
		Set<ExpectedForecast> eF = new HashSet<ExpectedForecast>(tc.getForecast());
		Set<ActualForecast> aF = new HashSet<ActualForecast>(er.getForecasts());
		
		for(ExpectedForecast ef : eF){
			for(ActualForecast af : aF){
				if(matcher.match(af.getVaccine(), ef.getTarget())){
					forecastVal.getPairs().add(validateForecast(ef,af));
					eF.remove(ef);
					aF.remove(af);
				}
			}
		}
		
		forecastVal.getUnmatched().addAll(eF);
		return forecastVal;
	}
	
	public EventsReport browseEvents(EngineResponse er, TestCase tc){
		EventsReport eventReport = new EventsReport();
		Set<Event> events = new HashSet<Event>(tc.getEvents());
		
		for(ResponseVaccinationEvent rve : er.getEvents()){
			for(Event e : events){
				if(e instanceof VaccinationEvent){
					VaccinationEvent ve = (VaccinationEvent) e;
					if(matcher.match(rve.getAdministred(), ve.getAdministred()) && dates.same(ve.getDate(), rve.getDate())){
						eventReport.getEvents().add(this.validateEvent(rve, ve));
						events.remove(e);
					}
				}
			}
		}
		
		eventReport.getUnmatched().addAll(events);
		return eventReport;
	}
	
	public EvaluationPair validateEvaluation(ExpectedEvaluation ee, ActualEvaluation ae){
		EvaluationPair ep = new EvaluationPair();
		ep.setActual(ae);
		ep.setExpected(ee);
		ep.setMatch(ae.getStatus().equals(ee.getStatus()));
		return ep;
	}
	
	public ForecastPair validateForecast(ExpectedForecast ef, ActualForecast af){
		ForecastPair fp = new ForecastPair();
		fp.setActual(af);
		fp.setExpected(ef);
		fp.setEarliest(dates.same(ef.getEarliest(), af.getEarliest()));
		fp.setRecommended(dates.same(ef.getRecommended(), af.getRecommended()));
		fp.setPastDue(dates.same(ef.getPastDue(),af.getPastDue()));
		fp.setComplete(dates.same(ef.getComplete(),af.getComplete()));
		fp.setDose(ef.getDoseNumber().equals(af.getDoseNumber()));
		return fp;
	}

}
