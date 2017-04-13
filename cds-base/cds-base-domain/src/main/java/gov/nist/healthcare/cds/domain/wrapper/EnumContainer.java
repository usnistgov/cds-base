package gov.nist.healthcare.cds.domain.wrapper;

public class EnumContainer {
	private String code;
	private String details;
	
	
	public EnumContainer(String code, String details) {
		super();
		this.code = code;
		this.details = details;
	}
	
	
	public EnumContainer() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
