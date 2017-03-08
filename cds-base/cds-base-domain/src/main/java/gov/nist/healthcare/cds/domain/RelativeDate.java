package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.RelativeTo;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("relative")
public class RelativeDate extends Date implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int year;
	private int month;
	private int day;
	@Enumerated(EnumType.STRING)
	private RelativeTo relativeTo;

	public RelativeTo getRelativeTo() {
		return relativeTo;
	}

	public void setRelativeTo(RelativeTo relativeTo) {
		this.relativeTo = relativeTo;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	
	
}
