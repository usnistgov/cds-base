package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("GENERIC")
@JsonTypeName("generic")
public class Vaccine extends Injection implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8871233411947052965L;
	
	private String name;
	private String details;
	
	public String getCvx() {
		return id;
	}
	public void setCvx(String cvx) {
		this.id = cvx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Vaccine that = (Vaccine) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
    
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
}
