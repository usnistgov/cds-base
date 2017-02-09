package gov.nist.healthcare.cds.domain;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class ExpectedEvaluation extends Evaluation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7602014579808041159L;
	
	@ManyToOne
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
    
}
