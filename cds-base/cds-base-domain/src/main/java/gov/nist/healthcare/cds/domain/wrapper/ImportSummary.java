package gov.nist.healthcare.cds.domain.wrapper;


import java.util.ArrayList;
import java.util.List;

import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public class ImportSummary {
	
	private List<EntityResult> files;
	private int all;
	private int imported;
	private int werrors;
	private TestPlan testPlan;
	
	public static ImportSummary invalidFormat(){
		ImportSummary sum = new ImportSummary();
		sum.resultFor("Configuration").getErrors().add(new ErrorModel(0,0,"Format","Unsupported Format"));
		return sum;
	}
	
	public static ImportSummary configError(){
		ImportSummary sum = new ImportSummary();
		sum.resultFor("Configuration").getErrors().add(new ErrorModel(0,0,"Import Configuration","Invalid Configuration"));
		return sum;
	}
	
	public static ImportSummary errorInFile(){
		ImportSummary sum = new ImportSummary();
		sum.resultFor("Request").getErrors().add(new ErrorModel(0,0,"Request","Invalid Request"));
		return sum;
	}
	
	public List<EntityResult> getFiles() {
		if(files == null)
			files = new ArrayList<>();
		return files;
	}
	public void setFiles(List<EntityResult> files) {
		this.files = files;
	}
	public int getAll() {
		return all;
	}
	public void setAll(int all) {
		this.all = all;
	}
	public int getImported() {
		return imported;
	}
	public void setImported(int imported) {
		this.imported = imported;
	}
	public int getWerrors() {
		return werrors;
	}
	public void setWerrors(int werrors) {
		this.werrors = werrors;
	}
	
	public EntityResult resultFor(String name){
		for(EntityResult r : this.getFiles()){
			if(r.getName().equals(name))
				return r;
		}
		EntityResult r = new EntityResult();
		r.setName(name);
		this.getFiles().add(r);
		return r;
	}
	public TestPlan getTestPlan() {
		return testPlan;
	}
	public void setTestPlan(TestPlan testPlan) {
		this.testPlan = testPlan;
	}
	
	
	
	
}
