package gov.nist.healthcare.cds.domain.exception;

public class ProductNotFoundException extends Exception {

	private String cvx;
	private String mvx;
	
	public ProductNotFoundException(String cvx, String mvx) {
		super();
		this.cvx = cvx;
		this.mvx = mvx;
	}

	public String getCvx() {
		return cvx;
	}

	public void setCvx(String cvx) {
		this.cvx = cvx;
	}

	public String getMvx() {
		return mvx;
	}

	public void setMvx(String mvx) {
		this.mvx = mvx;
	}
	
}
