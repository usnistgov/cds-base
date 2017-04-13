package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.domain.wrapper.Report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TestExecution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707759373751607645L;
	
	private java.util.Date executionDate;
	private SoftwareConfig software;
	private List<Report> reports;

	
	public SoftwareConfig getSoftware() {
		return software;
	}
	public void setSoftware(SoftwareConfig software) {
		this.software = software;
	}
	public java.util.Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(java.util.Date executionDate) {
		this.executionDate = executionDate;
	}
	public List<Report> getReports() {
		if(reports == null){
			reports = new ArrayList<Report>();
		}
		return reports;
	}
	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
	
}
