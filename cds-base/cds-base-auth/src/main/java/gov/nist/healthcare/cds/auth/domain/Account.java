package gov.nist.healthcare.cds.auth.domain;

import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Account {

	@Id
	private String id;
//	@Column(nullable=false)
	private String username;
//	@Column(nullable=false)
	private String password;
//	@Column(nullable=false)
	private String email;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//	@JoinTable(name = "AccountRole",
//			   joinColumns = @JoinColumn(
//					   name = "accountId",
//					   referencedColumnName = "id"),
//				inverseJoinColumns = @JoinColumn(
//						name = "roleId",
//						referencedColumnName = "id"))
	@DBRef
	private Set<Privilege> privileges;
	
	public Account(){
		
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
