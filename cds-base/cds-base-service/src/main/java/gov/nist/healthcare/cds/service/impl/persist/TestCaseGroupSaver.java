package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.IllegalSave;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.EntitySaver;

@Service
public class TestCaseGroupSaver extends EntitySaver<TestCaseGroup> {

	@Autowired
	private TestPlanRepository tpRepository;
	
	public TestCaseGroupSaver() {
		super(TestCaseGroup.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void prepare(TestCaseGroup persisted, TestCaseGroup e, String user) throws IllegalSave {
		TestPlan tp = tpRepository.findOne(e.getTestPlan());
		
		//--- NEW
		if(persisted == null){
			this.mdService.update(e.getMetaData());
			tp.getTestCaseGroups().add(e);
		}
		//--- EXISTS
		else {
			TestCaseGroup grp = tp.getGroup(persisted.getId());
			this.verify(TestCaseGroup.class, grp, persisted.getId());
			grp.setDescription(e.getDescription());
			grp.setName(e.getName());
			grp.setMetaData(e.getMetaData());
			this.mdService.update(grp.getMetaData());
		}
		this.saveObject.setTp(tp);
	}

	@Override
	public boolean exists(TestCaseGroup e) {
		return tpRepository.testCaseGroup(e.getId()) != null;
	}

	

}
