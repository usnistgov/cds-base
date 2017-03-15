package gov.nist.healthcare.cds.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import gov.nist.healthcare.cds.enumeration.RelativeTo;


@JsonTypeName("static")
public class StaticDateReference extends DateReference {
	
	@Enumerated(EnumType.STRING)
	private RelativeTo id;

	
	public StaticDateReference() {
		super();
		// TODO Auto-generated constructor stub
	}


	public StaticDateReference(RelativeTo type) {
		super();
		this.id = type;
	}


	public RelativeTo getId() {
		return id;
	}


	public void setId(RelativeTo id) {
		this.id = id;
	}
	
	

	
}
