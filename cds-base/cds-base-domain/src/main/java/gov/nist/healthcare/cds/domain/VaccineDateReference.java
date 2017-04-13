package gov.nist.healthcare.cds.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("dynamicReference")
public class VaccineDateReference extends DateReference {
	
	private int id;

	public VaccineDateReference(){
		
	}
	
	public VaccineDateReference(int id){
		this.setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
