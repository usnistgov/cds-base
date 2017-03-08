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
			System.out.println(ref);
			if(!ref.getMvx().isEmpty()){
				return ref.getCvx().equals(p.getVx().getCvx()) || this.isGroupOf(ref.getCvx(), i);
			}
			else {
				return ref.getCvx().equals(p.getVx().getCvx()) && ref.getMvx().equals(p.getMx().getMvx());
			}
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
		System.out.println("[VMATCHER] cvx "+cvx);
		if(i instanceof Product){
			Product p = (Product) i;
			System.out.println("[VMATCHER] p.cvx "+p.getVx().getCvx());
			VaccineMapping mp = vmRepo.findMapping(p.getVx().getCvx());
			for(VaccineGroup vg : mp.getGroups()){
				System.out.println("[VMATCHER] groups : "+vg.getCvx());
				if(vg.getCvx().equals(cvx))
					return true;
			}
			return false;
		}
		else if (i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			VaccineMapping mp = vmRepo.findMapping(v.getCvx());
			System.out.println("[VMATCHER] v.cvx "+v.getCvx());
			for(VaccineGroup vg : mp.getGroups()){
				System.out.println("[VMATCHER] groups : "+vg.getCvx());
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
