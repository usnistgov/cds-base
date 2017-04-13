package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;

public class VaccinationEventValidation {
	
	private VaccinationEventRequirement veRequirement;
	private List<EvaluationValidation> eValidation;
	private ResultCounts counts;
	
	public VaccinationEventRequirement getVeRequirement() {
		return veRequirement;
	}
	public void setVeRequirement(VaccinationEventRequirement veRequirement) {
		this.veRequirement = veRequirement;
	}
	public List<EvaluationValidation> geteValidation() {
		if(eValidation == null){
			eValidation = new ArrayList<>();
		}
		return eValidation;
	}
	public void seteValidation(List<EvaluationValidation> eValidation) {
		this.eValidation = eValidation;
	}
	public ResultCounts getCounts() {
		return counts;
	}
	public void setCounts(ResultCounts counts) {
		this.counts = counts;
	}
	
	public static VaccinationEventValidation unMatched(VaccinationEventRequirement ve) {
		VaccinationEventValidation vev = new VaccinationEventValidation();
		ResultCounts counts = new ResultCounts();
		vev.setCounts(counts);
		vev.setVeRequirement(ve);
		for(ExpectedEvaluation ee : ve.getvEvent().getEvaluations()){
			vev.geteValidation().add(new EvaluationValidation(ee, new EvaluationCriterion(ValidationStatus.U)));
			counts.addU();
		}
		return vev;
	}
	
}
