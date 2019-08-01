package gov.nist.healthcare.cds.service.impl.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.VaccineService;

@Deprecated
public class SimpleVaccineMatcherService implements VaccineMatcherService {

	@Autowired
	private VaccineMappingRepository vmRepo;
	@Autowired
	private VaccineService vaccineService;
	
	@Override
	public int match(VaccineRef ref, Injection i, StringBuilder log) {
		return 0;
	}
	
	public boolean isSiblingOf(String cvx, String iCvx){
		VaccineMapping refMp = vmRepo.findMapping(cvx);
		VaccineMapping iMp = vmRepo.findMapping(iCvx);
		return refMp != null && refMp.getGroups() != null && iMp.getGroups() != null && refMp.getGroups().size() != 0 && refMp.getGroups().size() == iMp.getGroups().size() && areEqual(refMp.getGroups(),iMp.getGroups());
	}
	
	public boolean areEqual(Set<VaccineGroup> vgRef, Set<VaccineGroup> vgF){
		List<VaccineGroup> td_dtap = new ArrayList<VaccineGroup>();
		VaccineGroup vgTd = new VaccineGroup();
		VaccineGroup vgTdp = new VaccineGroup();
		VaccineGroup vgDt = new VaccineGroup();
		vgTd.setCvx("139");
		vgDt.setCvx("107");
		vgTdp.setCvx("115");
		td_dtap.add(vgDt);
		td_dtap.add(vgTd);
		td_dtap.add(vgTdp);
		
		if(vgRef.containsAll(vgF)){
			return true;
		}
		else {
			if(vgRef.size() == 1 && vgF.size() == 1){
				VaccineGroup ref = (VaccineGroup) vgRef.toArray()[0];
				VaccineGroup f = (VaccineGroup) vgF.toArray()[0];
				return td_dtap.contains(ref) && td_dtap.contains(f);
			}
			return false;
		}
	}
	
	public boolean isGroupOf(String cvx, String iCvx){
		

//		if((cvx.equals("107") || cvx.equals("115") || cvx.equals("09") || cvx.equals("139") || cvx.equals("113") || cvx.equals("138")) &&  (iCvx.equals("107") || iCvx.equals("115") || iCvx.equals("09") || iCvx.equals("139") || iCvx.equals("113") || iCvx.equals("138"))){
//			return true;
//		}
		VaccineMapping mp = vmRepo.findMapping(iCvx);
		VaccineGroup vg = new VaccineGroup();
		vg.setCvx(cvx);
		return mp.getGroups() != null && mp.getGroups().contains(vg);
	}
	

	

}
