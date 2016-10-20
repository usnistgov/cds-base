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

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class Patient implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3581693775619531483L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String firstName;
	private String lastName;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Date dob;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@OneToOne
	private TestCase testCase;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public TestCase getTestCase() {
		return testCase;
	}
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
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
