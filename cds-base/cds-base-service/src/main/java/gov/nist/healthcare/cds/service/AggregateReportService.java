package gov.nist.healthcare.cds.service;

import java.util.List;

import gov.nist.healthcare.cds.domain.wrapper.AggregateReport;
import gov.nist.healthcare.cds.domain.wrapper.Report;

public interface AggregateReportService {

	public AggregateReport aggregate(List<Report> reports);
	
}
