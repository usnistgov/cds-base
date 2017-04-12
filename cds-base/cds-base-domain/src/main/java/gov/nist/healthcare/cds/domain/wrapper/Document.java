package gov.nist.healthcare.cds.domain.wrapper;


public class Document {
	
	private String name;
	private String fileName;
	private String location;
	private String date;
	
	public String getName() {
		return name;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String d) {
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
