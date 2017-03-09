package gov.nist.healthcare.cds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.VaccineMatcherService;

@Service
public class SimpleVaccineMatcherService implements VaccineMatcherService {

	@Autowired
	private VaccineMappingRepository vmRepo;
	
	@Override
	public boolean match(VaccineRef ref, Injection i) {
		
		if(i instanceof Product){
			Product p = (Product) i;
			if(!ref.isHasMvx()){
				return ref.getCvx().equals(p.getVx().getCvx()) || this.isGroupOf(ref.getCvx(), p.getVx().getCvx()) || this.isSiblingOf(ref.getCvx(), p.getVx().getCvx());
			}
			else {
				return ref.getCvx().equals(p.getVx().getCvx()) && ref.getMvx().equals(p.getMx().getMvx());
			}
		}
		else if (i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			return v.getCvx().equals(ref.getCvx()) || this.isGroupOf(ref.getCvx(), v.getCvx()) || this.isSiblingOf(ref.getCvx(), v.getCvx());
		}
		else {
			return false;
		}
	}
	
	public boolean isSiblingOf(String cvx, String iCvx){
		VaccineMapping refMp = vmRepo.findMapping(cvx);
		VaccineMapping iMp = vmRepo.findMapping(iCvx);
		return refMp != null && refMp.getGroups() != null && iMp.getGroups() != null && refMp.getGroups().size() != 0 && refMp.getGroups().size() == iMp.getGroups().size() && refMp.getGroups().containsAll(iMp.getGroups());
	}
	
	public boolean isGroupOf(String cvx, String iCvx){
		VaccineMapping mp = vmRepo.findMapping(iCvx);
		VaccineGroup vg = new VaccineGroup();
		vg.setCvx(cvx);
		return mp.getGroups() != null && mp.getGroups().contains(vg);
	}

}
