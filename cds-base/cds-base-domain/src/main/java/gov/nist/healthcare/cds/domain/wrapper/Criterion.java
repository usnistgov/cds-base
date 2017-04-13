package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.ValidationStatus;

public class Criterion {
	
	private ValidationStatus status;

	
	public Criterion() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Criterion(ValidationStatus status) {
		super();
		this.status = status;
	}


	public ValidationStatus getStatus() {
		return status;
	}

	public void setStatus(ValidationStatus status) {
		this.status = status;
	}
	
}
