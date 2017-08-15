package gov.nist.healthcare.cds.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValidationJob {

	@Id
	private String id;
	private java.util.Date startedOn;
	private java.util.Date finishedOn;
	private java.util.Date assessment;
	private SoftwareConfig target;
	private JobExecutionStatus status;
	private String initiator;
	private List<TestCase> runningQueue;
	private List<ExecutionFault> faults;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public List<TestCase> getRunningQueue() {
		return runningQueue;
	}
	public void setRunningQueue(List<TestCase> runningQueue) {
		this.runningQueue = runningQueue;
	}
	public java.util.Date getStartedOn() {
		return startedOn;
	}
	public void setStartedOn(java.util.Date startedOn) {
		this.startedOn = startedOn;
	}
	public java.util.Date getFinishedOn() {
		return finishedOn;
	}
	public void setFinishedOn(java.util.Date finishedOn) {
		this.finishedOn = finishedOn;
	}
	public JobExecutionStatus getStatus() {
		return status;
	}
	public void setStatus(JobExecutionStatus status) {
		this.status = status;
	}
	public java.util.Date getAssessment() {
		return assessment;
	}
	public void setAssessment(java.util.Date assessment) {
		this.assessment = assessment;
	}
	public SoftwareConfig getTarget() {
		return target;
	}
	public void setTarget(SoftwareConfig target) {
		this.target = target;
	}
	public List<ExecutionFault> getFaults() {
		return faults;
	}
	public void setFaults(List<ExecutionFault> faults) {
		this.faults = faults;
	}
	
	
}
