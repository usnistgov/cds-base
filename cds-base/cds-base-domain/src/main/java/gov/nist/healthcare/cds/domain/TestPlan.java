package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TestPlan implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private MetaData metaData;

//	@OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="testPlan")
	private List<TestCase> testCases;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	public void addTestCase(TestCase tc){
		if(this.testCases == null)
			this.testCases = new ArrayList<TestCase>();
		this.testCases.add(tc);
	}
	
}
