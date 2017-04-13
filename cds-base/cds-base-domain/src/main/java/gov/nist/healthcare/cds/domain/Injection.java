package gov.nist.healthcare.cds.domain;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Document
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "discriminator")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Vaccine.class, name = "generic"),
        @JsonSubTypes.Type(value = Product.class, name = "specific")
})
public abstract class Injection {

	@Transient
	@JsonProperty(value = "discriminator") 
	private String type;
	
	@Id
	@Indexed
	protected String id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
