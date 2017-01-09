package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manufacturer implements Serializable {

	@Id
	private String mvx;
	private String name;
	
	public String getMvx() {
		return mvx;
	}
	public void setMvx(String mvx) {
		this.mvx = mvx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
