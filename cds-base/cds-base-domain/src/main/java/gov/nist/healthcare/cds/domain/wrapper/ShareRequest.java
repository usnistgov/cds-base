package gov.nist.healthcare.cds.domain.wrapper;

public class ShareRequest {
	
	private String userId;
	private String tpId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTpId() {
		return tpId;
	}
	public void setTpId(String tpId) {
		this.tpId = tpId;
	}
}
