package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.Forecast;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ActualForecast extends Forecast {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VaccineRef vaccine;
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	public VaccineRef getVaccine() {
		return vaccine;
	}

	public void setVaccine(VaccineRef vaccine) {
		this.vaccine = vaccine;
	}
	
	
}
