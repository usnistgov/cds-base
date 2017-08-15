package gov.nist.healthcare.cds.service.impl.persist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.EntityDelete;

@Service
public class TestCaseGroupDelete extends EntityDelete<TestCaseGroup> {

	@Autowired
	private TestPlanRepository testPlanRepository;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	public TestCaseGroupDelete() {
		super(TestCaseGroup.class);
	}

	@Override
	public boolean proceed(TestCaseGroup tg, String user) {
		TestPlan tp = this.ledger.tpBelongsTo(tg.getTestPlan(), user, EntityAccess.W);
		TestCaseGroup tcg = tp.getGroup(tg.getId());
		for(TestCase tc : tcg.getTestCases()){
			this.deleteReportsForTC(tc);
		}
		testCaseRepository.delete(tcg.getTestCases());
		tp.getTestCaseGroups().remove(tcg);
		mdService.update(tp.getMetaData());
		testPlanRepository.save(tp);
		return true;
	}
	
	public void deleteReportsForTC(TestCase tc){
		List<Report> reports = reportRepository.reportsForTestCase(tc.getId());
		reportRepository.delete(reports);
	}

}
