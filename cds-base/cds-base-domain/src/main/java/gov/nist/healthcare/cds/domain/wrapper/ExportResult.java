package gov.nist.healthcare.cds.domain.wrapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportResult {
	private List<ExportedFileStream> reader;
	
	public ExportResult() {
		super();
		reader = new ArrayList<>();
	}

	public boolean add(String name, InputStream e) {
		return reader.add(new ExportedFileStream(name, e));
	}

	public List<ExportedFileStream> getReader() {
		return reader;
	}

	public void setReader(List<ExportedFileStream> reader) {
		this.reader = reader;
	}

	
}
