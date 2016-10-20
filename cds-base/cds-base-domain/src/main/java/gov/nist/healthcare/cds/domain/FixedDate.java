package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("FIXED")
@JsonTypeName("fixed")
public class FixedDate extends Date implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8561218443824434426L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;

	
	public FixedDate(){
		
	}
	
	public FixedDate(java.util.Date d){
		this.date = d;
	}
	
	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	

}
