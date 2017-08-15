package gov.nist.healthcare.cds.service.impl.transformation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Patient;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.RelativeDate;
import gov.nist.healthcare.cds.domain.RelativeDateRule;
import gov.nist.healthcare.cds.domain.StaticDateReference;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineDateReference;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.exception.ProductNotFoundException;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.ExportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ExportResult;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.domain.xml.beans.*;
import gov.nist.healthcare.cds.enumeration.DatePosition;
import gov.nist.healthcare.cds.enumeration.EvaluationReason;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.RelativeTo;
import gov.nist.healthcare.cds.enumeration.SerieStatus;
import gov.nist.healthcare.cds.repositories.ProductRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.FormatService;
import gov.nist.healthcare.cds.service.MetaDataService;

@Service
public class NISTFormatServiceImpl implements FormatService {

	@Autowired
	private VaccineRepository vaccineRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MetaDataService mdService;
	
	final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";
	
	public InputStream export(TestCase tc, ExportConfig config) {
		try {
			gov.nist.healthcare.cds.domain.xml.beans.TestCase tcp = new gov.nist.healthcare.cds.domain.xml.beans.TestCase();
			tcp.setName(tc.getName());
			tcp.setDescription(tc.getDescription());
			tcp.setAssessmentDate(date(tc.getEvalDate()));
			tcp.setDateType(tc.getDateType().toString());
			if(tc.getGroupTag() != null && !tc.getGroupTag().isEmpty())
				tcp.setGroup(tc.getGroupTag());
			if(tc.getUid() != null && !tc.getUid().isEmpty())
				tcp.setUID(tc.getUid());
			
			MetaDataType mdt = new MetaDataType();
			if(tc.getMetaData() != null){
				MetaData md = tc.getMetaData();
				mdt.setVersion(md.getVersion());
				mdt.setDateCreated(date(md.getDateCreated()));
				mdt.setDateLastUpdated(date(md.getDateLastUpdated()));
			}
			
			PatientType pt = new PatientType();
			if(tc.getPatient() != null){
				Patient p = tc.getPatient();
				if(p.getGender().equals(Gender.F)){
					pt.setGender(GenderType.FEMALE);
				}
				else {
					pt.setGender(GenderType.MALE);
				}
				pt.setDateOfBirth(date(p.getDob()));
			}
			
			List<Event> evts = tc.getEvents();
			if(evts != null && evts.size() > 0){
				EventsType evtst = new EventsType();
				for(Event e : evts){
					EventType evt = new EventType();
					evt.setType(eventType(e));
					evt.setEventDate(date(e.getDate()));
					VaccinationEvent vev = (VaccinationEvent) e;
					evt.setID(vev.getPosition());
					
					VaccineType vt = new VaccineType();
					Injection inject = vev.getAdministred();
					if(inject instanceof Vaccine){
						Vaccine v = (Vaccine) inject;
						vt.setCvx(v.getCvx());
						vt.setName(v.getName());
					}
					else if(inject instanceof Product){
						Product v = (Product) inject;
						vt.setCvx(v.getVx().getCvx());
						vt.setMvx(v.getMx().getMvx());
						vt.setName(v.getName());
					}
					
					evt.setAdministred(vt);
					
					Set<ExpectedEvaluation> evals = vev.getEvaluations();
					EvaluationsType evalst = new EvaluationsType();
					for(ExpectedEvaluation exe : evals){
						EvaluationType et = new EvaluationType();
						et.setStatus(toXMLStatus(exe.getStatus()));
						
						if(exe.getReason() != null){
							EvaluationReasonType evrt = new EvaluationReasonType();
							evrt.setCode(exe.getReason().name());
							evrt.setValue(exe.getReason().getDetails());
							et.setEvaluationReason(evrt);
						}
						
						VaccineType vte = new VaccineType();
						vte.setCvx(exe.getRelatedTo().getCvx());
						vte.setName(exe.getRelatedTo().getName());
						et.setVaccine(vte);
						
						evalst.getEvaluation().add(et);
					}
					evt.setEvaluations(evalst);
					evtst.getEvent().add(evt);
				}
				tcp.setEvents(evtst);
			}
			
			List<ExpectedForecast> efs = tc.getForecast();
			if(efs != null && efs.size() > 0){
				ForecastsType fts = new ForecastsType();				
				for(ExpectedForecast ef : efs){
					ForecastType ft = new ForecastType();
					ft.setEarliest(date(ef.getEarliest()));
					ft.setRecommended(date(ef.getRecommended()));
					ft.setPastDue(date(ef.getPastDue()));
					ft.setLatest(date(ef.getComplete()));
					ft.setForecastReason(ef.getForecastReason());
					
					if(ef.getSerieStatus() != null){
						SerieStatusType sst = new SerieStatusType();
						sst.setCode(ef.getSerieStatus().toString());
						sst.setDetails(ef.getSerieStatus().getDetails());
						
						ft.setSerieStatus(sst);
					}
					
					VaccineType vt = new VaccineType();
					vt.setCvx(ef.getTarget().getCvx());
					vt.setName(ef.getTarget().getName());
					
					ft.setTarget(vt);
					fts.getForecast().add(ft);
				}			
				tcp.setForecasts(fts);
			}

			
			tcp.setPatient(pt);
			tcp.setMetaData(mdt);
			
			
			return this.objToString(tcp);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String eventType(Event e) {
		if(e instanceof VaccinationEvent)
			return "VACCINATION";
		else
			return "";
	}

	public XMLGregorianCalendar date(java.util.Date d) throws DatatypeConfigurationException{
		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(d);
		XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
		        .newXMLGregorianCalendar(
		            gregory);
		return calendar;
	}
	
	public DateType date(Date d) throws DatatypeConfigurationException{
		DateType dt = new DateType();
		
		if(d instanceof FixedDate){
			FixedDate fd = (FixedDate) d;
			GregorianCalendar gregory = new GregorianCalendar();
			gregory.setTime(fd.getDate());
			XMLGregorianCalendar calendar = DatatypeFactory.newInstance()
			        .newXMLGregorianCalendar(
			            gregory);
			FixedDateType fdt = new FixedDateType();
			fdt.setDate(calendar);
			dt.setFixed(fdt);
		}
		else if(d instanceof RelativeDate){
			RelativeDateType rdt = new RelativeDateType();
			for(RelativeDateRule r : ((RelativeDate) d).getRules()){
				RuleType rt = new RuleType();
				rt.setDays(r.getDay());
				rt.setMonths(r.getMonth());
				rt.setYears(r.getYear());
				RelativeToType rtt = new RelativeToType();
				rtt.setPosition(r.getPosition().toString());
				if(r.getRelativeTo() instanceof StaticDateReference){
					rtt.setReference(((StaticDateReference)r.getRelativeTo()).getId().toString());
				}
				else if (r.getRelativeTo() instanceof VaccineDateReference){
					rtt.setId(((VaccineDateReference)r.getRelativeTo()).getId());
					rtt.setReference("VACCINATION");
				}
				rt.setRelativeTo(rtt);
				rdt.getRule().add(rt);
			}
			dt.setRelative(rdt);
		}
		return dt;
	}
	
	public Date date(DateType d) throws DatatypeConfigurationException{
		if(d.getFixed() != null){
			FixedDateType fd = d.getFixed();
			FixedDate f = new FixedDate(fd.getDate().toGregorianCalendar().getTime());
			return f;
		}
		else if(d.getRelative() != null){
			RelativeDateType rdt = d.getRelative();
			RelativeDate rd = new RelativeDate();
			for(RuleType rt : rdt.getRule()){
				RelativeDateRule rdr = new RelativeDateRule();
				rdr.setDay(rt.getDays());
				rdr.setMonth(rt.getMonths());
				rdr.setYear(rt.getYears());
				rdr.setPosition(DatePosition.valueOf(rt.getRelativeTo().getPosition()));
				if(rt.getRelativeTo() != null){
					if(rt.getRelativeTo().getReference().equals("VACCINATION")){
						rdr.setRelativeTo(new VaccineDateReference(rt.getRelativeTo().getId()));
					}
					else{
						rdr.setRelativeTo(new StaticDateReference(RelativeTo.valueOf(rt.getRelativeTo().getReference())));
					}
				}
				rd.add(rdr);
			}
			return rd;
		}
		return null;
	}
	
	private ByteArrayInputStream objToString(gov.nist.healthcare.cds.domain.xml.beans.TestCase r)
			throws JAXBException, UnsupportedEncodingException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String context = "gov.nist.healthcare.cds.domain.xml.beans";
		JAXBContext jc;
		jc = JAXBContext.newInstance(context);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(r, baos);
		return new ByteArrayInputStream(baos.toByteArray());
	}
	
	private gov.nist.healthcare.cds.domain.xml.beans.TestCase stringToObj(InputStream r)
			throws JAXBException {
		JAXBContext jc = JAXBContext
				.newInstance("gov.nist.healthcare.cds.domain.xml.beans");
		Unmarshaller u = jc.createUnmarshaller();
		gov.nist.healthcare.cds.domain.xml.beans.TestCase o = (gov.nist.healthcare.cds.domain.xml.beans.TestCase) u.unmarshal(r);
		return o;
	}

	@Override
	public TransformResult importFromFile(InputStream stream, ImportConfig config) throws ConfigurationException {
		TransformResult transform = new TransformResult();
		try {
			gov.nist.healthcare.cds.domain.xml.beans.TestCase tcp = this.stringToObj(stream);
			if(tcp == null){
				return null;
			}
			TestCase tc = new TestCase();
			tc.setName(tcp.getName());
			tc.setDescription(tcp.getDescription());
			tc.setDateType(gov.nist.healthcare.cds.enumeration.DateType.valueOf(tcp.getDateType()));
			tc.setEvalDate(date(tcp.getAssessmentDate()));
			if(tcp.getGroup() != null && !tcp.getGroup().isEmpty()){
				tc.setGroupTag(tcp.getGroup());
			}
			if(tcp.getUID() != null && !tcp.getUID().isEmpty()){
				tc.setUid(tcp.getUID());
			}
			else {
				tc.setUid("");
			}
			
			MetaData md;
			if(tcp.getMetaData() != null){
				MetaDataType mdt = tcp.getMetaData();
				md = mdService.create(true,mdt.getVersion());
			}
			else {
				md = mdService.create(true);
			}
			
			Patient p = new Patient();
			if(tcp.getPatient() != null){
				PatientType pt = tcp.getPatient();
				if(pt.getGender().equals(GenderType.FEMALE)){
					p.setGender(Gender.F);
				}
				else {
					p.setGender(Gender.M);
				}
				p.setDob(date(pt.getDateOfBirth()));
			}
			
			EventsType evts = tcp.getEvents();
			List<Event> evs = new ArrayList<Event>();
			
			if(evts != null){
				List<EventType> etl = evts.getEvent();
				for(EventType e : etl){
					VaccinationEvent vev = new VaccinationEvent();
					vev.setPosition(e.getID());
					vev.setDate(date(e.getEventDate()));
					
					if(e.getAdministred().getMvx() != null && !e.getAdministred().getMvx().isEmpty()){
						Product pr = productRepository.getProduct(e.getAdministred().getMvx(), e.getAdministred().getCvx());
						if(pr != null){
							vev.setAdministred(pr);
						} else {
							throw new ProductNotFoundException(e.getAdministred().getCvx(),e.getAdministred().getMvx());
						}
					}
					else {
						Vaccine vt = vaccineRepository.findOne(e.getAdministred().getCvx());
						if(vt != null){
							vev.setAdministred(vt);
						} else {
							throw new VaccineNotFoundException(e.getAdministred().getCvx());
						}
					}
					
					Set<ExpectedEvaluation> evals = new HashSet<ExpectedEvaluation>();
					EvaluationsType evalst = e.getEvaluations();
					if(evalst != null){
						List<EvaluationType> evtl = evalst.getEvaluation();
						for(EvaluationType exe : evtl){
							ExpectedEvaluation expe = new ExpectedEvaluation();
							expe.setStatus(fromXMLStatus(exe.getStatus()));
							
							if(exe.getEvaluationReason() != null){
								expe.setReason(EvaluationReason.valueOf(exe.getEvaluationReason().getCode()));
							}
							
							Vaccine vte = vaccineRepository.findOne(exe.getVaccine().getCvx());
							if(vte != null){
								expe.setRelatedTo(vte);
							} else {
								throw new VaccineNotFoundException(exe.getVaccine().getCvx());
							}
							
							evals.add(expe);
						}
					}
					
					vev.setEvaluations(evals);
					evs.add(vev);
				}
			}
			
			List<ExpectedForecast> efs = new ArrayList<ExpectedForecast>();
			ForecastsType fts = tcp.getForecasts();
			if(fts != null){
				for(ForecastType ft : fts.getForecast()){
					ExpectedForecast ef = new ExpectedForecast();
					ef.setEarliest(date(ft.getEarliest()));
					ef.setRecommended(date(ft.getRecommended()));
					ef.setPastDue(date(ft.getPastDue()));
					ef.setComplete(date(ft.getLatest()));
					ef.setForecastReason(ft.getForecastReason());
					if(ft.getSerieStatus() != null){
						ef.setSerieStatus(SerieStatus.valueOf(ft.getSerieStatus().getCode()));
					}

					Vaccine vt = vaccineRepository.findOne(ft.getTarget().getCvx());
					if(vt != null){
						ef.setTarget(vt);
					} else {
						throw new VaccineNotFoundException(ft.getTarget().getCvx());
					}
					efs.add(ef);
				}
			}
			
			tc.setPatient(p);
			tc.setMetaData(md);
			tc.setForecast(efs);
			tc.setEvents(evs);
			
			 transform.add(tc);
		}
		catch(VaccineNotFoundException v){
			transform.add(new ErrorModel(0,0,"Vaccine", "CVX not found in database : "+v.getCvx()));
		}
		catch(ProductNotFoundException p){
			transform.add(new ErrorModel(0,0,"Product", "Product not found in database cvx : "+p.getCvx()+" mvx : "+p.getMvx()));
		} 
		catch (DatatypeConfigurationException e) {
			transform.add(new ErrorModel(0,0,"Date", "Invalid Date Format"));
		} catch (JAXBException e1) {
			transform.add(new ErrorModel(0,0,"File Format", "Invalid File Format"));
		}
		
		transform.setTotalTC(1);
		return transform;
		
	}
	
	public StatusType toXMLStatus(EvaluationStatus es){
		switch(es){
		case VALID : return StatusType.VALID;
		case INVALID : return StatusType.INVALID;
		case SUBSTANDARD : return StatusType.SUBSTANDARD;
		case EXTRANEOUS : return StatusType.EXTRANEOUS;
		default : return null;
		}
	}
	
	public EvaluationStatus fromXMLStatus(StatusType es){
		switch(es){
		case VALID : return EvaluationStatus.VALID;
		case INVALID : return EvaluationStatus.INVALID;
		case SUBSTANDARD : return EvaluationStatus.SUBSTANDARD;
		case EXTRANEOUS : return EvaluationStatus.EXTRANEOUS;
		default : return null;
		}
	}

	@Override
	public List<ErrorModel> preImport(InputStream stream) {
		try {
			InputStream schu = NISTFormatServiceImpl.class.getResourceAsStream("/schema/testCase.xsd");
			SchemaFactory factory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
			Schema schema;
			schema = factory.newSchema(new StreamSource(schu));
			Validator validator = schema.newValidator();
			
			final ArrayList<ErrorModel> errors = new ArrayList<ErrorModel>();
			validator.setErrorHandler(new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception)
						throws SAXException {
					errors.add(new ErrorModel(exception));
				}
	
				@Override
				public void fatalError(SAXParseException exception)
						throws SAXException {
					errors.add(new ErrorModel(exception));
				}
	
				@Override
				public void error(SAXParseException exception)
						throws SAXException {
					errors.add(new ErrorModel(exception));
				}
			});
			validator.validate(new StreamSource(stream));
			return errors;
		} catch (SAXParseException e) {
			return Arrays.asList(new ErrorModel(e));
		} catch (SAXException e) {
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String formatName() {
		return "nist";
	}

	@Override
	public List<ErrorModel> preExport(TestCase tc) {
		List<ErrorModel> errors = new ArrayList<ErrorModel>();
		if(!tc.isRunnable()){
			errors.add(new ErrorModel(-1,-1,tc.getName()," TestCase is not complete"));
		}
		return errors;
	}

	@Override
	public ExportResult exportToFile(List<TestCase> tcs, ExportConfig config) throws ConfigurationException {
		ExportResult exportResult = new ExportResult();
		for(TestCase tc : tcs){
			exportResult.add(tc.getName().replaceAll(" ","-")+".xml",this.export(tc, config));
		}
		return exportResult;
	}

}
