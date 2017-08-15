package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class VaccineGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4539430668013568861L;
	@Id
	private String cvx;
	private String name;
	
	
	
	public VaccineGroup() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VaccineGroup(String cvx) {
		super();
		this.cvx = cvx;
	}

	public String getCvx() {
		return cvx;
	}
	public void setCvx(String cvx) {
		this.cvx = cvx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (cvx == null || obj == null || getClass() != obj.getClass())
            return false;
        VaccineGroup that = (VaccineGroup) obj;
        return cvx.equals(that.cvx);
    }
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
    @Override
    public int hashCode() {
        return cvx == null ? 0 : cvx.hashCode();
    }
	
	
}
