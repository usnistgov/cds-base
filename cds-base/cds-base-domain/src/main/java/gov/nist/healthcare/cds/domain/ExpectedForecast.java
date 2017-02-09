package gov.nist.healthcare.cds.domain;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class ExpectedForecast extends Forecast implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808535673936520763L;
	
	@ManyToOne
	private Vaccine target;

	public Vaccine getTarget() {
		return target;
	}
	public void setTarget(Vaccine target) {
		this.target = target;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
}
