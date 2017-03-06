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
			boolean cvx = p.getVx().getCvx().equals(ref.getCvx());
			boolean mvx = (!p.getMx().getMvx().isEmpty() && p.getMx().getMvx().equals(ref.getMvx())) ||  p.getMx().getMvx().isEmpty();
			return cvx && mvx || this.isGroupOf(ref.getCvx(), i);
		}
		else if (i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			return v.getCvx().equals(ref.getCvx()) || this.isGroupOf(ref.getCvx(), i);
			
		}
		else {
			return false;
		}
	}
	
	public boolean isGroupOf(String cvx, Injection i){
		if(i instanceof Product){
			Product p = (Product) i;
			VaccineMapping mp = vmRepo.findMapping(p.getVx().getCvx());
			for(VaccineGroup vg : mp.getGroups()){
				if(vg.getCvx().equals(cvx))
					return true;
			}
			return false;
		}
		else if (i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			VaccineMapping mp = vmRepo.findMapping(v.getCvx());
			for(VaccineGroup vg : mp.getGroups()){
				if(vg.getCvx().equals(cvx))
					return true;
			}
			return false;
		}
		else {
			return false;
		}
	}

}
