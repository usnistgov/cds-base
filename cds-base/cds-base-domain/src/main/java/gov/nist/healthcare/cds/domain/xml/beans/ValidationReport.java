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
 * <p>Java class for ValidationReport complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationReport">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportMetaData" type="{https://fits.nist.gov/xml/}ReportMetaDataType"/>
 *         &lt;element name="TestCaseMetaData" type="{https://fits.nist.gov/xml/}TestCaseMetaDataType"/>
 *         &lt;element name="TestCaseInformation" type="{https://fits.nist.gov/xml/}TestCaseInformationType"/>
 *         &lt;element name="ValidationResult" type="{https://fits.nist.gov/xml/}ValidationResultType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationReport", propOrder = {
    "reportMetaData",
    "testCaseMetaData",
    "testCaseInformation",
    "validationResult"
})
public class ValidationReport {

    @XmlElement(name = "ReportMetaData", required = true)
    protected ReportMetaDataType reportMetaData;
    @XmlElement(name = "TestCaseMetaData", required = true)
    protected TestCaseMetaDataType testCaseMetaData;
    @XmlElement(name = "TestCaseInformation", required = true)
    protected TestCaseInformationType testCaseInformation;
    @XmlElement(name = "ValidationResult", required = true)
    protected ValidationResultType validationResult;

    /**
     * Gets the value of the reportMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link ReportMetaDataType }
     *     
     */
    public ReportMetaDataType getReportMetaData() {
        return reportMetaData;
    }

    /**
     * Sets the value of the reportMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportMetaDataType }
     *     
     */
    public void setReportMetaData(ReportMetaDataType value) {
        this.reportMetaData = value;
    }

    /**
     * Gets the value of the testCaseMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link TestCaseMetaDataType }
     *     
     */
    public TestCaseMetaDataType getTestCaseMetaData() {
        return testCaseMetaData;
    }

    /**
     * Sets the value of the testCaseMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestCaseMetaDataType }
     *     
     */
    public void setTestCaseMetaData(TestCaseMetaDataType value) {
        this.testCaseMetaData = value;
    }

    /**
     * Gets the value of the testCaseInformation property.
     * 
     * @return
     *     possible object is
     *     {@link TestCaseInformationType }
     *     
     */
    public TestCaseInformationType getTestCaseInformation() {
        return testCaseInformation;
    }

    /**
     * Sets the value of the testCaseInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestCaseInformationType }
     *     
     */
    public void setTestCaseInformation(TestCaseInformationType value) {
        this.testCaseInformation = value;
    }

    /**
     * Gets the value of the validationResult property.
     * 
     * @return
     *     possible object is
     *     {@link ValidationResultType }
     *     
     */
    public ValidationResultType getValidationResult() {
        return validationResult;
    }

    /**
     * Sets the value of the validationResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidationResultType }
     *     
     */
    public void setValidationResult(ValidationResultType value) {
        this.validationResult = value;
    }

}
