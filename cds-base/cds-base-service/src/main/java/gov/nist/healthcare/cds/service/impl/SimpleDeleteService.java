package gov.nist.healthcare.cds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.DeleteTestObjectService;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class SimpleDeleteService implements DeleteTestObjectService {

	@Autowired
	private TestPlanRepository testPlanRepository;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private MetaDataService mdService;
	
	@Override
	@Transactional
	public boolean deleteTestCase(TestCase tc) {
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
	
	public void deleteReportsForTC(TestCase tc){
		List<Report> reports = reportRepository.reportsForTestCase(tc.getId());
		reportRepository.delete(reports);
	}

	@Override
	@Transactional
	public boolean deleteTestPlan(TestPlan tp) {
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

	@Override
	public boolean deleteTestCaseGroup(TestCaseGroup tcg) {
		TestPlan tp = testPlanRepository.testCaseGroup(tcg.getId());
		tp.getTestCaseGroups().remove(tcg);
		for(TestCase tc : tcg.getTestCases()){
			this.deleteReportsForTC(tc);
		}
		testCaseRepository.delete(tcg.getTestCases());
		mdService.update(tp.getMetaData());
		testPlanRepository.save(tp);
		return true;
	}
	
}
