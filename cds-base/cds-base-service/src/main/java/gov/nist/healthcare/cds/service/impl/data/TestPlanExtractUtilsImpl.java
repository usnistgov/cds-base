package gov.nist.healthcare.cds.service.impl.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.service.TestPlanExtractUtils;

@Service
public class TestPlanExtractUtilsImpl implements TestPlanExtractUtils {

	@Override
	public List<TestCase> extract(TestPlan tp, String[] ids) {
		List<String> idList = Arrays.asList(ids);
		List<TestCase> extracted = new ArrayList<>();
		
		for(TestCase tc : tp.getTestCases()){
			if(idList.contains(tc.getId())){
				extracted.add(tc);
			}
		}
		
		for(TestCaseGroup tg : tp.getTestCaseGroups()){
			for(TestCase tc : tg.getTestCases()){
				if(idList.contains(tc.getId())){
					tc.setGroupTag(tg.getName());
					extracted.add(tc);
				}
			}
		}
		
		return extracted;
	}

}
