package gov.nist.healthcare.cds.domain.wrapper;

public class VaccineRef {
	private String cvx;
	private String mvx;
	private boolean hasMvx;
	
	public boolean isHasMvx() {
		return hasMvx;
	}
	public void setHasMvx(boolean hasMvx) {
		this.hasMvx = hasMvx;
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
