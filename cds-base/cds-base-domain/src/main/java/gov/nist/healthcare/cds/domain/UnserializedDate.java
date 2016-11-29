package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.RelativeTo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class UnserializedDate extends Date {

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date date;
	private String timePeriod;
	@Enumerated(EnumType.STRING)
	private RelativeTo relativeTo;
	
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	public RelativeTo getRelativeTo() {
		return relativeTo;
	}
	public void setRelativeTo(RelativeTo relativeTo) {
		this.relativeTo = relativeTo;
	}
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Date that = (Date) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	
	
}
