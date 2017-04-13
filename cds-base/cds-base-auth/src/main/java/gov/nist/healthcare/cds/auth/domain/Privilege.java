package gov.nist.healthcare.cds.auth.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class Privilege {

	@Id
	private String Id;
	private String role;
	
	public Privilege(){
		
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
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
