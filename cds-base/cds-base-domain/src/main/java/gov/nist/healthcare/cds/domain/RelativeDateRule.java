package gov.nist.healthcare.cds.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import gov.nist.healthcare.cds.enumeration.DatePosition;

public class RelativeDateRule {
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private DatePosition position;
	private int year;
	private int month;
	private int day;
	@NotNull
	@Valid
	private DateReference relativeTo;
	
	public RelativeDateRule(){
		
	}
	
	
	public RelativeDateRule(DatePosition position, int year, int month, int day, DateReference relativeTo) {
		super();
		this.position = position;
		this.year = year;
		this.month = month;
		this.day = day;
		this.relativeTo = relativeTo;
	}


	public DatePosition getPosition() {
		return position;
	}
	public void setPosition(DatePosition position) {
		this.position = position;
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
	public DateReference getRelativeTo() {
		return relativeTo;
	}
	public void setRelativeTo(DateReference relativeTo) {
		this.relativeTo = relativeTo;
	}
	
	
}
