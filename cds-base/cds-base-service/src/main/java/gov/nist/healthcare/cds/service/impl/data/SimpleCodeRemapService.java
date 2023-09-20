package gov.nist.healthcare.cds.service.impl.data;

import com.google.common.base.Strings;
import gov.nist.healthcare.cds.domain.*;
import gov.nist.healthcare.cds.repositories.*;
import gov.nist.healthcare.cds.service.VaccineImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class SimpleCodeRemapService {

    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private VaccineRepository vaccineRepository;
    @Autowired
    private VaccineMappingRepository vaccineMappingRepository;
    @Autowired
    private VaccineGroupRepository vaccineGroupRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private VaccineImportService vaccineService;

    private Set<String> changes;

    public void reloadCodeSetsAndRemapTestCases(InputStream cvx, InputStream vg,InputStream mvx, InputStream products,   Map<String, String> cvxMapping, Map<String, String> productMapping) throws Exception {
        this.vaccineRepository.deleteAll();
        this.vaccineMappingRepository.deleteAll();
        this.vaccineGroupRepository.deleteAll();
        this.manufacturerRepository.deleteAll();
        this.productRepository.deleteAll();
        Set<VaccineMapping> set = vaccineService._import(cvx, vg,mvx,products);
        vaccineMappingRepository.save(set);
        this.checkVaccineCodeIntegrity();
        this.checkTestCasesVaccineIntegrity(cvxMapping, productMapping);
    }

    public boolean checkVaccineCodeIntegrity() throws Exception {
        // Check Mappings
        List<VaccineMapping> mappings = this.vaccineMappingRepository.findAll();
        for(VaccineMapping mapping: mappings) {
            String CVX = mapping.getId();
            if(mapping.getVx() == null || this.vaccineRepository.findOne(CVX) == null) {
                throw new Exception("(Mapping) " + mapping.getId() + " vaccine is null or not found");
            }
            if(mapping.isGroup() && this.vaccineGroupRepository.findByCvx(CVX) == null) {
                throw new Exception("(Mapping) " + mapping.getId() + " is group but vaccine group not found");
            }

            Set<VaccineGroup> groupSet = mapping.getGroups();
            for(VaccineGroup group: groupSet) {
                if(this.vaccineGroupRepository.findByCvx(group.getCvx()) == null) {
                    throw new Exception("(Mapping) " + mapping.getId() + "'s group " + group.getCvx() + " not found ");
                }
            }

            Set<Product> products = mapping.getProducts();
            for(Product product: products) {
                if(this.productRepository.findOne(product.getId()) == null) {
                    throw new Exception("(Mapping) " + mapping.getId() + "'s product " + product.getId() + " not found ");
                }
            }
        }

        // Check Groups
        List<VaccineGroup> vaccineGroups = this.vaccineGroupRepository.findAll();
        for(VaccineGroup group: vaccineGroups) {
            String CVX = group.getCvx();
            if(this.vaccineRepository.findOne(CVX) == null) {
                throw new Exception("(Group) " + CVX + " vaccine not found");
            }
            if(this.vaccineMappingRepository.findOne(CVX) == null) {
                throw new Exception("(Group) " + CVX + " mapping not found");
            }
        }

        // Check Vaccines
        List<Vaccine> vaccines = this.vaccineRepository.findAll();
        for(Vaccine vaccine: vaccines) {
            String CVX = vaccine.getCvx();
            if(this.vaccineMappingRepository.findOne(CVX) == null) {
                throw new Exception("(Vaccine) " + CVX + " mapping not found");
            }
        }

        // Check Products
        List<Product> products = this.productRepository.findAll();
        for(Product product: products) {
            String CVX = product.getCvx();
            String MVX = product.getMvx();
            if(this.vaccineMappingRepository.findOne(CVX) == null) {
                throw new Exception("(Product) " + product.getId() + " mapping for " + CVX + " not found");
            }
            if(this.manufacturerRepository.findByMvx(MVX) == null) {
                throw new Exception("(Product) " + product.getId() + " manufacturer " + MVX + " not found");
            }
        }
        return true;
    }

    public boolean checkTestCasesVaccineIntegrity(Map<String, String> cvx, Map<String, String> product) throws Exception {
        List<TestCase> testCases = this.testCaseRepository.findAll();
        List<String> updates = new ArrayList<>();
        int tcc = 0;
        int fc = 0;
        int ev = 0;
        int evl = 0;
        changes = new HashSet<>();
        for(TestCase tc: testCases) {
            boolean updated = false;
            for(Event e : tc.getEvents()) {
                // Vaccination Events
                if(e instanceof VaccinationEvent) {
                    updated = updated | this.mapOrCheckVaccinationEvent((VaccinationEvent) e, cvx, product);
                    if(((VaccinationEvent) e).getEvaluations() != null) {
                        for(ExpectedEvaluation expectedEvaluation: ((VaccinationEvent) e).getEvaluations()) {
                            updated = updated | this.mapOrCheckVaccinationEventEvaluation(expectedEvaluation, cvx);
                            evl++;
                        }
                    }
                    ev++;
                }
            }
            //Expected Forecasts
            for(ExpectedForecast expectedForecast: tc.getForecast()) {
                updated = updated | this.mapOrCheckForecast(expectedForecast, cvx);
                fc++;
            }

            if(updated) {
                updates.add(tc.getId());
            }
            tcc++;
        }

        Set<String> users = new HashSet<>();
        this.testCaseRepository.findAll(updates).forEach((tc) -> users.add(tc.getUser()));
        this.testCaseRepository.save(testCases);
        System.out.println("[TEST_CASE_REMAP] testcases : " + tcc +" , events: "+ev+" , evaluations: "+evl+", forecasts: " + fc);
        System.out.println("[TEST_CASE_REMAP] remapped " + updates.size() + " targets " + updates + " from users " + users);
        System.out.println(String.join("\n", changes));
        changes = new HashSet<>();
        return updates.size() > 0;
    }

    public boolean mapOrCheckVaccinationEvent(VaccinationEvent event, Map<String, String> cvx, Map<String, String> product) throws Exception {
        return this.mapOrCheckInjection(event.getAdministred(), cvx, product);
    }

    public boolean mapOrCheckVaccinationEventEvaluation(ExpectedEvaluation evaluation, Map<String, String> cvx) throws Exception {
        return this.mapOrCheckVaccine(evaluation.getRelatedTo(), cvx);
    }

    public boolean mapOrCheckForecast(ExpectedForecast forecast, Map<String, String> cvx) throws Exception {
        return this.mapOrCheckVaccine(forecast.getTarget(), cvx);
    }

    public boolean mapOrCheckInjection(Injection injection, Map<String, String> cvx, Map<String, String> product) throws Exception {
        if(injection instanceof Vaccine) {
            return this.mapOrCheckVaccine((Vaccine) injection, cvx);
        } else if(injection instanceof Product){
            return this.mapOrCheckProduct((Product) injection, product);
        } else {
            return false;
        }
    }

    public boolean mapOrCheckVaccine(Vaccine vaccine, Map<String, String> cvx) throws Exception {
        if(vaccine == null) {
            System.out.println("Vaccine NULL");
            return false;
        }
        String remap = cvx.get(vaccine.getCvx());
        String code = vaccine.getCvx();
        if(!Strings.isNullOrEmpty(remap)) {
            Vaccine vx = this.vaccineRepository.findOne(remap);
            if(vx == null) {
                throw new Exception("Remapping vaccine " + vaccine.getCvx() + " to " + remap + " failed (target not found)");
            }
            vaccine.setDetails(vx.getDetails());
            vaccine.setId(vx.getId());
            vaccine.setName(vx.getName());
            vaccine.setCvx(vx.getCvx());
            System.out.println("Remapping vaccine " + code + " to " + remap);
            return true;
        } else if(this.vaccineRepository.findOne(vaccine.getId()) == null) {
            throw new Exception("Vaccine " + vaccine.getCvx() + " in testcase not found");
        } else {
            Vaccine v = this.vaccineRepository.findOne(vaccine.getId());
            boolean name = v.getName().equals(vaccine.getName());
            boolean details = v.getDetails().equals(vaccine.getDetails());
            if(!name || !details) {
                if(!name) {
                    changes.add("Changing Vaccine "+ vaccine.getId() +" name from '"+vaccine.getName()+"' to '" + v.getName() +"'");
                }
                if(!details) {
                    changes.add("Changing Vaccine "+ vaccine.getId() +" Details from '"+vaccine.getDetails()+"' to '" + v.getDetails() +"'");
                }
                vaccine.setName(v.getName());
                vaccine.setDetails(v.getDetails());
                return true;
            }
        }
        return false;
    }

    public boolean mapOrCheckProduct(Product p, Map<String, String> product) throws Exception {
        if(product == null) {
            System.out.println("Product NULL");
            return false;
        }
        String remap = product.get(p.getCode());
        String code = p.getCode();
        if(!Strings.isNullOrEmpty(remap)) {
            Product prd = this.productRepository.findOne(remap);
            if(prd == null) {
                throw new Exception("Remapping product " + p.getCode() + " to " + remap + " failed (target not found)");
            }
            p.setCode(prd.getCode());
            p.setMx(prd.getMx());
            p.setName(prd.getName());
            p.setVx(prd.getVx());
            p.setId(prd.getId());
            System.out.println("Remapping product " + code + " to " + remap);
            return true;
        } else if(this.productRepository.findOne(p.getCode()) == null) {
            throw new Exception("Product " +  p.getCode() + " in testcase not found");
        } else {
            Product existing = this.productRepository.findOne(p.getCode());
            boolean name = existing.getName().equals(p.getName());
            boolean vx = existing.getCvx().equals(p.getCvx());
            boolean mx = existing.getMvx().equals(p.getMvx());
            if(!name || !vx || !mx) {
                p.setName(existing.getName());
                p.setVx(existing.getVx());
                p.setMx(existing.getMx());
                if(!name) {
                    changes.add("Changing Product "+p.getId()+" name from '"+p.getName()+"' to '" + existing.getName() +"'");
                }
                if(!vx) {
                    changes.add("Changing Product "+p.getId()+" CVX from '"+p.getCvx()+"' to '" + existing.getCvx() +"'");
                }
                if(!mx) {
                    changes.add("Changing Product "+p.getId()+" MVX from '"+p.getMvx()+"' to '" + existing.getMvx() +"'");
                }
                return true;
            }
        }
        return false;
    }


}
