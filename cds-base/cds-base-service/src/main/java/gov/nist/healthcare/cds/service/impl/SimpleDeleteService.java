package gov.nist.healthcare.cds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.DeleteTestObjectService;
import gov.nist.healthcare.cds.service.MetaDataService;
import gov.nist.healthcare.cds.service.PropertyService;

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
		tp.getTestCases().remove(tc);
		mdService.update(tp.getMetaData());
		testPlanRepository.save(tp);
		testCaseRepository.delete(tc);	
		return true;
	}

	@Override
	@Transactional
	public boolean deleteTestPlan(TestPlan tp) {
		for(TestCase tc : tp.getTestCases()){
			this.deleteTestCase(tc);
		}
		testPlanRepository.delete(tp);
		return true;
	}
	
}
