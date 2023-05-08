package gov.nist.healthcare.cds.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import gov.nist.healthcare.cds.domain.*;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.domain.matcher.EvaluationMatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.ForecastMatchCandidate;
import gov.nist.healthcare.cds.service.domain.matcher.ScoredMatches;
import gov.nist.healthcare.cds.service.domain.matcher.VaccinationEventMatchCandidate;

public class LoggerService {

	public static void injection(Injection i, StringBuilder log, boolean ln, int t){
		if(i instanceof Product){
			product((Product) i, log, ln, t);
		}
		else if(i instanceof Vaccine){
			vaccine((Vaccine) i, log, ln, t);
		}
	}
	
	public static void vaccineRef(VaccineRef i, StringBuilder log, boolean ln, int t){
		tab(t, log);
		if(i.isHasMvx()){
			log.append(" (Product) CVX="+i.getCvx()+", mvx="+i.getMvx()); 
		}
		else {
			log.append(" (Vaccine) CVX="+i.getCvx()); 
		}
		if(ln)
		log.append("\n");
	}
	
	public static void product(Product p, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append(" (Product) CVX="+p.getCvx()+", mvx="+p.getMx().getMvx()); 
		if(ln)
		log.append("\n");
	}
	
	public static void vaccine(Vaccine v, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append(" (Vaccine) CVX="+v.getCvx()); 
		if(ln)
		log.append("\n");
	}
	
	public static void event(Injection i, String date, StringBuilder log, int t){
		tab(t, log);
		log.append(" Vaccination Event ");
		injection(i, log, false, t);
		log.append(" at ");
		log.append(date);
		log.append("\n");
	}
	
	public static void comment(String comment, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append("| "+comment +" ");
		if(ln)
			log.append("\n");
	}
	
	public static void vaccineGroupSet(Set<VaccineGroup> groups, StringBuilder log, int t){
		tab(t, log);
		for(VaccineGroup g : groups){
			log.append("["+g.getName()+","+g.getCvx()+"]");
		}
	}
	
	public static void tab(int i, StringBuilder log){
		for(int j = 0; j < i; j++){
			log.append("\t");
		}
	}
	
	public static void event(Injection i, LocalDate date, StringBuilder log, int t){
		event(i, FixedDate.formatter.format(date), log, t);
	}
	
	public static void separator(StringBuilder log){
		log.append("========== ========== ========== ========== ==========");
		log.append("\n");
	}
	
	public static void separator(String title, StringBuilder log){
		log.append("========== "+title+" ==========");
		log.append("\n");
	}
	
	public static void banner(String title, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append("["+title+"]");
		if(ln){
			log.append("\n");
		}
	}
	
	public static void text(String title, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append(title);
		if(ln){
			log.append("\n");
		}
	}
	
	public static void pass(String assertion, StringBuilder log, int t){
		tab(t, log);
		log.append("[PASS] "+assertion);
		log.append("\n");
	}
	
	public static void fail(String assertion, StringBuilder log, int t){
		tab(t, log);
		log.append("[FAIL] "+assertion);
		log.append("\n");
	}
	
	public static void passOrFail(boolean pof, String pass, String fail, StringBuilder log, int t){
		if(pof) pass(pass, log, t); else fail(fail, log, t);
	}
	
	public static void result(String data, StringBuilder log, boolean ln, int t){
		tab(t, log);
		log.append("[DECISION] "+data);
		if(ln)
		log.append("\n");
	}

	public static void printScoredForecasts(ScoredMatches<ForecastMatchCandidate> scored, StringBuilder logs) {
		LoggerService.banner("FOUND "+scored.getSize()+" MATCHES", logs, true, 1);

		if(scored.getSize() > 0) {
			LoggerService.banner("CALCULATING MATCHES SCORES", logs, true, 2);

			for(Long score: scored.getMatchScores().keySet()) {
				for(ForecastMatchCandidate candidate: scored.getMatchScores().get(score)) {
					LoggerService.vaccineRef(candidate.getPayload().getVaccine(), logs, false, 3);
					LoggerService.text(String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]", candidate.getConfidence(), score, candidate.getCompleteness()), logs, true, 1);
				}
			}

			LoggerService.banner("BEST MATCH", logs, false, 2);
			LoggerService.vaccineRef(scored.getBestMatch().getPayload().getVaccine(), logs, false, 0);
			LoggerService.text("with score of "+String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]",
					scored.getBestMatch().getConfidence(),
					scored.getScore(),
					scored.getBestMatch().getCompleteness()),
					logs,
					true,
					0
			);
		}

	}

	public static void printScoredEvaluations(ScoredMatches<EvaluationMatchCandidate> scored, StringBuilder logs) {
		LoggerService.banner("FOUND "+scored.getSize()+" MATCHES", logs, true, 1);

		if(scored.getSize() > 0) {
			LoggerService.banner("CALCULATING MATCHES SCORES", logs, true, 2);

			for (Long score : scored.getMatchScores().keySet()) {
				for (EvaluationMatchCandidate candidate : scored.getMatchScores().get(score)) {
					LoggerService.vaccineRef(candidate.getPayload().getVaccine(), logs, false, 3);
					LoggerService.text(String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]", candidate.getConfidence(), score, candidate.getCompleteness()), logs, true, 1);
				}
			}

			LoggerService.banner("BEST MATCH", logs, false, 2);
			LoggerService.vaccineRef(scored.getBestMatch().getPayload().getVaccine(), logs, false, 0);
			LoggerService.text("with score of " + String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]",
					scored.getBestMatch().getConfidence(),
					scored.getScore(),
					scored.getBestMatch().getCompleteness()),
					logs,
					true,
					0
			);
		}
	}

	public static void printScoredVaccinationEvent(ScoredMatches<VaccinationEventMatchCandidate> scored, StringBuilder logs) {
		LoggerService.banner("FOUND "+scored.getSize()+" MATCHES", logs, true, 1);

		if(scored.getSize() > 0) {
			LoggerService.banner("CALCULATING MATCHES SCORES", logs, true, 2);

			for (Long score : scored.getMatchScores().keySet()) {
				for (VaccinationEventMatchCandidate candidate : scored.getMatchScores().get(score)) {
					LoggerService.vaccineRef(candidate.getPayload().getAdministred(), logs, false, 3);
					LoggerService.text(String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]", candidate.getConfidence(), score, candidate.getCompleteness()), logs, true, 1);
				}
			}

			LoggerService.banner("BEST MATCH", logs, false, 2);
			LoggerService.vaccineRef(scored.getBestMatch().getPayload().getAdministred(), logs, false, 0);
			LoggerService.text("with score of " + String.format(" => [ Confidence = %d, Expectation = %d, Completeness = %d ]",
					scored.getBestMatch().getConfidence(),
					scored.getScore(),
					scored.getBestMatch().getCompleteness()),
					logs,
					true,
					0
			);
		}
	}
	
}
