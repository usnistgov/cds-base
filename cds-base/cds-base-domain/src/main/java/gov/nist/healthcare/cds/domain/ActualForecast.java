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

@Entity
public class ActualForecast implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6665951930928860474L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String doseNumber;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	private FixedDate earliest;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	private FixedDate recommended;
	@OneToOne(cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
	private FixedDate pastDue;
	@ManyToOne
	private Vaccine target;
	@Enumerated(EnumType.STRING)
	private SerieStatus serieStatus;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDoseNumber() {
		return doseNumber;
	}
	public void setDoseNumber(String doseNumber) {
		this.doseNumber = doseNumber;
	}
	public FixedDate getEarliest() {
		return earliest;
	}
	public void setEarliest(FixedDate earliest) {
		this.earliest = earliest;
	}
	public FixedDate getRecommended() {
		return recommended;
	}
	public void setRecommended(FixedDate recommended) {
		this.recommended = recommended;
	}
	public FixedDate getPastDue() {
		return pastDue;
	}
	public void setPastDue(FixedDate pastDue) {
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
	
	

}
