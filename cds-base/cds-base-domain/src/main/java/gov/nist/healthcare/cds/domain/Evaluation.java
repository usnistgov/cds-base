package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.EvaluationReason;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Evaluation implements Serializable {

	@NotNull(message = "Evaluation must have an expected status")
	@Valid
	@Enumerated(EnumType.STRING)
	protected EvaluationStatus status;
	@Enumerated(EnumType.STRING)
	protected EvaluationReason reason;
	
	public EvaluationStatus getStatus() {
		return status;
	}
	public void setStatus(EvaluationStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}

	public EvaluationReason getReason() {
		return reason;
	}
	public void setReason(EvaluationReason reason) {
		this.reason = reason;
	}
    
}
