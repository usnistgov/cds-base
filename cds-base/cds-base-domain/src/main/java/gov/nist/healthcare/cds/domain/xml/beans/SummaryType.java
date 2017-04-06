//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.02 at 09:33:03 PM EDT 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Correct" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Errors" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Warnings" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="Incomplete" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CompletionPercentage" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CorrectnessPercentage" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummaryType", propOrder = {
    "correct",
    "errors",
    "warnings",
    "incomplete",
    "completionPercentage",
    "correctnessPercentage"
})
public class SummaryType {

    @XmlElement(name = "Correct", required = true)
    protected BigDecimal correct;
    @XmlElement(name = "Errors", required = true)
    protected BigDecimal errors;
    @XmlElement(name = "Warnings", required = true)
    protected BigDecimal warnings;
    @XmlElement(name = "Incomplete", required = true)
    protected BigDecimal incomplete;
    @XmlElement(name = "CompletionPercentage", required = true)
    protected BigDecimal completionPercentage;
    @XmlElement(name = "CorrectnessPercentage", required = true)
    protected BigDecimal correctnessPercentage;

    /**
     * Gets the value of the correct property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCorrect() {
        return correct;
    }

    /**
     * Sets the value of the correct property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCorrect(BigDecimal value) {
        this.correct = value;
    }

    /**
     * Gets the value of the errors property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setErrors(BigDecimal value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setWarnings(BigDecimal value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the incomplete property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIncomplete() {
        return incomplete;
    }

    /**
     * Sets the value of the incomplete property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIncomplete(BigDecimal value) {
        this.incomplete = value;
    }

    /**
     * Gets the value of the completionPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCompletionPercentage() {
        return completionPercentage;
    }

    /**
     * Sets the value of the completionPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCompletionPercentage(BigDecimal value) {
        this.completionPercentage = value;
    }

    /**
     * Gets the value of the correctnessPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCorrectnessPercentage() {
        return correctnessPercentage;
    }

    /**
     * Sets the value of the correctnessPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCorrectnessPercentage(BigDecimal value) {
        this.correctnessPercentage = value;
    }

}
