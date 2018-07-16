package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;

public class DateCriterion extends Criterion {

	private FixedDate value;

	
	public DateCriterion(FixedDate value) {
		super();
		this.value = value;
	}
	
	public DateCriterion(java.util.Date value) {
		super();
		this.value = new FixedDate(value);
	}
	
	public DateCriterion() {
		super();
	}

	public DateCriterion(ValidationStatus status) {
		super(status);
	}


	public DateCriterion(ValidationStatus status, FixedDate dt) {
		super(status);
		this.value = dt;
	}
	
	public DateCriterion(ValidationStatus status, java.util.Date dt) {
		super(status);
		this.value = new FixedDate(dt);
	}
	
	public FixedDate getValue() {
		return value;
	}

	public void setValue(FixedDate value) {
		this.value = value;
	}
	
}
