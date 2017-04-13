package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.ValidationStatus;

public class ForecastValidation {
	
	private ForecastRequirement forecastRequirement;
	private StringCriterion dose;
	private DateCriterion earliest;
	private DateCriterion recommended;
	private DateCriterion pastDue;
	private DateCriterion complete;
	private ResultCounts counts;
	
	public static ForecastValidation unMatched(ForecastRequirement fr){
		ForecastValidation fv = new ForecastValidation();
		ResultCounts counts = new ResultCounts();
		fv.setForecastRequirement(fr);
		fv.setCounts(counts);
		
		// Dose
		if(fr.getExpForecast().getDoseNumber().isEmpty()){
			fv.setDose(new StringCriterion(ValidationStatus.N));
		}
		else {
			fv.setDose(new StringCriterion(ValidationStatus.U));
			counts.addU();
		}
		
		// Earliest
		if(fr.getEarliest() == null){
			fv.setEarliest(new DateCriterion(ValidationStatus.N));
		}
		else {
			fv.setEarliest(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		
		// Recommended
		if(fr.getRecommended() == null){
			fv.setRecommended(new DateCriterion(ValidationStatus.N));
		}
		else {
			fv.setRecommended(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		
		// pastDue
		if(fr.getPastDue() == null){
			fv.setPastDue(new DateCriterion(ValidationStatus.N));
		}
		else {
			fv.setPastDue(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		
		// Complete
		if(fr.getComplete() == null){
			fv.setComplete(new DateCriterion(ValidationStatus.N));
		}
		else {
			fv.setComplete(new DateCriterion(ValidationStatus.U));
			counts.addU();
		}
		
		return fv;
	}	
	public ForecastRequirement getForecastRequirement() {
		return forecastRequirement;
	}
	public void setForecastRequirement(ForecastRequirement forecastRequirement) {
		this.forecastRequirement = forecastRequirement;
	}
	public StringCriterion getDose() {
		return dose;
	}
	public void setDose(StringCriterion dose) {
		this.dose = dose;
	}
	public DateCriterion getEarliest() {
		return earliest;
	}
	public void setEarliest(DateCriterion earliest) {
		this.earliest = earliest;
	}
	public DateCriterion getRecommended() {
		return recommended;
	}
	public void setRecommended(DateCriterion recommended) {
		this.recommended = recommended;
	}
	public DateCriterion getPastDue() {
		return pastDue;
	}
	public void setPastDue(DateCriterion pastDue) {
		this.pastDue = pastDue;
	}
	public DateCriterion getComplete() {
		return complete;
	}
	public void setComplete(DateCriterion complete) {
		this.complete = complete;
	}
	public ResultCounts getCounts() {
		return counts;
	}
	public void setCounts(ResultCounts counts) {
		this.counts = counts;
	}
	
	
}
