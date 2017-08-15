package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.JobStatus;

public class JobExecutionStatus {

	private JobStatus status;
	private double progress;
	
	public JobStatus getStatus() {
		return status;
	}
	public void setStatus(JobStatus status) {
		this.status = status;
	}
	public double getProgress() {
		return progress;
	}
	public void setProgress(double progress) {
		this.progress = progress;
	}
	
}
