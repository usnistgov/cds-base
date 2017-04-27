package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.IllegalSave;
import gov.nist.healthcare.cds.service.SaveService;

@Service
public class DefaultSaveService implements SaveService {

	@Autowired
	private TestCaseSaver tcSave;

	@Autowired
	private TestPlanSaver tpSave;

	@Autowired
	private TestCaseGroupSaver tgSave;

	@Override
	public TestCase saveTC(TestCase testCase, String user) throws IllegalSave {
		return tcSave.saveEntity(testCase, user);
	}

	@Override
	public TestPlan saveTP(TestPlan testPlan, String user) throws IllegalSave {
		return tpSave.saveEntity(testPlan, user);
	}

	@Override
	public TestCaseGroup saveTG(TestCaseGroup testCaseGroup, String user) throws IllegalSave {
		return tgSave.saveEntity(testCaseGroup, user);
	}

}
