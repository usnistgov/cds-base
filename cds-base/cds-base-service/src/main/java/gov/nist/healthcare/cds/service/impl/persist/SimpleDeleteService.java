package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.exception.IllegalDelete;
import gov.nist.healthcare.cds.service.DeleteTestObjectService;

@Service
public class SimpleDeleteService implements DeleteTestObjectService {

	@Autowired
	private TestPlanDelete testPlanDelete;
	
	@Autowired
	private TestCaseDelete testCaseDelete;
	
	@Autowired
	private TestCaseGroupDelete testCaseGroupDelete;
	
	@Autowired
	private ValidationReportDelete reportDelete;
	
	

	@Override
	public boolean deleteTestCase(String tc, String user) throws IllegalDelete {
		return testCaseDelete.delete(tc, user);
	}

	@Override
	public boolean deleteTestPlan(String tp, String user) throws IllegalDelete {
		return testPlanDelete.delete(tp, user);
	}

	@Override
	public boolean deleteTestCaseGroup(String tg, String user) throws IllegalDelete {
		return testCaseGroupDelete.delete(tg, user);
	}

	@Override
	public boolean deleteReport(String report, String user) throws IllegalDelete {
		return reportDelete.delete(report, user);
	}
	

	
}
