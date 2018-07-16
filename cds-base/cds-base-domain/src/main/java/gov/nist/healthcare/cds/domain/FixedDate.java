package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("fixed")
public class FixedDate extends Date implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8561218443824434426L;
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	@NotNull(message = "Fixed Date is required")
	private String dateStr;
	@Temporal(TemporalType.TIMESTAMP)
	@Deprecated
	private java.util.Date date;

	
	public FixedDate(){
		
	}
	
	public FixedDate(String date){
		try {
			DATE_FORMAT.parse(date);
			this.dateStr = date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public FixedDate(java.util.Date date){
		this.dateStr = DATE_FORMAT.format(date);
	}

	public String getDateString() {
		return dateStr;
	}

	public void setDateString(String date) {
		this.dateStr = date;
	}
	
	public java.util.Date asDate() {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	

}
