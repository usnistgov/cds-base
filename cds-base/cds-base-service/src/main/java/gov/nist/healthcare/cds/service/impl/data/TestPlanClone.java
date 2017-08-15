package gov.nist.healthcare.cds.service.impl.data;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.PropertyService;

@Service
public class TestPlanClone {

	@Autowired
	private TestPlanRepository tpRepo;
	
	@Autowired
	private TestCaseRepository tcRepo;
	
	@Autowired
	private PropertyService ledger;
	
	@Autowired
	private RWTestPlanFilter filter;
	
	public TestPlan clone(String tpI, String user){
		
		TestPlan tp = ledger.tpBelongsTo(tpI, user, EntityAccess.R);
		
		if(tp != null){

			if(!tp.getUser().equals(user)){
				filter.filterTestPlan(tp);
			}
			
			ArrayList<TestCase> testCases = new ArrayList<TestCase>();
			String tpId = ObjectId.get().toHexString();
			
			//-- TP
			tp.setId(tpId);
			tp.setUser(user);
			tp.setViewers(null);
			
			//-- TC
			for(TestCase tc : tp.getTestCases()){
				tc.setId(null);
				testCases.add(tc);
				tc.setTestPlan(tpId);
			}
			
			for(TestCaseGroup tcg : tp.getTestCaseGroups()){
				String tcgId = ObjectId.get().toHexString();
				tcg.setId(tcgId);
				tcg.setTestPlan(tpId);
				
				for(TestCase tc : tcg.getTestCases()){
					tc.setId(null);
					tc.setGroupTag(tcgId);
					tc.setTestPlan(tpId);
					testCases.add(tc);
				}
			}
			
			StringBuilder str = new StringBuilder();
			
			tp.setName(str.append("[CLONE] ").append(tp.getName()).toString());
			tp.setViewers(new ArrayList<String>());
			tcRepo.save(testCases);
			tpRepo.save(tp);
			return tp;
		}
		else {
			return null;
		}
		
	}
}
