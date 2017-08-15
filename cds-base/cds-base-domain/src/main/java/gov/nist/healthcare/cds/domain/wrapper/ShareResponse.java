package gov.nist.healthcare.cds.domain.wrapper;

public class ShareResponse {
	
	private boolean status;
	private String code;
	private String id;
	
	
	public ShareResponse(boolean status, String code, String id) {
		super();
		this.status = status;
		this.code = code;
		this.id = id;
	}
	
	public ShareResponse() {
		super();
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
