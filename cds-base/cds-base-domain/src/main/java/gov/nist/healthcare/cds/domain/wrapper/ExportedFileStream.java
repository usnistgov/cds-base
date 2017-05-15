package gov.nist.healthcare.cds.domain.wrapper;

import java.io.InputStream;

public class ExportedFileStream {
	private String name;
	private InputStream in;
	
	
	public ExportedFileStream(String name, InputStream in) {
		super();
		this.name = name;
		this.in = in;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	
	
}
