package gov.nist.healthcare.cds.domain.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import gov.nist.healthcare.cds.domain.xml.ErrorModel;

public class ZipExportSummary  extends ExportSummary {

	
	private ByteArrayOutputStream baos;
	private ZipOutputStream out;
	final int BUFFER = 2048;
	
	
	public ZipExportSummary() {
		super();
	}


	public ZipExportSummary(ByteArrayOutputStream out) {
		super();
		baos = out;
		this.out = new ZipOutputStream(out);
		testCases = new ArrayList<EntityResult>();
	}


	public int getAll() {
		return all;
	}


	public void setAll(int all) {
		this.all = all;
	}

	public List<EntityResult> getTestCases() {
		return testCases;
	}


	public ByteArrayOutputStream getBaos() {
		return baos;
	}


	public void setBaos(ByteArrayOutputStream baos) {
		this.baos = baos;
	}


	public void setTestCases(List<EntityResult> testCases) {
		this.testCases = testCases;
	}

	public int getWerrors() {
		return werrors;
	}


	public void setWerrors(int werrors) {
		this.werrors = werrors;
	}


	public ZipOutputStream getOut() {
		return out;
	}


	public void setOut(ZipOutputStream out) {
		this.out = out;
	}


	public void add(ExportResult toAdd,List<String> names) throws IOException{
		for(ExportedFileStream efs : toAdd.getReader()){
			byte data[] = new byte[BUFFER];
			this.resultFor(efs.getName());
			String name = efs.getName();
			while(names.contains(name)){
				name ="*"+name;
			}
			ZipEntry e = new ZipEntry(name);
			out.putNextEntry(e);
			int size = -1;
			while((size = efs.getIn().read(data, 0, BUFFER)) != -1  )
            {
                out.write(data, 0, size);
            }
			names.add(name);
			out.closeEntry();
			all++;
		}
	}
	
	public void invalid(String name, List<ErrorModel> errors){
		EntityResult rs = this.resultFor(name);
		rs.setErrors(errors);
		werrors++;
		all++;
		
	}
	
	public EntityResult resultFor(String name){
		for(EntityResult r : this.getTestCases()){
			if(r.getName().equals(name))
				return r;
		}
		EntityResult r = new EntityResult();
		r.setName(name);
		this.getTestCases().add(r);
		return r;
	}
}
