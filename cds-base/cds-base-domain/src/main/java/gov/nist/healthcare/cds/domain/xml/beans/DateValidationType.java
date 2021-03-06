//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.01 at 03:57:52 PM EST 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DateValidationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateValidationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Expected" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Actual" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Assessment" type="{https://fits.nist.gov/xml/}validationStatus"/>
 *         &lt;element name="DaysOffset" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateValidationType", propOrder = {
    "expected",
    "actual",
    "assessment",
    "daysOffset"
})
public class DateValidationType {

    @XmlElement(name = "Expected", required = true)
    @XmlSchemaType(name = "date")
    protected String expected;
    @XmlElement(name = "Actual", required = true)
    @XmlSchemaType(name = "date")
    protected String actual;
    @XmlElement(name = "Assessment", required = true)
    protected ValidationStatus assessment;
    @XmlElement(name = "DaysOffset", required = true)
    protected BigInteger daysOffset;

    /**
     * Gets the value of the expected property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getExpected() {
        return expected;
    }

    /**
     * Sets the value of the expected property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpected(String value) {
        this.expected = value;
    }

    /**
     * Gets the value of the actual property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public String getActual() {
        return actual;
    }

    /**
     * Sets the value of the actual property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActual(String value) {
        this.actual = value;
    }

    /**
     * Gets the value of the assessment property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationStatus }
     *     
     */
    public ValidationStatus getAssessment() {
        return assessment;
    }

    /**
     * Sets the value of the assessment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationStatus }
     *     
     */
    public void setAssessment(ValidationStatus value) {
        this.assessment = value;
    }

    /**
     * Gets the value of the daysOffset property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDaysOffset() {
        return daysOffset;
    }

    /**
     * Sets the value of the daysOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDaysOffset(BigInteger value) {
        this.daysOffset = value;
    }

}
