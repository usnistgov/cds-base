package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public class EntityResult {

	private String name;
	private List<ErrorModel> errors;
	
	public EntityResult() {
		super();
	}
	public EntityResult(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ErrorModel> getErrors() {
		if(errors == null){
			errors = new ArrayList<>();
		}
		return errors;
	}
	public void setErrors(List<ErrorModel> errors) {
		this.errors = errors;
	}
	
	
}
