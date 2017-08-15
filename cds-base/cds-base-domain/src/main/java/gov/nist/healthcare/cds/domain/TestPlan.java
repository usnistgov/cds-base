package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class TestPlan extends ContainingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8368001729121705377L;
	
	@NotNull
	private String name;
	private String description;
	@Indexed
	private String user;
	private boolean isPublic;
	private List<String> viewers;
	private List<TestCaseGroup> testCaseGroups;
	
	public TestPlan(){

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
	public List<TestCaseGroup> getTestCaseGroups() {
		if(this.testCaseGroups == null)
			this.testCaseGroups = new ArrayList<>();
		return testCaseGroups;
	}
	public void setTestCaseGroups(List<TestCaseGroup> testCaseGroups) {
		this.testCaseGroups = testCaseGroups;
	}
	public void addTestCase(TestCase tc){
		if(this.testCases == null)
			this.testCases = new ArrayList<TestCase>();
		tc.setTestPlan(this.getId());
		this.testCases.add(tc);
	}
	public TestCaseGroup getOrCreateGroup(String id,String name){
		if(testCaseGroups != null){
			for(TestCaseGroup gr : testCaseGroups){
				if(gr.getId().equals(id)){
					return gr;
				}
			}
		}
		TestCaseGroup tcg = new TestCaseGroup();
		tcg.setId(id);
		tcg.setName(name);
		tcg.setTestPlan(this.getId());
		this.getTestCaseGroups().add(tcg);
		return tcg;
	}
	public TestCaseGroup createGroup(String name){
		TestCaseGroup tcg = new TestCaseGroup();
		tcg.setId(UUID.randomUUID().toString());
		tcg.setName(name);
		tcg.setTestPlan(this.getId());
		this.getTestCaseGroups().add(tcg);
		return tcg;
	}
	public TestCaseGroup getByNameOrCreateGroup(String name){
		if(testCaseGroups != null){
			for(TestCaseGroup gr : testCaseGroups){
				if(gr.getName().equals(name)){
					return gr;
				}
			}
		}
		TestCaseGroup tcg = new TestCaseGroup();
		tcg.setId(UUID.randomUUID().toString());
		tcg.setName(name);
		tcg.setTestPlan(this.getId());
		this.getTestCaseGroups().add(tcg);
		return tcg;
	}
	
	public TestCaseGroup getGroup(String id){
		if(testCaseGroups == null)
			return null;
		for(TestCaseGroup gr : testCaseGroups){
			if(gr.getId().equals(id)){
				return gr;
			}
		}
		return null;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        TestPlan that = (TestPlan) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public List<String> getViewers() {
		if(viewers == null){
			viewers = new ArrayList<String>();
		}
		return viewers;
	}

	public void setViewers(List<String> viewers) {
		this.viewers = viewers;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
}
