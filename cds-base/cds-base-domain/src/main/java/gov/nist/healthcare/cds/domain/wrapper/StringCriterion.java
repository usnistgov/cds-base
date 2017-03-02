package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.ValidationStatus;


public class StringCriterion extends Criterion {

	private String value;

	
	public StringCriterion() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public StringCriterion(ValidationStatus status, String value) {
		super(status);
		this.value = value;
	}


	public StringCriterion(ValidationStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
