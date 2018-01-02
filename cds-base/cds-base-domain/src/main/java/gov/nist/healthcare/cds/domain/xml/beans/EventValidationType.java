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
 * <p>Java class for EventValidationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventValidationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VaccinationEvent" type="{https://fits.nist.gov/xml/}VaccinationEventReportType"/>
 *         &lt;element name="Evaluations" type="{https://fits.nist.gov/xml/}EvaluationsValidationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventValidationType", propOrder = {
    "vaccinationEvent",
    "evaluations"
})
public class EventValidationType {

    @XmlElement(name = "VaccinationEvent", required = true)
    protected VaccinationEventReportType vaccinationEvent;
    @XmlElement(name = "Evaluations", required = true)
    protected EvaluationsValidationType evaluations;

    /**
     * Gets the value of the vaccinationEvent property.
     * 
     * @return
     *     possible object is
     *     {@link VaccinationEventReportType }
     *     
     */
    public VaccinationEventReportType getVaccinationEvent() {
        return vaccinationEvent;
    }

    /**
     * Sets the value of the vaccinationEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link VaccinationEventReportType }
     *     
     */
    public void setVaccinationEvent(VaccinationEventReportType value) {
        this.vaccinationEvent = value;
    }

    /**
     * Gets the value of the evaluations property.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationsValidationType }
     *     
     */
    public EvaluationsValidationType getEvaluations() {
        return evaluations;
    }

    /**
     * Sets the value of the evaluations property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationsValidationType }
     *     
     */
    public void setEvaluations(EvaluationsValidationType value) {
        this.evaluations = value;
    }

}
