package gov.nist.healthcare.cds.service;

import java.io.InputStream;
import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.wrapper.ExportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ExportResult;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public interface FormatService {

	//-- Format's NAME
	public String formatName();
	
	//-- Pre Conditions
	public List<ErrorModel> preImport(InputStream stream);
	public List<ErrorModel> preExport(TestCase tc);
	
	//-- Import/Export Logic
	public TransformResult importFromFile(InputStream stream, ImportConfig config) throws ConfigurationException;
	public ExportResult exportToFile(List<TestCase> tc, ExportConfig config) throws ConfigurationException;
	
}
