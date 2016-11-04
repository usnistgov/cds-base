package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.xml.XMLError;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface CDCSpreadSheetFormatService {

	public List<TestCase> _import(InputStream file);
	public OutputStream _export(List<TestCase> tcs);
	public List<XMLError> _validate(InputStream file);
	
}
