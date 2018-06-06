//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.01 at 04:16:03 PM EST 
//


package gov.nist.healthcare.cds.service.domain.matcher;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the gov.nist.healthcare.cds.service.domain.matcher package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _VaccineMatcher_QNAME = new QName("http://fits.nist.gov/vaccine_matcher/", "VaccineMatcher");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: gov.nist.healthcare.cds.service.domain.matcher
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VaccineMatcherType }
     * 
     */
    public VaccineMatcherType createVaccineMatcherType() {
        return new VaccineMatcherType();
    }

    /**
     * Create an instance of {@link VaccineMappingType }
     * 
     */
    public VaccineMappingType createVaccineMappingType() {
        return new VaccineMappingType();
    }

    /**
     * Create an instance of {@link GroupType }
     * 
     */
    public GroupType createGroupType() {
        return new GroupType();
    }

    /**
     * Create an instance of {@link CVXType }
     * 
     */
    public CVXType createCVXType() {
        return new CVXType();
    }

    /**
     * Create an instance of {@link EquivalentMatchType }
     * 
     */
    public EquivalentMatchType createEquivalentMatchType() {
        return new EquivalentMatchType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VaccineMatcherType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fits.nist.gov/vaccine_matcher/", name = "VaccineMatcher")
    public JAXBElement<VaccineMatcherType> createVaccineMatcher(VaccineMatcherType value) {
        return new JAXBElement<VaccineMatcherType>(_VaccineMatcher_QNAME, VaccineMatcherType.class, null, value);
    }

}