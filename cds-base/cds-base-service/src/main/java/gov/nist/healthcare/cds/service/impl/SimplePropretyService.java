package gov.nist.healthcare.cds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.repositories.ReportRepository;
import gov.nist.healthcare.cds.repositories.SoftwareConfigRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.PropertyService;

@Service
public class SimplePropretyService implements PropertyService {

	@Autowired
	private TestPlanRepository testPlanRepository;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private SoftwareConfigRepository configRepository;

	@Override
	public TestCase tcBelongsTo(String tc, String user) {
		TestPlan tp = testPlanRepository.tcUser(tc);
		if(tp != null && tp.getUser().equals(user)){
			return testCaseRepository.findOne(tc);
		}
		return null;
	}

	@Override
	public TestPlan tpBelongsTo(String tpId, String user) {
		TestPlan tp = testPlanRepository.findOne(tpId);
		if(tp != null && tp.getUser().equals(user)){
			return tp;
		}
		return null;
	}

	@Override
	public Report reportBelongsTo(String reportId, String user) {
		Report r = reportRepository.findOne(reportId);
		if(r != null){
			TestPlan tp = testPlanRepository.tcUser(r.getId());
			if(tp != null && tp.getUser().equals(user)){
				return r;
			}
		}
		return null;
	}

	@Override
	public SoftwareConfig configBelongsTo(String configId, String user) {
		SoftwareConfig config = configRepository.findOne(configId);
		if(config != null && config.getUser().equals(user)){
			return config;
		}
		return null;
	}

}
