package gov.nist.healthcare.cds.domain;


import java.io.Serializable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Forecast implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808535673936520763L;
	

	protected String doseNumber = "#";
	protected String forecastReason;
	
	
	

	public String getForecastReason() {
		return forecastReason;
	}
	public void setForecastReason(String forecastReason) {
		this.forecastReason = forecastReason;
	}	
    public String getDoseNumber() {
		return doseNumber;
	}

	public void setDoseNumber(String doseNumber) {
		this.doseNumber = doseNumber;
	}
}
