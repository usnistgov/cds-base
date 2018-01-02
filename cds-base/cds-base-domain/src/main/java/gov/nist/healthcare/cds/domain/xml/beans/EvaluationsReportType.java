//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.01 at 03:57:52 PM EST 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EvaluationsReportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EvaluationsReportType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VaccinationEventValidation" type="{https://fits.nist.gov/xml/}EventValidationType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EvaluationsReportType", propOrder = {
    "vaccinationEventValidation"
})
public class EvaluationsReportType {

    @XmlElement(name = "VaccinationEventValidation")
    protected List<EventValidationType> vaccinationEventValidation;

    /**
     * Gets the value of the vaccinationEventValidation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccinationEventValidation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccinationEventValidation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventValidationType }
     * 
     * 
     */
    public List<EventValidationType> getVaccinationEventValidation() {
        if (vaccinationEventValidation == null) {
            vaccinationEventValidation = new ArrayList<EventValidationType>();
        }
        return this.vaccinationEventValidation;
    }

}
