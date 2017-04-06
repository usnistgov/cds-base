package gov.nist.healthcare.cds.domain.wrapper;

import java.io.Serializable;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.ToStringBuilder;

public class MetaData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6944914219990941855L;
	
	private String version;
	private boolean imported;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date dateLastUpdated;
	private String changeLog;

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isImported() {
		return imported;
	}
	public void setImported(boolean imported) {
		this.imported = imported;
	}
	public String getChangeLog() {
		return changeLog;
	}
	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
	public java.util.Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public java.util.Date getDateLastUpdated() {
		return dateLastUpdated;
	}
	public void setDateLastUpdated(java.util.Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
}
