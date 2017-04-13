package gov.nist.healthcare.cds.domain.wrapper;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ExecutionConfig {

	private String software;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	
	
}
