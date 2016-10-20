package gov.nist.healthcare.cds.domain;


import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.commons.lang.builder.ToStringBuilder;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DATE_TYPE")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedDate.class, name = "fixed"),
        @JsonSubTypes.Type(value = RelativeDate.class, name = "relative")
})
public abstract class Date implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -833018968888415267L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
}
