package gov.nist.healthcare.cds.service.impl.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.enumeration.WorkflowTag;
import gov.nist.healthcare.cds.service.TestPlanFilter;

@Service
public class RWTestPlanFilter implements TestPlanFilter {

	@Override
	public void filterTestPlan(TestPlan x) {
		List<String> emptyIds = new ArrayList<String>();
		x.setTestCases(filter(x.getTestCases()));
		for(TestCaseGroup tcg : x.getTestCaseGroups()){
			tcg.setTestCases(filter(tcg.getTestCases()));
			if(tcg.getTestCases().isEmpty()){
				emptyIds.add(tcg.getId());
			}
		}
		
		for(String id : emptyIds){
			TestCaseGroup tcg = x.getGroup(id);
			x.getTestCaseGroups().remove(tcg);
		}
		
	}
	
	@Override
	public List<TestCase> filter(List<TestCase> tcList){
		List<TestCase> tcL = new ArrayList<TestCase>();
		for(TestCase tc : tcList){
			if(conform(tc)){
				tcL.add(tc);
			}
		}
		return tcL;
	}
	
	@Override
	public boolean conform(TestCase tc){
		return tc.getWorkflowTag() != null && tc.getWorkflowTag().equals(WorkflowTag.FINAL);
	}

}
