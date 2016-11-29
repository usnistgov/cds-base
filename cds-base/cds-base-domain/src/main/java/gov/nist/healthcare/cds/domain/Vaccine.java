package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Vaccine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5456108710476579961L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	private String details;
	private String cvx;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getCvx() {
		return cvx;
	}
	public void setCvx(String cvx) {
		this.cvx = cvx;
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
        Vaccine that = (Vaccine) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	
	

}
