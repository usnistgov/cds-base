package gov.nist.healthcare.cds.domain;

import org.springframework.data.annotation.Id;

import gov.nist.healthcare.cds.domain.wrapper.MetaData;

public class Entity {
	
	@Id
	protected String id;
	protected MetaData metaData;

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
