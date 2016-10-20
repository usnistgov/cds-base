package gov.nist.healthcare.cds.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Privilege {

	@Id
	private Long Id;
	@Column(nullable=false)
	private String role;
	
	public Privilege(){
		
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Privilege [Id=" + Id + ", role=" + role + "]";
	}
	
	
	
	
}
