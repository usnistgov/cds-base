package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;

public class Document {
	
	private String name;
	private String fileName;
	private String location;
	private Date date;
	
	public String getName() {
		return name;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date d) {
		this.date = d;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}
