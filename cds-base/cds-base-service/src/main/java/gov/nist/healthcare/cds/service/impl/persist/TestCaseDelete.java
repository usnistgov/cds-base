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
public class TestCaseDelete extends EntityDelete<TestCase> {

	@Autowired
	private TestPlanRepository testPlanRepository;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private MetaDataService mdService;
	
	public TestCaseDelete() {
		super(TestCase.class);
	}

	@Override
	public boolean proceed(TestCase tc, String user) {
		List<Report> reports = reportRepository.reportsForTestCase(tc.getId());
		reportRepository.delete(reports);
		TestPlan tp = testPlanRepository.findOne(tc.getTestPlan());
		List<TestCase> list = findTC(tp,tc.getId());
		if(list != null){
			list.remove(tc);
			mdService.update(tp.getMetaData());
			testPlanRepository.save(tp);
			testCaseRepository.delete(tc);
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<TestCase> findTC(TestPlan tp, String id){
		for(TestCaseGroup tcg : tp.getTestCaseGroups()){
			for(TestCase tc : tcg.getTestCases()){
				if(tc.getId().equals(id)){
					return tcg.getTestCases();
				}
			}
		}
		for(TestCase tc : tp.getTestCases()){
			if(tc.getId().equals(id)){
				return tp.getTestCases();
			}
		}
		return null;
	}

}
