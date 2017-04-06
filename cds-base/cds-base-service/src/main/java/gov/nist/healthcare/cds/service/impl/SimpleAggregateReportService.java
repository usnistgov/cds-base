package gov.nist.healthcare.cds.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import gov.nist.healthcare.cds.domain.wrapper.AggregateReport;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationValidation;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventValidation;
import gov.nist.healthcare.cds.enumeration.ValidationCriterion;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;
import gov.nist.healthcare.cds.service.AggregateReportService;

@Service
public class SimpleAggregateReportService implements AggregateReportService {

	@Override
	public AggregateReport aggregate(List<Report> reports) {
		AggregateReport report = new AggregateReport();
		boolean fc;
		boolean ev;
		for(Report r : reports){
			List<ForecastValidation> fvL = r.getFcValidation();
			List<VaccinationEventValidation> revL = r.getVeValidation();
			fc = this.browseFC(fvL, report);
			ev = this.browseEV(revL, report);
			report.setTotal(report.getTotal()+1);
			if(fc || ev){
				report.setErrors(report.getErrors()+1);
			}
			else {
				report.setCorrect(report.getCorrect()+1);
			}
		}
		return report;
	}
	
	private boolean browseFC(List<ForecastValidation> fvL, AggregateReport report){
		boolean hasErrors = false;
		for(ForecastValidation fv : fvL){
			report.add(ValidationCriterion.DoseNumber, fv.getDose());
			report.add(ValidationCriterion.EarliestDate, fv.getEarliest());
			report.add(ValidationCriterion.RecommendedDate, fv.getRecommended());
			report.add(ValidationCriterion.PastDueDate, fv.getPastDue());
			report.add(ValidationCriterion.CompleteDate, fv.getComplete());
			if(!hasErrors){
				hasErrors = fv.getDose().getStatus().equals(ValidationStatus.F) || 
						fv.getEarliest().getStatus().equals(ValidationStatus.F) ||
						fv.getRecommended().getStatus().equals(ValidationStatus.F) ||
						fv.getPastDue().getStatus().equals(ValidationStatus.F) ||
						fv.getComplete().getStatus().equals(ValidationStatus.F);
			}
		}
		return hasErrors;
	}
	
	private boolean browseEV(List<VaccinationEventValidation> revL, AggregateReport report){
		boolean hasErrors = false;
		for(VaccinationEventValidation ev : revL){
			for(EvaluationValidation e : ev.geteValidation()){
				report.add(ValidationCriterion.EvaluationStatus, e.getValidation());
				if(!hasErrors){
					hasErrors = e.getValidation().getStatus().equals(ValidationStatus.F);
				}	
			}
		}
		return hasErrors;
	}

}
