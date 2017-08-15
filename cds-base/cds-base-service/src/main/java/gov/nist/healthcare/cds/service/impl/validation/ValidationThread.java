package gov.nist.healthcare.cds.service.impl.validation;


import gov.nist.healthcare.cds.domain.ExecutionFault;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.ValidationJob;
import gov.nist.healthcare.cds.domain.exception.ConnectionException;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.service.TestCaseExecutionService;

public class ValidationThread implements Runnable {

	private TestCaseExecutionService executionService;
	private ValidationJob job;
	
	
	public ValidationThread(TestCaseExecutionService executionService, ValidationJob job) {
		super();
		this.executionService = executionService;
		this.job = job;
	}

	@Override
	public void run() {
		//---- Create Results Folder
		for(TestCase tc : job.getRunningQueue()){
			try {
				
				Report report = executionService.execute(job.getTarget(), tc,job.getAssessment());
				
				
				
				
				
				
				
				
			} catch (UnresolvableDate | ConnectionException e) {
				job.getFaults().add(new ExecutionFault(tc.getId(), e));
			}
			
			
			
		}
		
	}

}
