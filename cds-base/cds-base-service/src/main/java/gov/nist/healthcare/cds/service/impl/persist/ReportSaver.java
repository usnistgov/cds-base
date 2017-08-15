package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.exception.IllegalSave;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.service.EntitySaver;

public class ReportSaver extends EntitySaver<Report> {

	@Autowired
	public ReportRepository repository;
	
	public ReportSaver() {
		super(Report.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare(Report persisted, Report e, String user) throws IllegalSave {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(Report e) {
		// TODO Auto-generated method stub
		return false;
	}

}
