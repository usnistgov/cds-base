package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.domain.wrapper.Report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.data.mongodb.core.mapping.Document;

import com.mysql.fabric.xmlrpc.base.Array;


public class TestExecution implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707759373751607645L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private java.util.Date executionDate;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private SoftwareConfig software;
	private List<Report> reports;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
