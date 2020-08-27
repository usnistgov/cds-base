package gov.nist.healthcare.cds.service.impl.persist;


import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.EntitySaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestPlanSaver extends EntitySaver<TestPlan> {

	@Autowired
	private TestPlanRepository tpRepository;
	
	public TestPlanSaver() {
		super(TestPlan.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare(TestPlan persisted, TestPlan e, String user) {
		if(persisted == null){
			e.setUser(user);
		}
		else {
			e.setUser(persisted.getUser());
			e.setTestCaseGroups(persisted.getTestCaseGroups());
			e.setTestCases(persisted.getTestCases());
		}
		this.saveObject.setTp(e);
	}

	@Override
	public boolean exists(TestPlan e) {
		return tpRepository.exists(e.getId());
	}


}
