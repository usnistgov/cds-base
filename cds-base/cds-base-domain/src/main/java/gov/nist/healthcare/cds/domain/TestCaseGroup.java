package gov.nist.healthcare.cds.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestCaseGroup {

	@Id
	private String id;
	@DBRef
	private List<TestCase> testCases;
	private String testPlan;
	private String name;
	
	public List<TestCase> getTestCases() {
		if(testCases == null){
			testCases = new ArrayList<>();
		}
		return testCases;
	}
	
	public String getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
