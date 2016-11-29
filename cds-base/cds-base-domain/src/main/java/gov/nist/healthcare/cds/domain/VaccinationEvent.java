package gov.nist.healthcare.cds.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("VACCINATION")
@JsonTypeName("vaccination")
public class VaccinationEvent extends Event {

	private int doseNumber = 1;
	private String MVX;
	@ManyToOne
	private Vaccine administred;
	@OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
	private List<ExpectedEvaluation> evaluations;
	
	public int getDoseNumber() {
		return doseNumber;
	}
	public void setDoseNumber(int doseNumber) {
		this.doseNumber = doseNumber;
	}
	public String getMVX() {
		return MVX;
	}
	public void setMVX(String mVX) {
		MVX = mVX;
	}
	public Vaccine getAdministred() {
		return administred;
	}
	public void setAdministred(Vaccine administred) {
		this.administred = administred;
	}
	public List<ExpectedEvaluation> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<ExpectedEvaluation> evaluations) {
		this.evaluations = evaluations;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
