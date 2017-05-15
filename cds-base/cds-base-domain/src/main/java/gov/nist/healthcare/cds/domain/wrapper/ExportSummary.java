package gov.nist.healthcare.cds.domain.wrapper;

import java.io.IOException;
import java.util.List;

public abstract class ExportSummary {

	protected List<EntityResult> testCases;
	protected int all;
	protected int werrors;
	
	public abstract void add(ExportResult toAdd,List<String> names) throws IOException;
	
}
