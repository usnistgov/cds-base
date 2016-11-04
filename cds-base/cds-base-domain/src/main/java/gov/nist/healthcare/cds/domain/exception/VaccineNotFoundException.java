package gov.nist.healthcare.cds.domain.exception;

public class VaccineNotFoundException extends Exception {

	private String cvx;

	public VaccineNotFoundException(String cvx) {
		super();
		this.cvx = cvx;
	}

	public String getCvx() {
		return cvx;
	}

	public void setCvx(String cvx) {
		this.cvx = cvx;
	}
	
	
}
