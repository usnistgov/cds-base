package gov.nist.healthcare.cds.service.impl.data;

import java.io.InputStream;

import gov.nist.healthcare.cds.domain.wrapper.ImportResult;

public abstract class ImportExportHandler {

	public abstract ImportResult importFromFile(InputStream file);
	
}
