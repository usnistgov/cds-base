package gov.nist.healthcare.cds.service;

import java.io.InputStream;
import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public interface FormatService {

	public String formatName();
	public List<ErrorModel> _validate(InputStream stream);
	public TransformResult _import(InputStream stream, ImportConfig config) throws ConfigurationException;
	public InputStream _exportOne(TestCase tc);
	public InputStream _exportAll(List<TestCase> tc);
	
}
