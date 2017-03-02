package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;

import java.util.Date;

public class DateCriterion extends Criterion {

	private Date value;

	
	public DateCriterion(Date value) {
		super();
		this.value = value;
	}
	
	

	public DateCriterion() {
		super();
		// TODO Auto-generated constructor stub
	}



	public DateCriterion(ValidationStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}


	public DateCriterion(ValidationStatus status, Date dt) {
		super(status);
		this.value = dt;
		// TODO Auto-generated constructor stub
	}

	public DateCriterion(ValidationStatus f,
			gov.nist.healthcare.cds.domain.Date dt) {
		super(f);
		this.value = ((FixedDate) dt).getDate();
		// TODO Auto-generated constructor stub
	}



	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}
	
	
}
