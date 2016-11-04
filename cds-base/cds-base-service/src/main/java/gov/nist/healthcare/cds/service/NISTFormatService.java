package gov.nist.healthcare.cds.service;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.xml.XMLError;


public interface NISTFormatService {

	public TestCase _import(String file) throws VaccineNotFoundException;
	public String _export(TestCase tcs);
	public List<XMLError> _validate(String file);
}
