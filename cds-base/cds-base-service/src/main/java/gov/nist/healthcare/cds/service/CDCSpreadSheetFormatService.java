package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.CDCImport;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface CDCSpreadSheetFormatService {

	public OutputStream _export(List<TestCase> tcs);
	public List<ErrorModel> _validate(InputStream file);
	public CDCImport _import(InputStream in, ImportConfig config);
	
}
