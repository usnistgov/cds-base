package gov.nist.healthcare.cds.domain.wrapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import gov.nist.healthcare.cds.enumeration.Gender;

public class TestCasePayLoad {
	
	public class VaccinationEventPayLoad {
		private VaccineRef ref;
		private LocalDate administred;
		
		public VaccineRef getRef() {
			return ref;
		}
		public void setRef(VaccineRef ref) {
			this.ref = ref;
		}
		public LocalDate getAdministred() {
			return administred;
		}
		public void setAdministred(LocalDate administred) {
			this.administred = administred;
		}
	}
	private String testCaseId;
	private String testCaseNumber;
	private Gender gender;
	private LocalDate dateOfBirth;
	private LocalDate evaluationDate;
	private List<VaccinationEventPayLoad> immunizations;
	
	
	public TestCasePayLoad() {
		super();
		this.immunizations = new ArrayList<VaccinationEventPayLoad>();
	}
	
	public void addImmunization(VaccineRef vr, LocalDate d){
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
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<VaccinationEventPayLoad> getImmunizations() {
		return immunizations;
	}
	public void setImmunizations(List<VaccinationEventPayLoad> immunizations) {
		this.immunizations = immunizations;
	}
	public LocalDate getEvaluationDate() {
		return evaluationDate;
	}
	public void setEvaluationDate(LocalDate evaluationDate) {
		this.evaluationDate = evaluationDate;
	}
	public String getTestCaseNumber() {
		return testCaseNumber;
	}
	public void setTestCaseNumber(String testCaseNumber) {
		this.testCaseNumber = testCaseNumber;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
