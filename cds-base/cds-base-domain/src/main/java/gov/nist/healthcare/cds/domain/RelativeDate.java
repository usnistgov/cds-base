package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.RelativeTo;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("RELATIVE")
@JsonTypeName("relative")
public class RelativeDate extends Date implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String timePeriod;
	@Enumerated(EnumType.STRING)
	private RelativeTo relativeTo;
	
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
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
