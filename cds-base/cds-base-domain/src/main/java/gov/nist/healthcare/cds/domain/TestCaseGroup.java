package gov.nist.healthcare.cds.domain;


import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TestCaseGroup extends ContainingEntity {

	private String testPlan;
	private String name;
	private String description;
	
	
	public String getTestPlan() {
		return testPlan;
	}

	public void setTestPlan(String testPlan) {
		this.testPlan = testPlan;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        TestCaseGroup that = (TestCaseGroup) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	
	
}
//{ $elemMatch: { 'refund.id' :  ?0 } } 