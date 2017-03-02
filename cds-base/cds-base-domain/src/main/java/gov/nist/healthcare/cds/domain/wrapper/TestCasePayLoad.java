package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import gov.nist.healthcare.cds.enumeration.Gender;

public class TestCasePayLoad {
	
	public class VaccinationEventPayLoad {
		private VaccineRef ref;
		private Date administred;
		
		public VaccineRef getRef() {
			return ref;
		}
		public void setRef(VaccineRef ref) {
			this.ref = ref;
		}
		public Date getAdministred() {
			return administred;
		}
		public void setAdministred(Date administred) {
			this.administred = administred;
		}
	}
	
	private Gender gender;
	private Date dateOfBirth;
	private Date evaluationDate;
	private List<VaccinationEventPayLoad> immunizations;
	
	
	public TestCasePayLoad() {
		super();
		this.immunizations = new ArrayList<VaccinationEventPayLoad>();
	}
	
	public void addImmunization(VaccineRef vr, Date d){
		VaccinationEventPayLoad vep = new VaccinationEventPayLoad();
		vep.setAdministred(d);
		vep.setRef(vr);
		this.immunizations.add(vep);
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<VaccinationEventPayLoad> getImmunizations() {
		return immunizations;
	}
	public void setImmunizations(List<VaccinationEventPayLoad> immunizations) {
		this.immunizations = immunizations;
	}
	public Date getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(Date evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
