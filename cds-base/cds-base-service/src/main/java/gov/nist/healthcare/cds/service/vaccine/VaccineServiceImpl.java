package gov.nist.healthcare.cds.service.vaccine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.exception.ProductNotFoundException;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.repositories.VaccineGroupRepository;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.VaccineService;

@Service
public class VaccineServiceImpl implements VaccineService {
	
	@Autowired
	private VaccineMappingRepository repo;
	@Autowired
	private VaccineRepository vaccineRepository;
	@Autowired
	private VaccineGroupRepository vaccineGpRepository;

	@Override
	public Injection getVax(VaccineRef ref, boolean ignoreMvxInCaseOfFailure) throws ProductNotFoundException, VaccineNotFoundException {
		
		VaccineMapping mapping = repo.findMapping(ref.getCvx());
		if(mapping == null) throw new VaccineNotFoundException(ref.getCvx());
		
		switch(typeOf(ref)){
		case "generic" : return mapping.getVx();
		case "specific" :	try {
								return getProduct(ref.getCvx(), ref.getMvx());
							}
							catch(ProductNotFoundException pnf){
								if(ignoreMvxInCaseOfFailure) return mapping.getVx();
								else throw pnf;
							}
		}
		return null;
	}

	@Override
	public String typeOf(Injection inject) {
		if(inject instanceof Vaccine) return "generic";
		if(inject instanceof Product) return "specific";
		return "";
	}

	@Override
	public Product asProduct(Injection inject) throws IllegalArgumentException {
		if(typeOf(inject).equals("specific")) return (Product) inject; 
		else throw new IllegalArgumentException();
	}

	@Override
	public Vaccine asVaccine(Injection inject) {
		if(typeOf(inject).equals("generic")) return (Vaccine) inject; 
		else if(typeOf(inject).equals("specific")) return asProduct(inject).getVx();
		else throw new IllegalArgumentException();
	}

	@Override
	public Set<Product> productsOf(String cvx) throws VaccineNotFoundException {
		VaccineMapping vxm = repo.findMapping(cvx);
		if(vxm == null) throw new VaccineNotFoundException(cvx);
		return vxm.getProducts();
	}

	@Override
	public boolean isGroupOf(String vax, String cvx) throws VaccineNotFoundException {
		VaccineMapping mapping = repo.findMapping(vax);
		if(mapping == null) throw new VaccineNotFoundException(vax);
		Set<VaccineGroup> groups = mapping.getGroups();
		if(groups != null && groups.size() > 0){
			for(VaccineGroup group : groups){
				if(group.getCvx().equals(cvx))
					return true;
			}
		}
		else {
			return vax.equals(cvx);
		}
		
		return false;
	}

	@Override
	public String typeOf(VaccineRef inject) {
		if(inject.getCvx() != null && !inject.getCvx().isEmpty() && inject.isHasMvx()) return "specific";
		if(inject.getCvx() != null && !inject.getCvx().isEmpty() && !inject.isHasMvx()) return "generic";
		return "";
	}

	@Override
	public Product getProduct(String cvx, String mvx) throws ProductNotFoundException, VaccineNotFoundException {
		Set<Product> products = productsOf(cvx);
		for(Product p : products){
			if(p.getMx().getMvx().equals(mvx))
				return p;
		}
		throw new ProductNotFoundException(cvx, mvx);
	}
	
	@Override
	public Product getProduct(VaccineRef ref) throws ProductNotFoundException, VaccineNotFoundException {
		return getProduct(ref.getCvx(), ref.getMvx());
	}

	@Override
	public Vaccine findGroup(String name) throws VaccineNotFoundException, IllegalArgumentException {
		Hashtable<String, String> transform = new Hashtable<String, String>();
		transform.put("POL", "POLIO");
		transform.put("PCV", "PneumoPCV");
		transform.put("ROTA", "ROTAVIRUS");
		transform.put("MCV", "MeningB");
		transform.put("VAR", "VARICELLA");
		String transKey = transform.containsKey(name.toUpperCase()) ? transform.get(name.toUpperCase()) : name;

		//-- Vaccine
		Vaccine vax = vaccineRepository.findByNameIgnoreCase(transKey);
		if(vax != null){
			VaccineMapping mp = repo.findMapping(vax.getCvx());
			if(mp.getGroups().size() == 1) {
				VaccineGroup grp = mp.getGroups().toArray(new VaccineGroup[0])[0];
				return getVaccine(grp);
			}
			else 
				return vax;
		}
		
		//-- Group
		VaccineGroup vaxGp = vaccineGpRepository.findByNameIgnoreCase(transKey);
		if(vaxGp != null){
			return getVaccine(vaxGp);
		}
		
		throw new VaccineNotFoundException(name);
	}
	
	public Vaccine getVaccine(VaccineGroup grp){
		return vaccineRepository.findOne(grp.getCvx());
	}

	@Override
	public Set<VaccineGroup> groupsOf(String cvx) throws VaccineNotFoundException {
		VaccineMapping mapping = repo.findMapping(cvx);
		if(mapping == null) throw new VaccineNotFoundException(cvx);
		Set<VaccineGroup> groups = mapping.getGroups();
		if(groups != null && groups.size() > 0){
			return groups;
		}
		else
			return new HashSet<>(Arrays.asList(new VaccineGroup(cvx)));
		
	}



}
