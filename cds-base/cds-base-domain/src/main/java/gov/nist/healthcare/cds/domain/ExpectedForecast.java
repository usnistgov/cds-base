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
public class ExpectedForecast implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8188917257211279163L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int doseNumber;
	private String forecastReason;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Date earliest;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Date recommended;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private Date pastDue;
	@ManyToOne(cascade = CascadeType.MERGE, optional = false)
	private Vaccine target;
	@Enumerated(EnumType.STRING)
	private SerieStatus serieStatus;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getDoseNumber() {
		return doseNumber;
	}
	public void setDoseNumber(int doseNumber) {
		this.doseNumber = doseNumber;
	}
	public String getForecastReason() {
		return forecastReason;
	}
	public void setForecastReason(String forecastReason) {
		this.forecastReason = forecastReason;
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
	public Vaccine getTarget() {
		return target;
	}
	public void setTarget(Vaccine target) {
		this.target = target;
	}
	public SerieStatus getSerieStatus() {
		return serieStatus;
	}
	public void setSerieStatus(SerieStatus serieStatus) {
		this.serieStatus = serieStatus;
	}
	
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
	
}
