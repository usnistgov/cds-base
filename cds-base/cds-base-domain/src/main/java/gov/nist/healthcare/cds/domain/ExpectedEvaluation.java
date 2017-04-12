package gov.nist.healthcare.cds.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ExpectedEvaluation extends Evaluation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602014579808041159L;
	
	@NotNull(message = "Evaluation must have a target Vaccine (cvx code)")
	@Valid
	private Vaccine relatedTo;

	public Vaccine getRelatedTo() {
		return relatedTo;
	}
	
	public void setRelatedTo(Vaccine relatedTo) {
		this.relatedTo = relatedTo;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
    
}
