package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Entity;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
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
	public TestCase tcBelongsTo(String tc, String user, EntityAccess accessType) {
		TestPlan tp = testPlanRepository.tcUser(tc);
		if(tp != null){
			boolean pass = tp.getUser().equals(user) || (accessType.equals(EntityAccess.R) && (tp.getViewers().contains(user) || tp.isPublic()));
			return pass ? testCaseRepository.findOne(tc) : null;
		}
		return null;
	}

	@Override
	public TestPlan tpBelongsTo(String tpId, String user, EntityAccess accessType) {
		TestPlan tp = testPlanRepository.findOne(tpId);
		if(tp != null){
			boolean pass = tp.getUser().equals(user) || (accessType.equals(EntityAccess.R) && (tp.getViewers().contains(user) || tp.isPublic()));
			return pass ? tp : null;
		}
		return null;
	}

	@Override
	public Report reportBelongsTo(String reportId, String user) {
		Report r = reportRepository.findOne(reportId);
		if(r != null && r.getUser() != null && r.getUser().equals(user)){
			return r;
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

	@Override
	public TestCaseGroup tgBelongsTo(String tgId, String user, EntityAccess accessType) {
		TestPlan tp = testPlanRepository.testCaseGroup(tgId);
		if(tp != null){
			boolean pass = tp.getUser().equals(user) || (accessType.equals(EntityAccess.R) && (tp.getViewers().contains(user) || tp.isPublic()));
			return pass ? tp.getGroup(tgId) : null;
		}
		return null;
	}

	@Override
	public <T extends Entity> T belongsTo(String id, String user, Class<T> type, EntityAccess accessType) {
		if(type.equals(TestCase.class)){
			return (T) this.tcBelongsTo(id, user, accessType);
		}
		else if(type.equals(TestCaseGroup.class)){
			return (T) this.tgBelongsTo(id, user, accessType);
		}
		else if(type.equals(TestPlan.class)){
			return (T) this.tpBelongsTo(id, user, accessType);
		}
		else if(type.equals(Report.class)){
			return (T) this.reportBelongsTo(id, user);
		}
		return null;
	}


}
