package gov.nist.healthcare.cds.service.impl;

import gov.nist.healthcare.cds.domain.*;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.domain.wrapper.TestCasePayLoad;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.TestCaseUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SimpleTestCaseUtilService implements TestCaseUtilService {

    @Autowired
    private DateService dates;

    public SimpleTestCaseUtilService() {
    }

    private SimpleTestCaseUtilService(DateService dates) {
        this.dates = dates;
    }

    @Override
    public TestCasePayLoad getTestCasePayload(TestCase testCase, LocalDate evaluationDate) throws UnresolvableDate {
        // Fix Eval, DOB, Events
        ResolvedDates rds = dates.resolveDates(testCase, evaluationDate);
        // Create PayLoad and Send request
        return this.getTestCasePayload(testCase, rds);
    }

    @Override
    public TestCasePayLoad getTestCasePayload(TestCase tc, ResolvedDates rds) throws UnresolvableDate {
        TestCasePayLoad tcP = new TestCasePayLoad();
        tcP.setGender(tc.getPatient().getGender());
        tcP.setEvaluationDate(rds.getEval());
        tcP.setDateOfBirth(rds.getDob());
        for(Event e : tc.getEvents()){
            if(e instanceof VaccinationEvent){
                VaccinationEvent ve = (VaccinationEvent) e;
                VaccineRef vr = this.vaccineRef(ve.getAdministred());
                LocalDate dA = rds.getEvents().get(ve.getPosition());
                tcP.addImmunization(vr, dA);
            }
        }
        return tcP;
    }

    private VaccineRef vaccineRef(Injection i){
        if(i instanceof Product){
            Product p = (Product) i;
            return new VaccineRef(p.getVx().getCvx(), p.getMx().getMvx());
        }
        else {
            Vaccine v = (Vaccine) i;
            return new VaccineRef(v.getCvx(), "");
        }
    }

    public static SimpleTestCaseUtilService getInstance() {
        return new SimpleTestCaseUtilService(SimpleDateService.getInstance());
    }

}
