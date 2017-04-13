package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.service.EntityDelete;

@Service
public class ValidationReportDelete extends EntityDelete<Report> {

	@Autowired
	private ReportRepository reportRepository;
	
	public ValidationReportDelete() {
		super(Report.class);
	}

	@Override
	public boolean proceed(Report entity, String user) {
		reportRepository.delete(entity);
		return true;
	}

}
