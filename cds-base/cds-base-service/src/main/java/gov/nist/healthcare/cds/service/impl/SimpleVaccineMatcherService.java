package gov.nist.healthcare.cds.service.impl;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.VaccineMatcherService;

public class SimpleVaccineMatcherService implements VaccineMatcherService {

	@Override
	public boolean match(VaccineRef ref, Injection i) {
		
		if(i instanceof Product){
			Product p = (Product) i;
			boolean cvx = p.getVx().getCvx().equals(ref.getCvx());
			boolean mvx = (!p.getMx().getMvx().isEmpty() && p.getMx().getMvx().equals(ref.getMvx())) ||  p.getMx().getMvx().isEmpty();
			return cvx && mvx;
		}
		else if (i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			return v.getCvx().equals(ref.getCvx());
			
		}
		else {
			return false;
		}
	}

}
