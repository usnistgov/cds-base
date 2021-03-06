package gov.nist.healthcare.cds.service.impl.persist;

import java.util.Date;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class SimpleMetaDataService implements MetaDataService {

	@Override
	public MetaData create(boolean imported) {
		Date dateCreated = new Date();
		Date dateUpdated = new Date();
		
		MetaData md = new MetaData();
		md.setDateCreated(dateCreated);
		md.setDateLastUpdated(dateUpdated);
		md.setImported(imported);
		md.setVersion("1.0");
		return md;
	}

	@Override
	public void update(MetaData md) {
		if(md != null){
			Date dateUpdated = new Date();
			md.setDateLastUpdated(dateUpdated);
		}
	}

	@Override
	public MetaData create(boolean imported, String version) {
		Date dateCreated = new Date();
		Date dateUpdated = new Date();
		
		MetaData md = new MetaData();
		md.setDateCreated(dateCreated);
		md.setDateLastUpdated(dateUpdated);
		md.setImported(imported);
		md.setVersion(version);
		return md;
	}

}
