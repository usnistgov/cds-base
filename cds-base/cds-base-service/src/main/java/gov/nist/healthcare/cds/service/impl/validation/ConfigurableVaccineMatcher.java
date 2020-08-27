package gov.nist.healthcare.cds.service.impl.validation;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.EqTuple;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.LoggerService;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.VaccineService;
import gov.nist.healthcare.cds.service.vaccine.VaccineMatcherConfiguration;

public class ConfigurableVaccineMatcher implements VaccineMatcherService {
	
	@Autowired
	private VaccineMatcherConfiguration configuration;
	@Autowired
	private VaccineService vaxService;
	
	
	@Override
	public int match(VaccineRef ref, Injection i, StringBuilder logs) {
		try {
			LoggerService.banner("CHECKING AGAINST", logs, false, 1);
			LoggerService.vaccineRef(ref, logs, true, 0);
			
			
			Injection provided = vaxService.getVax(ref, true);

			if(this.straightMatch(provided, i, logs)) return 1;
			else if(this.groupMatch(provided, i, logs)|| this.customMatch(provided, i, logs)) return 2;
			else return 0;
		
		} catch (Exception e) {
			return 0;
		}
	}
	
	private boolean straightMatch(Injection p, Injection i, StringBuilder logs){
		LoggerService.banner("STRAIGHT MATCH CHECK", logs, true, 2);
		
		if(!p.getCvx().equals(i.getCvx())) {
			LoggerService.fail("CVX does not match", logs, 3);
			return false;
		}
		else if(vaxService.typeOf(p).equals(vaxService.typeOf(i)) && vaxService.typeOf(p).equals("specific")){
			boolean mvx = vaxService.asProduct(p).getMx().getMvx().equals(vaxService.asProduct(i).getMx().getMvx());
			LoggerService.passOrFail(mvx, "CVX and MVX match", "CVX does match, but MVX does not match on both specific products", logs, 3);
			return mvx;
		}
		LoggerService.pass("CVX does match and both injections are generic", logs, 3);
		return true;
	}
	
	private boolean groupMatch(Injection p, Injection i, StringBuilder logs) throws VaccineNotFoundException{
		LoggerService.banner("SAME GROUP MATCH CHECK", logs, true, 2);
		Set<VaccineGroup> pGroups = vaxService.groupsOf(p.getCvx());
		Set<VaccineGroup> iGroups = vaxService.groupsOf(i.getCvx());
		
		LoggerService.comment("Administered Vaccine Group", logs, false, 3);
		LoggerService.vaccineGroupSet(iGroups, logs, 0);
		logs.append("\n");
		LoggerService.comment("Candidate Vaccine Group", logs, false, 3);
		LoggerService.vaccineGroupSet(pGroups, logs, 0);
		logs.append("\n");
		
		boolean b = pGroups.size() > 0 && iGroups.size() > 0 && pGroups.equals(iGroups);
		
		LoggerService.passOrFail(b, "Both vaccinations belong to the same group(s)", "Vaccinations belong to different groups", logs, 3);
		return b;
	}
	
	private boolean customMatch(Injection p, Injection i, StringBuilder logs) throws VaccineNotFoundException{
		LoggerService.banner("CUSTOM MAPPING GROUP MATCH CHECK", logs, true, 2);
		Set<VaccineGroup> pGroups = vaxService.groupsOf(p.getCvx());
		Set<VaccineGroup> iGroups = vaxService.groupsOf(i.getCvx());
		
		Set<String> pGroupsC = transform(pGroups);
		Set<String> iGroupsC = transform(iGroups);
		
		LoggerService.comment("Administered Vaccine Group Transformed to "+iGroupsC, logs, false, 3);
		logs.append("\n");
		LoggerService.comment("Candidate Vaccine Group Transformed to"+pGroupsC, logs, false, 3);
		logs.append("\n");
		
		boolean b = pGroupsC.size() > 0 && iGroupsC.size() > 0 && pGroupsC.equals(iGroupsC);
		LoggerService.passOrFail(b, "Both vaccinations belong to the same custom mapped group(s)", "Vaccinations belong to different groups", logs, 3);
		return b;
	}
	
	
	private Set<String> transform(Set<VaccineGroup> pGroups){
		Set<String> pGroupsC = new HashSet<>();
		for(VaccineGroup grp : pGroups){

			EqTuple tuple = configuration.tupleFor(grp.getCvx());
			if(tuple != null){
				pGroupsC.add(tuple.getEq());
			}
			else{
				pGroupsC.add(grp.getCvx());
			}
		}
		return pGroupsC;
	}

}
