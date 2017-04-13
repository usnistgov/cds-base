package gov.nist.healthcare.cds.domain.wrapper;

import java.util.ArrayList;
import java.util.List;


public class ModelValidationFailed {

	private boolean status;
	private List<ModelError> errors;
	
	
	public ModelValidationFailed() {
		super();
		this.status = false;
		this.errors = new ArrayList<>();
		// TODO Auto-generated constructor stub
	}
	public boolean add(ModelError e) {
		return errors.add(e);
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<ModelError> getErrors() {
		return errors;
	}
	public void setErrors(List<ModelError> errors) {
		this.errors = errors;
	}
	
	
}
