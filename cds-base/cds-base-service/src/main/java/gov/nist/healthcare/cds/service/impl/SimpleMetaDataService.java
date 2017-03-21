package gov.nist.healthcare.cds.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class SimpleMetaDataService implements MetaDataService {

	@Override
	public MetaData create(boolean imported) {
		Date dateCreated = Calendar.getInstance().getTime();
		Date dateUpdated = Calendar.getInstance().getTime();
		
		MetaData md = new MetaData();
		md.setDateCreated(dateCreated);
		md.setDateLastUpdated(dateUpdated);
		md.setImported(imported);
		md.setVersion("1.0");
		return md;
	}

	@Override
	public void update(MetaData md) {
		Date dateUpdated = Calendar.getInstance().getTime();
		md.setDateLastUpdated(dateUpdated);
	}

	@Override
	public MetaData create(boolean imported, String version) {
		Date dateCreated = Calendar.getInstance().getTime();
		Date dateUpdated = Calendar.getInstance().getTime();
		
		MetaData md = new MetaData();
		md.setDateCreated(dateCreated);
		md.setDateLastUpdated(dateUpdated);
		md.setImported(imported);
		md.setVersion(version);
		return md;
	}

}
