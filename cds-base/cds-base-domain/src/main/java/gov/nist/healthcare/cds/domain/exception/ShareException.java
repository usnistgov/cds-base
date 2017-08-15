package gov.nist.healthcare.cds.domain.exception;

public class ShareException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3082375513839359879L;
	private String errcode;
	private String id;
	
	
	public ShareException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ShareException(String errcode, String id) {
		super();
		this.errcode = errcode;
		this.id = id;
	}


	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	
	

}
