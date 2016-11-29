package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.TestCase;

import java.io.InputStream;
import java.util.List;

public interface VaccineImportService {
	public List<TestCase> _import(InputStream in);
}
