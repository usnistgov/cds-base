package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.wrapper.MetaData;

public interface MetaDataService {

	public MetaData create(boolean imported);
	public MetaData create(boolean imported, String version);
	public void update(MetaData md);
}
