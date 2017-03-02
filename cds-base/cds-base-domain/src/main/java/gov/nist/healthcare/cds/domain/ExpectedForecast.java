package gov.nist.healthcare.cds.domain;


import gov.nist.healthcare.cds.enumeration.SerieStatus;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class ExpectedForecast extends Forecast implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808535673936520763L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	@ManyToOne
	private Vaccine target;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	protected Date earliest;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	protected Date recommended;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	protected Date pastDue;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	protected Date complete;
	@Enumerated(EnumType.STRING)
	protected SerieStatus serieStatus;
	
	
	public Vaccine getTarget() {
		return target;
	}
	public void setTarget(Vaccine target) {
		this.target = target;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getEarliest() {
		return earliest;
	}
	public void setEarliest(Date earliest) {
		this.earliest = earliest;
	}
	public Date getRecommended() {
		return recommended;
	}
	public void setRecommended(Date recommended) {
		this.recommended = recommended;
	}
	public Date getPastDue() {
		return pastDue;
	}
	public void setPastDue(Date pastDue) {
		this.pastDue = pastDue;
	}
	public Date getComplete() {
		return complete;
	}
	public void setComplete(Date complete) {
		this.complete = complete;
	}
	public SerieStatus getSerieStatus() {
		return serieStatus;
	}
	public void setSerieStatus(SerieStatus serieStatus) {
		this.serieStatus = serieStatus;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        ExpectedForecast that = (ExpectedForecast) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
    
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
	
}
