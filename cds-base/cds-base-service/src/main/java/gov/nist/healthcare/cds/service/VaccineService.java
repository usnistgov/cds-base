package gov.nist.healthcare.cds.service;

import java.util.List;
import java.util.Set;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.exception.ProductNotFoundException;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;

public interface VaccineService {
	public Injection getVax(VaccineRef ref, boolean ignoreMvxInCaseOfFailure) throws ProductNotFoundException, VaccineNotFoundException;
	public String typeOf(Injection inject);
	public String typeOf(VaccineRef inject);
	public Product getProduct(String cvx, String mvx) throws ProductNotFoundException, VaccineNotFoundException;
	public Product asProduct(Injection inject) throws IllegalArgumentException;
	public Vaccine asVaccine(Injection inject);
	public Set<Product> productsOf(String cvx) throws VaccineNotFoundException;
	public Product getProduct(VaccineRef ref) throws ProductNotFoundException, VaccineNotFoundException;
	public boolean isGroupOf(String vax, String cvx) throws VaccineNotFoundException;
	public Vaccine findGroup(String name) throws VaccineNotFoundException, IllegalArgumentException;
	public Set<VaccineGroup> groupsOf(String cvx) throws VaccineNotFoundException;
}
