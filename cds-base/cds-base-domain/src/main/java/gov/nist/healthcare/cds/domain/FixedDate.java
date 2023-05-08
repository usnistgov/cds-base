package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("fixed")
public class FixedDate extends Date implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8561218443824434426L;
	public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	@NotNull(message = "Fixed Date is required")
	private String dateStr;
	
	public FixedDate(){
		
	}
	
	public FixedDate(String date){
		formatter.parse(date);
		this.dateStr = date;
	}

	public FixedDate(LocalDate date){
		this.dateStr = formatter.format(date);
	}

	public String getDateString() {
		return dateStr;
	}

	public void setDateString(String date) {
		this.dateStr = date;
	}
	
	public LocalDate asDate() {
		try {
			return LocalDate.parse(dateStr, formatter);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	

}
