//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.02 at 09:33:03 PM EDT 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ForecastType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForecastType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="target" type="{http://www.example.org/testCase/}VaccineType"/>
 *         &lt;element name="SerieStatus" type="{http://www.example.org/testCase/}SerieStatusType" minOccurs="0"/>
 *         &lt;element name="ForecastReason" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Earliest" type="{http://www.example.org/testCase/}DateType"/>
 *         &lt;element name="Recommended" type="{http://www.example.org/testCase/}DateType"/>
 *         &lt;element name="PastDue" type="{http://www.example.org/testCase/}DateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastType", propOrder = {
    "target",
    "serieStatus",
    "forecastReason",
    "earliest",
    "recommended",
    "pastDue"
})
public class ForecastType {

    @XmlElement(required = true)
    protected VaccineType target;
    @XmlElement(name = "SerieStatus")
    protected SerieStatusType serieStatus;
    @XmlElement(name = "ForecastReason")
    protected String forecastReason;
    @XmlElement(name = "Earliest", required = true)
    protected DateType earliest;
    @XmlElement(name = "Recommended", required = true)
    protected DateType recommended;
    @XmlElement(name = "PastDue", required = true)
    protected DateType pastDue;

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link VaccineType }
     *     
     */
    public VaccineType getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccineType }
     *     
     */
    public void setTarget(VaccineType value) {
        this.target = value;
    }

    /**
     * Gets the value of the serieStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SerieStatusType }
     *     
     */
    public SerieStatusType getSerieStatus() {
        return serieStatus;
    }

    /**
     * Sets the value of the serieStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SerieStatusType }
     *     
     */
    public void setSerieStatus(SerieStatusType value) {
        this.serieStatus = value;
    }

    /**
     * Gets the value of the forecastReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getForecastReason() {
        return forecastReason;
    }

    /**
     * Sets the value of the forecastReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setForecastReason(String value) {
        this.forecastReason = value;
    }

    /**
     * Gets the value of the earliest property.
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getEarliest() {
        return earliest;
    }

    /**
     * Sets the value of the earliest property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setEarliest(DateType value) {
        this.earliest = value;
    }

    /**
     * Gets the value of the recommended property.
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getRecommended() {
        return recommended;
    }

    /**
     * Sets the value of the recommended property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setRecommended(DateType value) {
        this.recommended = value;
    }

    /**
     * Gets the value of the pastDue property.
     * 
     * @return
     *     possible object is
     *     {@link DateType }
     *     
     */
    public DateType getPastDue() {
        return pastDue;
    }

    /**
     * Sets the value of the pastDue property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateType }
     *     
     */
    public void setPastDue(DateType value) {
        this.pastDue = value;
    }

}
