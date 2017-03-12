package gov.nist.healthcare.cds.domain.wrapper;

public class ModelError {
	
	private String path;
	private String message;
	
	
	public ModelError(String path, String message) {
		super();
		this.path = path;
		this.message = message;
	}
		
	public ModelError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	

}
