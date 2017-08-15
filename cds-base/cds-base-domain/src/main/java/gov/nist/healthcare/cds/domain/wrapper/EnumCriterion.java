package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.ValidationStatus;


public class EnumCriterion extends Criterion {

	private String value;
	private String code;
	
	public EnumCriterion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EnumCriterion(ValidationStatus status, String value) {
		super(status);
		this.value = value;
	}

	public EnumCriterion(ValidationStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
