package gov.nist.healthcare.cds.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class ContainingEntity extends Entity {

	@DBRef
	protected List<TestCase> testCases;

	public List<TestCase> getTestCases() {
		if(testCases == null){
			testCases = new ArrayList<>();
		}
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
}
