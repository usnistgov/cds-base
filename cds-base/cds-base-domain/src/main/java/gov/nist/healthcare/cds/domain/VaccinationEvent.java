package gov.nist.healthcare.cds.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonTypeName;

@DiscriminatorValue("VACCINATION")
@JsonTypeName("vaccination")
public class VaccinationEvent extends Event {

	private int doseNumber = 1;
	@NotNull
	@Valid
	private Injection administred;
	private Set<ExpectedEvaluation> evaluations;
	private int position;
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getDoseNumber() {
		return doseNumber;
	}
	public void setDoseNumber(int doseNumber) {
		this.doseNumber = doseNumber;
	}
	public Injection getAdministred() {
		return administred;
	}
	public void setAdministred(Injection administred) {
		this.administred = administred;
	}
	public Set<ExpectedEvaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(Set<ExpectedEvaluation> evaluations) {
		this.evaluations = evaluations;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
