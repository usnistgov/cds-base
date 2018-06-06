package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.VaccineMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public interface VaccineImportService {
	Set<VaccineMapping> _import(InputStream vaccines, InputStream groups, InputStream manufacturer, InputStream products) throws IOException;
}
