package gov.nist.healthcare.cds.service.impl.persist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.EntityDelete;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class TestPlanDelete extends EntityDelete<TestPlan> {

	@Autowired
	private TestPlanRepository testPlanRepository;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	public TestPlanDelete() {
		super(TestPlan.class);
	}

	@Override
	public boolean proceed(TestPlan tp, String user) {
		for(TestCase tc : tp.getTestCases()){
			this.deleteReportsForTC(tc);
		}
		testCaseRepository.delete(tp.getTestCases());
		for(TestCaseGroup tcg : tp.getTestCaseGroups()){
			for(TestCase tc : tcg.getTestCases()){
				this.deleteReportsForTC(tc);
			}
			testCaseRepository.delete(tcg.getTestCases());
		}
		testPlanRepository.delete(tp);
		return true;
	}
	
	public void deleteReportsForTC(TestCase tc){
		List<Report> reports = reportRepository.reportsForTestCase(tc.getId());
		reportRepository.delete(reports);
	}

}
