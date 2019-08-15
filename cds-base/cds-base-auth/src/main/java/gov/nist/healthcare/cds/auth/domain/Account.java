package gov.nist.healthcare.cds.auth.domain;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Document
public class Account {

	@Id
	private String id;
//	@Column(nullable=false)
	private String username;
//	@Column(nullable=false)
//	@JsonIgnore
	private String password;
//	@Column(nullable=false)

	private String email;

	private boolean pending = false;

	private String accountType;

	private String fullName;

	private String organization;
	
	private Boolean signedConfidentialityAgreement = false;

	@DBRef
	private Set<Privilege> privileges;
	
	public Account(){
		
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public boolean isPending() {
		return pending;
	}



	public void setPending(boolean pending) {
		this.pending = pending;
	}



	public String getAccountType() {
		return accountType;
	}



	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public Boolean getSignedConfidentialityAgreement() {
		return signedConfidentialityAgreement;
	}



	public void setSignedConfidentialityAgreement(Boolean signedConfidentialityAgreement) {
		this.signedConfidentialityAgreement = signedConfidentialityAgreement;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public Set<Privilege> getPrivileges() {
		return privileges;
	}


	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	
}
