package gov.nist.healthcare.cds.service.impl.persist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.IllegalSave;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.service.EntitySaver;
import gov.nist.healthcare.cds.service.domain.SaveObject;

@Service
public class TestCaseSaver extends EntitySaver<TestCase> {

	@Autowired
	private TestCaseRepository tcRepository;

	
	public TestCaseSaver() {
		super(TestCase.class);
	}

	@Override
	public void prepare(TestCase persisted, TestCase e, String user) throws IllegalSave {
		TestPlan tp = ledger.tpBelongsTo(e.getTestPlan(), user, EntityAccess.W);
		this.verify(TestPlan.class, tp, e.getTestPlan());
		
		//--- New
		if(persisted == null){
			
			//--- Group
			if(e.getGroupTag() != null && !e.getGroupTag().isEmpty()){
				TestCaseGroup grp = tp.getGroup(e.getGroupTag());
				this.verify(TestCaseGroup.class, grp, e.getGroupTag());
				grp.getTestCases().add(e);
				this.mdService.update(grp.getMetaData());
			}
			//--- TP
			else {
				tp.addTestCase(e);
			}
			this.saveObject.setTp(tp);
			this.saveObject.setTc(e);
		}
		//--- Exists
		else {
			
			//--- Group
			if(e.getGroupTag() != null && !e.getGroupTag().isEmpty()){
				TestCaseGroup grp = tp.getGroup(e.getGroupTag());
				this.verify(TestCaseGroup.class, grp, e.getGroupTag());
				
				//--- Same Group
				if(e.getGroupTag().equals(persisted.getGroupTag())){
					this.saveObject.setTc(e);
				}
				//--- Moving from TP
				else if(persisted.getGroupTag() == null || persisted.getGroupTag().isEmpty()){
					tp.getTestCases().remove(persisted);
					grp.getTestCases().add(e);
					this.mdService.update(grp.getMetaData());
					this.saveObject.setTp(tp);
					this.saveObject.setTc(e);
				}
				//--- Moving from Group
				else {
					TestCaseGroup oldGrp = tp.getGroup(persisted.getGroupTag());
					this.verify(TestCaseGroup.class, oldGrp, persisted.getGroupTag());
					oldGrp.getTestCases().remove(persisted);
					grp.getTestCases().add(e);
					this.mdService.update(oldGrp.getMetaData());
					this.mdService.update(grp.getMetaData());
					this.saveObject.setTp(tp);
					this.saveObject.setTc(e);
				}
			}
			//--- TP
			else {
				if(persisted.getGroupTag()!= null && !persisted.getGroupTag().isEmpty()){
					TestCaseGroup oldGrp = tp.getGroup(persisted.getGroupTag());
					this.verify(TestCaseGroup.class, oldGrp, persisted.getGroupTag());
					oldGrp.getTestCases().remove(persisted);
					this.mdService.update(oldGrp.getMetaData());
					tp.addTestCase(e);
					this.saveObject.setTp(tp);
					this.saveObject.setTc(e);
				}
				else {
					this.saveObject.setTc(e);
				}
			}
		}
	}

	@Override
	public boolean exists(TestCase e) {
		return tcRepository.exists(e.getId());
	}


}
