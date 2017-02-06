package gov.nist.healthcare.cds.domain;


import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import gov.nist.healthcare.cds.enumeration.EventType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="eventType")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT,
        property = "event")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VaccinationEvent.class, name = "vaccination")
})
public abstract class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	protected Long id;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Date date;
	@Enumerated
	private EventType type;

	public Long getId() {
		return id;
	}
	
	

	public EventType getType() {
		return type;
	}



	public void setType(EventType type) {
		this.type = type;
	}



	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Event that = (Event) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	
}
