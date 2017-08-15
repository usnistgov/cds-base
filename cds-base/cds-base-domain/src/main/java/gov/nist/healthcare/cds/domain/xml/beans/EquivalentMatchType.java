//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.07.12 at 10:27:54 AM EDT 
//


package gov.nist.healthcare.cds.domain.xml.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EquivalentMatchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EquivalentMatchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="Group" type="{http://fits.nist.gov/vaccine_matcher/}GroupType"/>
 *           &lt;element name="CVX" type="{http://fits.nist.gov/vaccine_matcher/}CVXType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EquivalentMatchType", propOrder = {
    "groupOrCVX"
})
public class EquivalentMatchType {

    @XmlElements({
        @XmlElement(name = "Group", type = GroupType.class),
        @XmlElement(name = "CVX", type = CVXType.class)
    })
    protected List<Object> groupOrCVX;

    /**
     * Gets the value of the groupOrCVX property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the groupOrCVX property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGroupOrCVX().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GroupType }
     * {@link CVXType }
     * 
     * 
     */
    public List<Object> getGroupOrCVX() {
        if (groupOrCVX == null) {
            groupOrCVX = new ArrayList<Object>();
        }
        return this.groupOrCVX;
    }

}
