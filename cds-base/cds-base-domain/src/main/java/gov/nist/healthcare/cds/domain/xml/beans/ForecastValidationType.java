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
 * <p>Java class for ForecastValidationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ForecastValidationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Target" type="{http://www.example.org/testCase/}VaccineType"/>
 *         &lt;element name="DoseNumber" type="{http://www.example.org/testCase/}DoseValidationType"/>
 *         &lt;element name="EarliestDate" type="{http://www.example.org/testCase/}DateValidationType"/>
 *         &lt;element name="RecommendedDate" type="{http://www.example.org/testCase/}DateValidationType"/>
 *         &lt;element name="PastDueDate" type="{http://www.example.org/testCase/}DateValidationType"/>
 *         &lt;element name="LatestDate" type="{http://www.example.org/testCase/}DateValidationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ForecastValidationType", propOrder = {
    "target",
    "doseNumber",
    "earliestDate",
    "recommendedDate",
    "pastDueDate",
    "latestDate"
})
public class ForecastValidationType {

    @XmlElement(name = "Target", required = true)
    protected VaccineType target;
    @XmlElement(name = "DoseNumber", required = true)
    protected DoseValidationType doseNumber;
    @XmlElement(name = "EarliestDate", required = true)
    protected DateValidationType earliestDate;
    @XmlElement(name = "RecommendedDate", required = true)
    protected DateValidationType recommendedDate;
    @XmlElement(name = "PastDueDate", required = true)
    protected DateValidationType pastDueDate;
    @XmlElement(name = "LatestDate", required = true)
    protected DateValidationType latestDate;

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
     * Gets the value of the doseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link DoseValidationType }
     *     
     */
    public DoseValidationType getDoseNumber() {
        return doseNumber;
    }

    /**
     * Sets the value of the doseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link DoseValidationType }
     *     
     */
    public void setDoseNumber(DoseValidationType value) {
        this.doseNumber = value;
    }

    /**
     * Gets the value of the earliestDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateValidationType }
     *     
     */
    public DateValidationType getEarliestDate() {
        return earliestDate;
    }

    /**
     * Sets the value of the earliestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateValidationType }
     *     
     */
    public void setEarliestDate(DateValidationType value) {
        this.earliestDate = value;
    }

    /**
     * Gets the value of the recommendedDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateValidationType }
     *     
     */
    public DateValidationType getRecommendedDate() {
        return recommendedDate;
    }

    /**
     * Sets the value of the recommendedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateValidationType }
     *     
     */
    public void setRecommendedDate(DateValidationType value) {
        this.recommendedDate = value;
    }

    /**
     * Gets the value of the pastDueDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateValidationType }
     *     
     */
    public DateValidationType getPastDueDate() {
        return pastDueDate;
    }

    /**
     * Sets the value of the pastDueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateValidationType }
     *     
     */
    public void setPastDueDate(DateValidationType value) {
        this.pastDueDate = value;
    }

    /**
     * Gets the value of the latestDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateValidationType }
     *     
     */
    public DateValidationType getLatestDate() {
        return latestDate;
    }

    /**
     * Sets the value of the latestDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateValidationType }
     *     
     */
    public void setLatestDate(DateValidationType value) {
        this.latestDate = value;
    }

}