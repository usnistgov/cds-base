package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("relative")
public class RelativeDate extends Date implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotNull(message = "Relative Date must is required")
	@Valid
	@Size(min=1,message = "Relative Date must have at least one rule")
	private List<RelativeDateRule> rules;

	

	public List<RelativeDateRule> getRules() {
		return rules;
	}



	public void setRules(List<RelativeDateRule> rules) {
		this.rules = rules;
	}



	public boolean add(RelativeDateRule e) {
		if(rules == null){
			rules = new ArrayList<>();
		}
		return rules.add(e);
	}



	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
}
