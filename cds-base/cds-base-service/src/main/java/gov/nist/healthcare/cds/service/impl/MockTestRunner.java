package gov.nist.healthcare.cds.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.EngineResponse;
import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.service.TestRunnerService;

//@Service
public class MockTestRunner implements TestRunnerService {

	@Override
	public EngineResponse run(SoftwareConfig config, TestCasePayLoad tc) {
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
			Date earliest = dateformat.parse("30/05/2011");
			Date recommended = dateformat.parse("26/06/2011");
			Date pastDue = dateformat.parse("22/08/2011");
			Date e1d = dateformat.parse("06/04/2011");
			Date e2d = dateformat.parse("02/05/2011");
			
			EngineResponse er = new EngineResponse();
			List<ActualForecast> afl = new ArrayList<ActualForecast>();
			ActualForecast af = new ActualForecast();
			af.setVaccine(new VaccineRef("107",""));
			af.setEarliest(earliest);
			af.setRecommended(recommended);
			af.setPastDue(pastDue);
			af.setDoseNumber("2");
			
			afl.add(af);
			List<ResponseVaccinationEvent> event = new ArrayList<ResponseVaccinationEvent>();
			ResponseVaccinationEvent rve = new ResponseVaccinationEvent();
			rve.setAdministred(new VaccineRef("107",""));
			rve.setDate(new FixedDate(e1d));
			rve.setDoseNumber(1);
			Set<ActualEvaluation> evals = new HashSet<ActualEvaluation>();
			ActualEvaluation ev = new ActualEvaluation();
			ev.setVaccine(new VaccineRef("107",""));
			ev.setStatus(EvaluationStatus.VALID);
			evals.add(ev);
			rve.setEvaluations(evals);
			event.add(rve);
			
			ResponseVaccinationEvent rve1 = new ResponseVaccinationEvent();
			rve1.setAdministred(new VaccineRef("107",""));
			rve1.setDate(new FixedDate(e2d));
			rve1.setDoseNumber(2);
			Set<ActualEvaluation> evals1 = new HashSet<ActualEvaluation>();
			ActualEvaluation ev1 = new ActualEvaluation();
			ev1.setVaccine(new VaccineRef("107",""));
			ev1.setStatus(EvaluationStatus.VALID);
			evals1.add(ev1);
			rve1.setEvaluations(evals1);
			event.add(rve1);
			
			er.setEvents(event);
			er.setForecasts(afl);
			return er;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
