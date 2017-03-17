package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.Gender;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Patient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3581693775619531483L;
	
	@NotNull(message = "Patient Date of Birth is required")
	private Date dob;
	@NotNull(message = "Patient Gender is required")
	private Gender gender;
	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
}
