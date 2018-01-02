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
 * <p>Java class for ValidationResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReportSummary" type="{https://fits.nist.gov/xml/}ReportSummaryType"/>
 *         &lt;element name="EvaluationsReport" type="{https://fits.nist.gov/xml/}EvaluationsReportType" minOccurs="0"/>
 *         &lt;element name="ForecastsReport" type="{https://fits.nist.gov/xml/}ForecastsReportType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationResultType", propOrder = {
    "reportSummary",
    "evaluationsReport",
    "forecastsReport"
})
public class ValidationResultType {

    @XmlElement(name = "ReportSummary", required = true)
    protected ReportSummaryType reportSummary;
    @XmlElement(name = "EvaluationsReport")
    protected EvaluationsReportType evaluationsReport;
    @XmlElement(name = "ForecastsReport", required = true)
    protected ForecastsReportType forecastsReport;

    /**
     * Gets the value of the reportSummary property.
     * 
     * @return
     *     possible object is
     *     {@link ReportSummaryType }
     *     
     */
    public ReportSummaryType getReportSummary() {
        return reportSummary;
    }

    /**
     * Sets the value of the reportSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportSummaryType }
     *     
     */
    public void setReportSummary(ReportSummaryType value) {
        this.reportSummary = value;
    }

    /**
     * Gets the value of the evaluationsReport property.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationsReportType }
     *     
     */
    public EvaluationsReportType getEvaluationsReport() {
        return evaluationsReport;
    }

    /**
     * Sets the value of the evaluationsReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationsReportType }
     *     
     */
    public void setEvaluationsReport(EvaluationsReportType value) {
        this.evaluationsReport = value;
    }

    /**
     * Gets the value of the forecastsReport property.
     * 
     * @return
     *     possible object is
     *     {@link ForecastsReportType }
     *     
     */
    public ForecastsReportType getForecastsReport() {
        return forecastsReport;
    }

    /**
     * Sets the value of the forecastsReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link ForecastsReportType }
     *     
     */
    public void setForecastsReport(ForecastsReportType value) {
        this.forecastsReport = value;
    }

}
