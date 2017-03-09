package gov.nist.healthcare.cds.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import gov.nist.healthcare.cds.domain.wrapper.AggregateReport;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationValidation;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventValidation;
import gov.nist.healthcare.cds.enumeration.ValidationCriterion;
import gov.nist.healthcare.cds.service.AggregateReportService;

@Service
public class SimpleAggregateReportService implements AggregateReportService {

	@Override
	public AggregateReport aggregate(List<Report> reports) {
		AggregateReport report = new AggregateReport();
		for(Report r : reports){
			List<ForecastValidation> fvL = r.getFcValidation();
			List<VaccinationEventValidation> revL = r.getVeValidation();
			this.browseFC(fvL, report);
			this.browseEV(revL, report);
		}
		return report;
	}
	
	private void browseFC(List<ForecastValidation> fvL, AggregateReport report){
		for(ForecastValidation fv : fvL){
			report.add(ValidationCriterion.DoseNumber, fv.getDose());
			report.add(ValidationCriterion.EarliestDate, fv.getEarliest());
			report.add(ValidationCriterion.RecommendedDate, fv.getRecommended());
			report.add(ValidationCriterion.PastDueDate, fv.getPastDue());
			report.add(ValidationCriterion.CompleteDate, fv.getComplete());
		}
	}
	
	private void browseEV(List<VaccinationEventValidation> revL, AggregateReport report){
		for(VaccinationEventValidation ev : revL){
			for(EvaluationValidation e : ev.geteValidation()){
				report.add(ValidationCriterion.EvaluationStatus, e.getValidation());
			}
		}
	}

}
