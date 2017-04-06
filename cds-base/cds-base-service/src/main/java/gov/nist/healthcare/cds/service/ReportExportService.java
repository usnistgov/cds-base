package gov.nist.healthcare.cds.service;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.wrapper.Report;

public interface ReportExportService {

	public String exportXML(Report report, TestCase tc) throws UnsupportedEncodingException, JAXBException, DatatypeConfigurationException;
}
