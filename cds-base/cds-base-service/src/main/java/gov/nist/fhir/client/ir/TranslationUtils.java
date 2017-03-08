/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.fhir.client.ir;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.wrapper.ActualEvaluation;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.ResponseVaccinationEvent;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.SerieStatus;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.emf.common.util.EList;
import org.hl7.fhir.Immunization;
import org.hl7.fhir.ImmunizationRecommendation;
import org.hl7.fhir.ImmunizationRecommendationDateCriterion;
import org.hl7.fhir.ImmunizationRecommendationRecommendation;
import org.hl7.fhir.ImmunizationVaccinationProtocol;

/**
 *
 * @author mccaffrey
 */
public class TranslationUtils {

    // TODO: Surely this must already exist somewhere else???
    public static final String IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_DUE = "due";
    public static final String IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_EARLIEST = "earliest";
    public static final String IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_OVERDUE = "overdue";
    public static final String IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_LATEST = "latest";

    public static String translateCsdiDateToFhirDate(FixedDate date) {
        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
        return print.format(date.getDate());
    }
    
    public static String translateJavaDateToFhirDate(Date date) {
        SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd");
        return print.format(date);
    }
    
    public static FixedDate translateTchDateToFhirDate(String date) {

        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);

        return new FixedDate(month + '/' + day + '/' + year);

    }

    public static ResponseVaccinationEvent translateImmunizationToResponseVaccinationEvent(Immunization imm) {

        ResponseVaccinationEvent rve = new ResponseVaccinationEvent();
        VaccineRef vaccineRef = new VaccineRef();
        //TODO: Error checking
        vaccineRef.setCvx(imm.getVaccineCode().getCoding().get(0).getCode().getValue());
        rve.setAdministred(vaccineRef);

        rve.setDate(TranslationUtils.translateTchDateToFhirDate(imm.getDate().getValue().toString()));
        rve.setEvaluations(new HashSet<ActualEvaluation>());
        EList<ImmunizationVaccinationProtocol> vaccinationProtocols = imm.getVaccinationProtocol();
        Iterator<ImmunizationVaccinationProtocol> it = vaccinationProtocols.iterator();
        while (it.hasNext()) {
            ImmunizationVaccinationProtocol ivp = it.next();
            ActualEvaluation ae = new ActualEvaluation();
            //TODO: Error check
            String status = ivp.getDoseStatus().getCoding().get(0).getCode().getValue();
            if ("Y".equalsIgnoreCase(status)) {
                ae.setStatus(EvaluationStatus.VALID);
            } else {
                ae.setStatus(EvaluationStatus.INVALID);
            }
            VaccineRef vr = new VaccineRef();
            //TODO: Error checking
            vr.setCvx(ivp.getSeries().getValue());
            ae.setVaccine(vr);
            rve.getEvaluations().add(ae);
        }
        return rve;
    }

    public static ActualForecast translateImmunizationRecommendationToActualForecast(ImmunizationRecommendation ir) {
        ActualForecast forecast = new ActualForecast();
        // TODO: Error checking
        ImmunizationRecommendationRecommendation irr = ir.getRecommendation().get(0);
        if (irr.getDoseNumber() != null && irr.getDoseNumber().getValue() != null) {
            forecast.setDoseNumber(irr.getDoseNumber().getValue().toString());
        }
        //System.out.println("DOSENUMBER = " + irr.getDoseNumber().getValue().toString());
        VaccineRef vaccineRef = new VaccineRef();
        // TODO: Error checking
        vaccineRef.setCvx(irr.getVaccineCode().getCoding().get(0).getCode().getValue());
        forecast.setVaccine(vaccineRef);
        EList<ImmunizationRecommendationDateCriterion> dateCriterions = irr.getDateCriterion();
        // TODO: Error checking
        Iterator<ImmunizationRecommendationDateCriterion> it = dateCriterions.iterator();
        while (it.hasNext()) {
            ImmunizationRecommendationDateCriterion dateCriterion = it.next();
            if (dateCriterion.getValue() != null && dateCriterion.getValue().getValue() != null) {
                FixedDate date = TranslationUtils.translateTchDateToFhirDate(dateCriterion.getValue().getValue().toString());

                // TODO: Error checking
                String status = dateCriterion.getCode().getCoding().get(0).getCode().getValue();
                switch (status) {
                    case IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_DUE:
                        forecast.setRecommended(date.getDate());
                        break;
                    case IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_EARLIEST:
                        forecast.setEarliest(date.getDate());
                        break;
                    case IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_OVERDUE:
                        forecast.setPastDue(date.getDate());
                        break;
                    case IMMUNIZATION_RECOMMENDATION_DATE_CRITERION_LATEST:
                        forecast.setComplete(date.getDate());
                        break;
                }

            }
        }
        //TODO: Error checking
        //forecast.setSerieStatus(SerieStatus.valueOf(irr.getForecastStatus().getCoding().get(0).getCode().getValue()));

        return forecast;
    }

}