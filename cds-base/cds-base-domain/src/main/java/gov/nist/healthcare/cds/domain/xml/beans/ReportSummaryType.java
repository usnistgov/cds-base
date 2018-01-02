//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.01 at 03:57:52 PM EST 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReportSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReportSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Evaluations" type="{https://fits.nist.gov/xml/}SummaryType"/>
 *         &lt;element name="Forecasts" type="{https://fits.nist.gov/xml/}SummaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportSummaryType", propOrder = {
    "evaluations",
    "forecasts"
})
public class ReportSummaryType {

    @XmlElement(name = "Evaluations", required = true)
    protected SummaryType evaluations;
    @XmlElement(name = "Forecasts", required = true)
    protected SummaryType forecasts;

    /**
     * Gets the value of the evaluations property.
     * 
     * @return
     *     possible object is
     *     {@link SummaryType }
     *     
     */
    public SummaryType getEvaluations() {
        return evaluations;
    }

    /**
     * Sets the value of the evaluations property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaryType }
     *     
     */
    public void setEvaluations(SummaryType value) {
        this.evaluations = value;
    }

    /**
     * Gets the value of the forecasts property.
     * 
     * @return
     *     possible object is
     *     {@link SummaryType }
     *     
     */
    public SummaryType getForecasts() {
        return forecasts;
    }

    /**
     * Sets the value of the forecasts property.
     * 
     * @param value
     *     allowed object is
     *     {@link SummaryType }
     *     
     */
    public void setForecasts(SummaryType value) {
        this.forecasts = value;
    }

}
