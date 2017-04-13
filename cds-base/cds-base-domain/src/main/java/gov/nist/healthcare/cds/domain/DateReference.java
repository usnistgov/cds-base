package gov.nist.healthcare.cds.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "reference")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VaccineDateReference.class, name = "dynamic"),
        @JsonSubTypes.Type(value = StaticDateReference.class, name = "static")
})
public abstract class DateReference {

}
