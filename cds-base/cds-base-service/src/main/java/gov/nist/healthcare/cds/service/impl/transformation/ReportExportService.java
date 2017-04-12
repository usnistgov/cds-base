package gov.nist.healthcare.cds.service.impl.transformation;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Patient;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.wrapper.DateCriterion;
import gov.nist.healthcare.cds.domain.wrapper.EvaluationValidation;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;
import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.ResultCounts;
import gov.nist.healthcare.cds.domain.wrapper.StringCriterion;
import gov.nist.healthcare.cds.domain.wrapper.TestCaseInformation;
import gov.nist.healthcare.cds.domain.wrapper.VaccinationEventValidation;
import gov.nist.healthcare.cds.domain.xml.beans.DateValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.DoseValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.EvaluationType;
import gov.nist.healthcare.cds.domain.xml.beans.EvaluationValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.EvaluationsReportType;
import gov.nist.healthcare.cds.domain.xml.beans.EvaluationsValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.EventValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.ForecastValidationType;
import gov.nist.healthcare.cds.domain.xml.beans.ForecastsReportType;
import gov.nist.healthcare.cds.domain.xml.beans.GenderType;
import gov.nist.healthcare.cds.domain.xml.beans.MetaDataType;
import gov.nist.healthcare.cds.domain.xml.beans.PatientTypeReport;
import gov.nist.healthcare.cds.domain.xml.beans.ReportMetaDataType;
import gov.nist.healthcare.cds.domain.xml.beans.ReportSummaryType;
import gov.nist.healthcare.cds.domain.xml.beans.SoftwareConfigType;
import gov.nist.healthcare.cds.domain.xml.beans.StatusType;
import gov.nist.healthcare.cds.domain.xml.beans.SummaryType;
import gov.nist.healthcare.cds.domain.xml.beans.TestCaseInformationType;
import gov.nist.healthcare.cds.domain.xml.beans.TestCaseMetaDataType;
import gov.nist.healthcare.cds.domain.xml.beans.VaccinationEventReportType;
import gov.nist.healthcare.cds.domain.xml.beans.VaccineType;
import gov.nist.healthcare.cds.domain.xml.beans.ValidationReport;
import gov.nist.healthcare.cds.domain.xml.beans.ValidationResultType;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.ValidationStatus;

@Service
public class ReportExportService implements gov.nist.healthcare.cds.service.ReportExportService {

	@Override
	public String exportXML(Report report, TestCase tc)
			throws UnsupportedEncodingException, JAXBException, DatatypeConfigurationException {
		ValidationReport vrt = new ValidationReport();
		vrt.setReportMetaData(reportMd(report));
		vrt.setTestCaseMetaData(tcMd(report, tc));
		vrt.setTestCaseInformation(tcInfo(report));
		vrt.setValidationResult(result(report));
		return objToString(vrt);
	}

	public ReportMetaDataType reportMd(Report r) throws DatatypeConfigurationException {
		ReportMetaDataType rmd = new ReportMetaDataType();
		rmd.setDateExecuted(date(r.getExecutionDate()));
		rmd.setSoftware(sct(r.getSoftwareConfig()));
		return rmd;
	}

	public SoftwareConfigType sct(SoftwareConfig sc) {
		SoftwareConfigType sct = new SoftwareConfigType();
		sct.setConnectionType(sc.getConnector().toString());
		sct.setEndPoint(sc.getEndPoint());
		return sct;
	}

	public TestCaseMetaDataType tcMd(Report r, TestCase tc) throws DatatypeConfigurationException {
		TestCaseMetaDataType tcmd = new TestCaseMetaDataType();
		MetaDataType mdt = new MetaDataType();
		TestCaseInformation tcInfo = r.getTcInfo();
		if(tcInfo != null){
			tcmd.setName(tcInfo.getName());
			tcmd.setUID(tcInfo.getUID());
			if (tcInfo.getMetaData() != null) {
				MetaData md = tcInfo.getMetaData();
				mdt.setVersion(md.getVersion());
				mdt.setDateCreated(date(md.getDateCreated()));
				mdt.setDateLastUpdated(date(md.getDateLastUpdated()));
			}
			tcmd.setMetaData(mdt);
		}
		return tcmd;
	}

	public ValidationResultType result(Report r) throws DatatypeConfigurationException {
		ValidationResultType vrt =  new ValidationResultType();
		vrt.setReportSummary(rst(r));
		vrt.setForecastsReport(frt(r));
		vrt.setEvaluationsReport(evRep(r));
		return vrt;
	}
	
	public EvaluationsReportType evRep(Report r) throws DatatypeConfigurationException{
		EvaluationsReportType ert = new EvaluationsReportType();
		for(VaccinationEventValidation vev : r.getVeValidation()){
			ert.getVaccinationEventValidation().add(evT(vev));
		}
		return ert;
	}
	
	public EventValidationType evT(VaccinationEventValidation vev) throws DatatypeConfigurationException{
		EventValidationType evt = new EventValidationType();
		VaccinationEventReportType vert = new VaccinationEventReportType();
		vert.setAdministred(inject(vev.getVeRequirement().getvEvent().getAdministred()));
		vert.setDate(date(vev.getVeRequirement().getDateAdministred()));
		evt.setEvaluations(evtt(vev));
		evt.setVaccinationEvent(vert);
		return evt;
	}
	
	public EvaluationsValidationType evtt(VaccinationEventValidation vev){
		EvaluationsValidationType evt = new EvaluationsValidationType();
		for(EvaluationValidation ev : vev.geteValidation()){
			evt.getEvaluation().add(evvt(ev));
		}
		return evt;
	}
	
	public EvaluationValidationType evvt(EvaluationValidation ev){
		EvaluationValidationType evt = new EvaluationValidationType();
		EvaluationType evvv = new EvaluationType();
		evvv.setVaccine(inject(ev.getExpEval().getRelatedTo()));
		evvv.setStatus(status(ev.getExpEval().getStatus()));
		evt.setEvaluation(evvv);
		if(ev.getValidation().getValue() != null)
			evt.setActual(status(ev.getValidation().getValue()));
		evt.setAssessment(vs(ev.getValidation().getStatus()));
		return evt;
	}
	
	public StatusType status(EvaluationStatus es){

		if(es.equals(EvaluationStatus.VALID)){
			return StatusType.VALID;
		}
		else if(es.equals(EvaluationStatus.INVALID)) {
			return StatusType.INVALID;
		}
		else if(es.equals(EvaluationStatus.SUBSTANDARD)){
			return StatusType.SUBSTANDARD;
		}
		else if(es.equals(EvaluationStatus.EXTRANEOUS)){
			return StatusType.EXTRANEOUS;
		}
		return null;
	}
	
	public gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus vs(ValidationStatus vs){

		if(vs.equals(ValidationStatus.P)){
			return gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.SUCCESS;
		}
		else if(vs.equals(ValidationStatus.W)){
			return gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.WARNING;
		}
		else if(vs.equals(ValidationStatus.U)){
			return gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.NO_MATCH;
		}
		else if(vs.equals(ValidationStatus.F)){
			return gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.ERROR;
		}
		
		return null;
	}
	
	public VaccineType inject(Injection i){
		VaccineType vt = new VaccineType();
		if(i instanceof Product){
			Product p = (Product) i;
			vt.setCvx(p.getVx().getCvx());
			vt.setMvx(p.getMx().getMvx());
			vt.setName(p.getName());
			return vt;
		}
		else if(i instanceof Vaccine){
			Vaccine v = (Vaccine) i;
			vt.setCvx(v.getCvx());
			vt.setName(v.getName());
			return vt;
		}
		return null;
	}
	
	public ForecastsReportType frt(Report r) throws DatatypeConfigurationException{
		ForecastsReportType frt = new ForecastsReportType();
		for(ForecastValidation fcV : r.getFcValidation()){
			frt.getForecastValidation().add(fv(fcV));
		}
		return frt;
	}
	
	public ForecastValidationType fv(ForecastValidation f) throws DatatypeConfigurationException{
		ForecastValidationType fvt = new ForecastValidationType();
		fvt.setDoseNumber(dovt(f.getDose(),f.getForecastRequirement().getExpForecast().getDoseNumber()));
		fvt.setEarliestDate(dvt(f.getEarliest(),f.getForecastRequirement().getEarliest()));
		fvt.setRecommendedDate(dvt(f.getRecommended(),f.getForecastRequirement().getRecommended()));
		fvt.setPastDueDate(dvt(f.getPastDue(),f.getForecastRequirement().getPastDue()));
		fvt.setLatestDate(dvt(f.getComplete(),f.getForecastRequirement().getComplete()));
		
		VaccineType target = new VaccineType();
		target.setCvx(f.getForecastRequirement().getExpForecast().getTarget().getCvx());
		target.setName(f.getForecastRequirement().getExpForecast().getTarget().getName());
		fvt.setTarget(target);
		return fvt;
	}
	
	public DateValidationType dvt(DateCriterion dc, java.util.Date d) throws DatatypeConfigurationException{
		DateValidationType dvt = new DateValidationType();
		if(dc.getStatus().equals(ValidationStatus.N)){
			return null;
		}
		else if(dc.getStatus().equals(ValidationStatus.P)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.SUCCESS);
		}
		else if(dc.getStatus().equals(ValidationStatus.W)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.WARNING);
		}
		else if(dc.getStatus().equals(ValidationStatus.U)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.NO_MATCH);
		}
		else if(dc.getStatus().equals(ValidationStatus.F)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.ERROR);
		}
		
		if(dc.getValue() != null){
			dvt.setActual(date(dc.getValue()));
		}
		if(d != null){
			dvt.setExpected(date(d));
		}
				
		return dvt;
	}
	
	public DoseValidationType dovt(StringCriterion dc, String s) throws DatatypeConfigurationException{
		DoseValidationType dvt = new DoseValidationType();
		if(dc.getStatus().equals(ValidationStatus.N)){
			return null;
		}
		else if(dc.getStatus().equals(ValidationStatus.P)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.SUCCESS);
		}
		else if(dc.getStatus().equals(ValidationStatus.W)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.WARNING);
		}
		else if(dc.getStatus().equals(ValidationStatus.U)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.NO_MATCH);
		}
		else if(dc.getStatus().equals(ValidationStatus.F)){
			dvt.setAssessment(gov.nist.healthcare.cds.domain.xml.beans.ValidationStatus.ERROR);
		}
		dvt.setActual(dc.getValue());
		dvt.setExpected(s);
		return dvt;
	}
	
	
	public ReportSummaryType rst(Report r){
		ReportSummaryType rst = new ReportSummaryType();
		SummaryType stE = new SummaryType();
		ResultCounts events = r.getEvents();
		stE.setErrors(new BigDecimal(events.getF()));
		stE.setCorrect(new BigDecimal(events.getP()));
		stE.setWarnings(new BigDecimal(events.getW()));
		stE.setIncomplete(new BigDecimal(events.getU()));
		stE.setCompletionPercentage(completion(events.getF(),events.getP(),events.getW(),events.getU()));
		stE.setCorrectnessPercentage(correctness(events.getF(),events.getP(),events.getW(),events.getU()));
		
		SummaryType stF = new SummaryType();
		ResultCounts fcts = r.getForecasts();
		stF.setErrors(new BigDecimal(fcts.getF()));
		stF.setCorrect(new BigDecimal(fcts.getP()));
		stF.setWarnings(new BigDecimal(fcts.getW()));
		stF.setIncomplete(new BigDecimal(fcts.getU()));
		stF.setCompletionPercentage(completion(fcts.getF(),fcts.getP(),fcts.getW(),fcts.getU()));
		stF.setCorrectnessPercentage(correctness(fcts.getF(),fcts.getP(),fcts.getW(),fcts.getU()));
		
		rst.setEvaluations(stE);
		rst.setForecasts(stF);
		
		return rst;
	}
	
	public BigDecimal completion(int f, int p, int w, int u){
		int total = f + p + w + u;
        int found = f + p + w;
        int result = total == 0 ? 100 : found / total * 100;
        return new BigDecimal(result);
	}

	public BigDecimal correctness(int f, int p, int w, int u){
		int total = f + p;
        int correct = p;
        int result = total == 0 ? 100 : correct / total * 100;
        return new BigDecimal(result);
	}
	
	public TestCaseInformationType tcInfo(Report r) throws DatatypeConfigurationException {
		PatientTypeReport pt = new PatientTypeReport();
		if (r.getGender().equals(Gender.F)) {
			pt.setGender(GenderType.FEMALE);
		} else {
			pt.setGender(GenderType.MALE);
		}
		pt.setDateOfBirth(date(r.getDob()));
		TestCaseInformationType tcit = new TestCaseInformationType();
		tcit.setPatient(pt);
		tcit.setAssessmentDate(date(r.getExecutionDate()));
		return tcit;
	}

	private String objToString(gov.nist.healthcare.cds.domain.xml.beans.ValidationReport r)
			throws JAXBException, UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String context = "gov.nist.healthcare.cds.domain.xml.beans";
		JAXBContext jc;
		jc = JAXBContext.newInstance(context);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(r, baos);
		return new String(baos.toByteArray(), "UTF-8");
	}

	public XMLGregorianCalendar date(java.util.Date d) throws DatatypeConfigurationException {
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(d);
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
		return calendar;
	}
}
