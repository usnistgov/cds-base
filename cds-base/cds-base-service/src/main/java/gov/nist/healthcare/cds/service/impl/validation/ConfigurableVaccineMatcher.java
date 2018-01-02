package gov.nist.healthcare.cds.service.impl.validation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.EqTuple;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.VaccineService;
import gov.nist.healthcare.cds.service.vaccine.VaccineMatcherConfiguration;

public class ConfigurableVaccineMatcher implements VaccineMatcherService {
	
	@Autowired
	private VaccineMatcherConfiguration configuration;
	@Autowired
	private VaccineService vaxService;
	
	
	@Override
	public boolean match(VaccineRef ref, Injection i) {
		try {
			Injection provided = vaxService.getVax(ref, true);
			return this.straightMatch(provided, i) || this.groupMatch(provided, i)|| this.customMatch(provided, i);
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean straightMatch(Injection p, Injection i){
		
		if(!p.getCvx().equals(i.getCvx())) 
			return false;
		else if(vaxService.typeOf(p).equals(vaxService.typeOf(i)) && vaxService.typeOf(p).equals("specific")){
			return vaxService.asProduct(p).getMx().getMvx().equals(vaxService.asProduct(i).getMx().getMvx());
		}
		return true;
	}
	
	private boolean groupMatch(Injection p, Injection i) throws VaccineNotFoundException{
		Set<VaccineGroup> pGroups = vaxService.groupsOf(p.getCvx());
		Set<VaccineGroup> iGroups = vaxService.groupsOf(i.getCvx());
		
		return pGroups.size() > 0 && iGroups.size() > 0 && pGroups.equals(iGroups);
	}
	
	private boolean customMatch(Injection p, Injection i) throws VaccineNotFoundException{
		Set<VaccineGroup> pGroups = vaxService.groupsOf(p.getCvx());
		Set<VaccineGroup> iGroups = vaxService.groupsOf(i.getCvx());
		
		Set<String> pGroupsC = transform(pGroups);
		Set<String> iGroupsC = transform(iGroups);
		
		return pGroupsC.size() > 0 && iGroupsC.size() > 0 && pGroupsC.equals(iGroupsC);
	}
	
	
	private Set<String> transform(Set<VaccineGroup> pGroups){
		Set<String> pGroupsC = new HashSet<>();
		for(VaccineGroup grp : pGroups){

			EqTuple tuple = configuration.tupleFor(grp.getCvx());
			if(tuple != null){
				System.out.println(tuple.getEq());
				pGroupsC.add(tuple.getEq());
			}
			else{
				System.out.println(grp.getCvx());
				pGroupsC.add(grp.getCvx());
			}
		}
		return pGroupsC;
	}

}
