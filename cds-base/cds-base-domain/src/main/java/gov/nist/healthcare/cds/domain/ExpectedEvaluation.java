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

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class ExpectedEvaluation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602014579808041159L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String evaluationReason;
	@ManyToOne
	private Vaccine relatedTo;
	@Enumerated(EnumType.STRING)
	private EvaluationStatus status;
	@Enumerated(EnumType.STRING)
	private EvaluationReason reason;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEvaluationReason() {
		return evaluationReason;
	}
	public void setEvaluationReason(String evaluationReason) {
		this.evaluationReason = evaluationReason;
	}
	public Vaccine getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(Vaccine relatedTo) {
		this.relatedTo = relatedTo;
	}
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
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        ExpectedEvaluation that = (ExpectedEvaluation) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	public EvaluationReason getReason() {
		return reason;
	}
	public void setReason(EvaluationReason reason) {
		this.reason = reason;
	}
    
}
