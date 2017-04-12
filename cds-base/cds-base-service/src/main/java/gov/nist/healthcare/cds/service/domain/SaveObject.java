package gov.nist.healthcare.cds.service.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class SaveObject {

	private TestPlan tp;
	private TestCase tc;
	@Autowired
	private TestCaseRepository tcRepository;
	@Autowired
	private TestPlanRepository tpRepository;
	@Autowired
	protected MetaDataService mdService;
	
	public TestPlan getTp() {
		return tp;
	}
	public void setTp(TestPlan tp) {
		this.tp = tp;
	}
	public TestCase getTc() {
		return tc;
	}
	public void setTc(TestCase tc) {
		this.tc = tc;
	}
	public boolean hasTestCase(){
		return tc != null;
	}
	public boolean hasTestPlan(){
		return tp != null;
	}	
	
	public void save() {
		if(hasTestCase()){
			this.mdService.update(getTc().getMetaData());
			tcRepository.save(getTc());
		}
		if(hasTestPlan()){
			this.mdService.update(getTp().getMetaData());
			tpRepository.save(getTp());
		}
	}
	public void clear() {
		tp = null;
		tc = null;
	}
	
}


