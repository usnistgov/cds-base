package gov.nist.healthcare.cds.domain.wrapper;


import gov.nist.healthcare.cds.domain.Evaluation;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ActualEvaluation extends Evaluation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VaccineRef vaccine;
	
	public VaccineRef getVaccine() {
		return vaccine;
	}

	public void setVaccine(VaccineRef vaccine) {
		this.vaccine = vaccine;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
}
