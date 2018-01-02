package gov.nist.healthcare.cds.service.impl.transformation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.Injection;
import gov.nist.healthcare.cds.domain.Patient;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.exception.NoDataInCell;
import gov.nist.healthcare.cds.domain.exception.ProductNotFoundException;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.wrapper.ExportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ExportResult;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.domain.wrapper.ModelError;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.enumeration.DateType;
import gov.nist.healthcare.cds.enumeration.EvaluationReason;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.SerieStatus;
import gov.nist.healthcare.cds.repositories.VaccineGroupRepository;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.DateService;
import gov.nist.healthcare.cds.service.FormatService;
import gov.nist.healthcare.cds.service.MetaDataService;
import gov.nist.healthcare.cds.service.VaccineService;

@Service
public class CSSFormatServiceImpl implements FormatService {

	@Autowired
	private VaccineRepository vaccineRepository;
	@Autowired
	private VaccineGroupRepository vaccineGrRepository;
	@Autowired
	private VaccineMappingRepository vaccineMpRepository;
	@Autowired
	private MetaDataService mdService;
	@Autowired
	private VaccineService vaxService;
	@Autowired
	private DateService dateService;
	
	private Hashtable<String,String> transform;
	
//	private XSSFDataFormat df;
	private CellStyle csDate;
	
	final int UID = 0;
	final int NAME = 1;
	final int DOB = 2;
	final int GENDER = 3;
	final int SERIESTATUS = 7;
	final int START_EVENTS = 8;
	final int ADMIN_DATE = 0;
	final int VA_NAME = 1;
	final int CVX = 2;
	final int MVX = 3;
	final int EVAL = 4;
	final int EVAL_REASON = 5;
	final int END_EVENTS = 49;
	final int DOSE_N = 50;
	final int EARLIEST = 51;
	final int RECOMMENDED = 52;
	final int PAST_DUE = 53;
	final int TARGET = 54;
	final int EVAL_DATE = 55;
	final int EVAL_TYPE = 56;
	final int DATE_ADD = 57;
	final int DATE_UP = 58;
	final int FORECAST_TYPE = 59;
	final int CHANGE = 60;
	final int VERSION = 61;
	final int DESCRIPTION = 62;
	
	public String getString(Cell cell,int i) throws NoDataInCell{
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING && !cell.getStringCellValue().isEmpty()){
			return cell.getStringCellValue();
		}
		else {
			throw new NoDataInCell(i);
		}
	}
	
	public String getStringOpt(Cell cell,String def) {
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING && !cell.getStringCellValue().isEmpty()){
			return cell.getStringCellValue();
		}
		else {
			return def;
		}
	}
	
	public String getDoubleAsStringOpt(Cell cell,String def) {
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			return cell.getNumericCellValue()+"";
		}
		else {
			return def;
		}
	}
	
	public java.util.Date getDate(Cell cell,int i) throws NoDataInCell{
		try {
			if(cell != null){
				return cell.getDateCellValue();
			}
			else {
				throw new NoDataInCell(i);
			}
		}
		catch(Exception e){
			throw new NoDataInCell(i);
		}
	}
	
	public boolean emptyLine(Row r){
		return r.getCell(0).getCellType() == Cell.CELL_TYPE_BLANK;
	}
	
	public void fillTestCaseInfo(Row r, TestCase tc) throws NoDataInCell{
		tc.setDateType(DateType.FIXED);
		tc.setName(this.getString(r.getCell(NAME),NAME));
		tc.setUid(this.getString(r.getCell(UID),UID));
		tc.setDescription(this.getStringOpt(r.getCell(DESCRIPTION),""));
		tc.setEvalDate(new FixedDate(this.getDate(r.getCell(EVAL_DATE),EVAL_DATE)));
		tc.setGroupTag(this.getString(r.getCell(TARGET),TARGET));
		
		Patient p = new Patient();
		p.setDob(new FixedDate(this.getDate(r.getCell(DOB),DOB)));
		p.setGender(Gender.valueOf(this.getString(r.getCell(GENDER),GENDER)));
		
		MetaData md = mdService.create(true);
		md.setChangeLog(this.getStringOpt(r.getCell(CHANGE),""));
		md.setVersion(this.getDoubleAsStringOpt(r.getCell(VERSION),"1.0"));
		md.setDateCreated(this.getDate(r.getCell(DATE_ADD),DATE_ADD));
		md.setDateLastUpdated(this.getDate(r.getCell(DATE_UP),DATE_UP));
		
		tc.setEvaluationType(this.getStringOpt(r.getCell(EVAL_TYPE),""));
		tc.setForecastType(this.getStringOpt(r.getCell(FORECAST_TYPE),""));
		
		tc.setPatient(p);
		tc.setMetaData(md);
	}
	
	public void exportTestCaseInfo(Row r, TestCase tc, String postfix) {
		
		Cell _UID = r.createCell(UID);
		_UID.setCellValue(tc.getUid()+postfix);
		
		Cell _NAME = r.createCell(NAME);
		_NAME.setCellValue(tc.getName());
		
		Cell _DESCRIPTION = r.createCell(DESCRIPTION);
		_DESCRIPTION.setCellValue(tc.getDescription());
		
		Cell _EVAL_DATE = r.createCell(EVAL_DATE);
		_EVAL_DATE.setCellValue(asFixed(tc.getEvalDate()));
		_EVAL_DATE.setCellStyle(csDate);
		
		Cell _DOB = r.createCell(DOB);
		_DOB.setCellValue(asFixed(tc.getPatient().getDob()));
		_DOB.setCellStyle(csDate);
		
		Cell _GENDER = r.createCell(GENDER);
		_GENDER.setCellValue(tc.getPatient().getGender().toString());
		
		Cell _CHANGE = r.createCell(CHANGE);
		_CHANGE.setCellValue(tc.getMetaData().getChangeLog());
		
		Cell _VERSION = r.createCell(VERSION);
		_VERSION.setCellValue(tc.getMetaData().getVersion());
		
		Cell _DATE_ADD = r.createCell(DATE_ADD);
		_DATE_ADD.setCellValue(tc.getMetaData().getDateCreated());
		_DATE_ADD.setCellStyle(csDate);
		
		Cell _DATE_UP = r.createCell(DATE_UP);
		_DATE_UP.setCellValue(tc.getMetaData().getDateLastUpdated());
		_DATE_UP.setCellStyle(csDate);
		
		Cell _EVAL_TYPE = r.createCell(EVAL_TYPE);
		_EVAL_TYPE.setCellValue(tc.getEvaluationType());
		
		Cell _FORECAST_TYPE = r.createCell(FORECAST_TYPE);
		_FORECAST_TYPE.setCellValue(tc.getForecastType());
		
	}

	public void exportForecast(Row r, ExpectedForecast ef) {
		transform = new Hashtable<String, String>();
		transform.put("polio","POL");
		transform.put("pneumopcv", "PCV");
		transform.put("rotavirus", "ROTA");
		transform.put("meningb", "MCV");
		transform.put("varicella", "VAR");
		
		Cell _SERIESTATUS = r.createCell(SERIESTATUS);
		_SERIESTATUS.setCellValue(ef.getSerieStatus().getDetails());
		
		Cell _DOSE_N = r.createCell(DOSE_N);
		_DOSE_N.setCellValue(ef.getDoseNumber());
		
		if(ef.getEarliest() != null){
			Cell _EARLIEST = r.createCell(EARLIEST);
			_EARLIEST.setCellValue(asFixed(ef.getEarliest()));
			_EARLIEST.setCellStyle(csDate);
		}
		
		if(ef.getRecommended() != null){
			Cell _RECOMMENDED = r.createCell(RECOMMENDED);
			_RECOMMENDED.setCellValue(asFixed(ef.getRecommended()));
			_RECOMMENDED.setCellStyle(csDate);
		}
		
		if(ef.getPastDue() != null){
			Cell _PAST_DUE = r.createCell(PAST_DUE);
			_PAST_DUE.setCellValue(asFixed(ef.getPastDue()));
			_PAST_DUE.setCellStyle(csDate);
		}
		
		Cell _TARGET = r.createCell(TARGET);
		String target = getTarget(ef.getTarget().getCvx());
		_TARGET.setCellValue(target);
		
	}
	
	public String getTarget(String cvx){
		Hashtable<String, String> transform = new Hashtable<String, String>();
		transform.put("polio","POL");
		transform.put("pneumopcv", "PCV");
		transform.put("rotavirus", "ROTA");
		transform.put("meningb", "MCV");
		transform.put("varicella", "VAR");
		
		String target = "";
		VaccineMapping mp = this.vaccineMpRepository.findMapping(cvx);
		if(mp.getGroups().size() > 1 || mp.getGroups().size() == 0){
			target = mp.getVx().getName();
		}
		else {
			target = mp.getGroups().toArray(new VaccineGroup[0])[0].getName();
		}
		
		if(transform.containsKey(target.toLowerCase())){
			target = transform.get(target.toLowerCase());
		}
		
		return target;
	}
	
	public Vaccine fillForecast(Row r, TestCase tc) throws NoDataInCell, VaccineNotFoundException{
		ExpectedForecast fc = new ExpectedForecast();
		fc.setSerieStatus(SerieStatus.getSerieStatus(this.getString(r.getCell(SERIESTATUS),SERIESTATUS)));
		if(r.getCell(50).getCellType() == Cell.CELL_TYPE_STRING){
			fc.setDoseNumber(r.getCell(DOSE_N).getStringCellValue());
		}
		else {
			fc.setDoseNumber((int) r.getCell(DOSE_N).getNumericCellValue() + "");
		}
		
		try {
			if(this.getDate(r.getCell(EARLIEST),EARLIEST) != null){
				fc.setEarliest(new FixedDate(this.getDate(r.getCell(EARLIEST),EARLIEST)));
			}
		}
		catch(Exception x){
			if(fc.getSerieStatus().hasDates()){
				throw x;
			}
		}
		
		try {
			if(this.getDate(r.getCell(RECOMMENDED),RECOMMENDED) != null){
				fc.setRecommended(new FixedDate(this.getDate(r.getCell(RECOMMENDED),RECOMMENDED)));
			}	
		}
		catch(Exception x){
			if(fc.getSerieStatus().hasDates()){
				throw x;
			}
		}
		
		try {
			if(this.getDate(r.getCell(PAST_DUE),PAST_DUE) != null){
				fc.setPastDue(new FixedDate(this.getDate(r.getCell(PAST_DUE),PAST_DUE)));
			}
		}
		catch(Exception x){
			
		}
		
		String target = this.getString(r.getCell(TARGET),TARGET);
		Vaccine v = vaxService.findGroup(target);
		
		fc.setTarget(v);
		tc.setForecast(Arrays.asList(fc));
		return v;
		
//		Vaccine v = vaccineRepository.findByNameIgnoreCase(target);
//		if(v == null){
//			String tr = transform.get(target.toUpperCase());
//			VaccineGroup vg;
//			if(tr != null && !tr.isEmpty()){
//				vg = vaccineGrRepository.findByNameIgnoreCase(tr);
//			}
//			else {
//				vg = vaccineGrRepository.findByNameIgnoreCase(target);
//			}
//			
//			if(vg != null){
//				v = vaccineRepository.findOne(vg.getCvx());
//			}
//			else {
//				throw new VaccineNotFoundException(target);
//			}
//		}
		
		
	}
	
	private VaccineRef getRef(Injection i){
		if(i instanceof Vaccine){
			return new VaccineRef(((Vaccine) i).getCvx(),"");
		}
		else if(i instanceof Product){
			return new VaccineRef(((Product) i).getVx().getCvx(), ((Product) i).getMx().getMvx());
		}
		else
			return new VaccineRef();
	}
	
	public void exportEvent(Row r, VaccinationEvent ve, int start) {
		Hashtable<EvaluationReason, String> transform = new Hashtable<EvaluationReason, String>();
		transform.put(EvaluationReason.H, "Age: Too Old");
		transform.put(EvaluationReason.C, "Age: Too Young");
		transform.put(EvaluationReason.G, "Inadvertent Vaccine");
		transform.put(EvaluationReason.D, "Interval: too short");
		transform.put(EvaluationReason.E, "Live Virus Conflict");
		transform.put(EvaluationReason.I, "Series Already Complete");
		
		Cell _ADMIN_DATE = r.createCell(start+ADMIN_DATE);
		_ADMIN_DATE.setCellValue(asFixed(ve.getDate()));
		_ADMIN_DATE.setCellStyle(csDate);
		
		Cell _VA_NAME = r.createCell(start+VA_NAME);
		_VA_NAME.setCellValue(ve.getAdministred().getName());
		
		VaccineRef ref = getRef(ve.getAdministred());
		
		Cell _CVX = r.createCell(start+CVX);
		_CVX.setCellValue(ref.getCvx());
		
		Cell _MVX = r.createCell(start+MVX);
		_MVX.setCellValue(ref.getMvx());
		
		
		ExpectedEvaluation ev = null;
		for(ExpectedEvaluation x : ve.getEvaluations()){
			ev = x;
			break;
		}

		if(ev != null && ev.getStatus() != null){
			Cell _EVAL = r.createCell(start+EVAL);
			_EVAL.setCellValue(ev.getStatus().getDetails());
			
			if(ev.getReason() != null && transform.containsKey(ev.getReason())){
				if(transform.containsKey(ev.getReason())) {
					Cell _EVAL_REASON = r.createCell(start+EVAL_REASON);
					_EVAL_REASON.setCellValue(transform.get(ev.getReason()));
				}
				else {
					Cell _EVAL_REASON = r.createCell(start+EVAL_REASON);
					_EVAL_REASON.setCellValue(ev.getReason().getDetails());
				}
			}
		}
		
	}
	
	public void fillEvent(Row r, TestCase tc, int start, int id, boolean ignore, Vaccine target) throws VaccineNotFoundException, NoDataInCell, ProductNotFoundException{
		Hashtable<String,EvaluationReason> transform = new Hashtable<String,EvaluationReason>();
		transform.put("age: too old", EvaluationReason.H);
		transform.put("age: too young", EvaluationReason.C);
		transform.put("inadvertent vaccine", EvaluationReason.G);
		transform.put("inadvertent vaccine: inadvertent administration", EvaluationReason.G);
		transform.put("vaccine: invalid usage", EvaluationReason.G);
		transform.put("interval: too short", EvaluationReason.D);
		transform.put("live virus conflict", EvaluationReason.E);
		transform.put("series already complete", EvaluationReason.I);
		
		VaccinationEvent ve = new VaccinationEvent();
		ve.setPosition(id);
		ve.setDoseNumber(0);
		ve.setDate(new FixedDate(this.getDate(r.getCell(start+ADMIN_DATE),start+ADMIN_DATE)));
		String cvx = r.getCell(start+CVX).getCellType() == Cell.CELL_TYPE_STRING ? this.getString(r.getCell(start+CVX),start+CVX) : ""+ (int) r.getCell(start+CVX).getNumericCellValue();
		String mvx ="";
		try {
			mvx =  this.getString(r.getCell(start+MVX),start+MVX);
		}
		catch(Exception e){
			mvx = "";
		}
		
		String eval = this.getString(r.getCell(start+EVAL),start+EVAL);
		ExpectedEvaluation evalt = new ExpectedEvaluation();
		
		if(eval.toUpperCase().equals("VALID")){
			evalt.setStatus(EvaluationStatus.VALID);
		}
		else if(eval.toUpperCase().equals("NOT VALID")){
			evalt.setStatus(EvaluationStatus.INVALID);
		}
		else if(eval.toUpperCase().equals("EXTRANEOUS")){
			evalt.setStatus(EvaluationStatus.EXTRANEOUS);
		}
		else if(eval.toUpperCase().equals("SUB-STANDARD")){
			evalt.setStatus(EvaluationStatus.SUBSTANDARD);
		}
		
		try {
			
			String reason = this.getString(r.getCell(start+EVAL_REASON),start+EVAL_REASON);

			if(transform.containsKey(reason.toLowerCase())){
				evalt.setReason(transform.get(reason.toLowerCase()));
			}
			else {
				for(EvaluationReason rs : EvaluationReason.values()){
					if(rs.getDetails().toLowerCase().equals(reason.toLowerCase())){
						evalt.setReason(rs);
						break;
					}
				}
			}
		}
		catch(Exception e){
			//e.printStackTrace();
		}

		VaccineRef ref = new VaccineRef(cvx,mvx);
		Injection injection = vaxService.getVax(ref,ignore);
		ve.setAdministred(injection);
		
		if(vaxService.isGroupOf(injection.getCvx(), target.getCvx())){
			evalt.setRelatedTo(target);
		}
		else {
			evalt.setRelatedTo(vaxService.asVaccine(injection));
		}
		
//		if(mvx != null && !mvx.isEmpty()){
//			VaccineMapping vm = vaccineMpRepository.findMapping(cvx);
//			if(vm == null){
//				throw new ProductNotFoundException(cvx+"", mvx);
//			}
//			else {
//				Product pr = null;
//				for(Product cp : vm.getProducts()){
//					if(cp.getMx().getMvx().equals(mvx)){
//						pr = cp;
//						break;
//					}
//				}
//				if(pr == null){
//					if(ignore){
//						throw new ProductNotFoundException(cvx+"", mvx);
//					}
//					else {
//						ve.setAdministred(vm.getVx());
//						evalt.setRelatedTo(vm.getVx());
//					}
//				}
//				else {
//					ve.setAdministred(pr);
//					evalt.setRelatedTo(pr.getVx());
//				}
//			}
//		}
//		else {
//			Vaccine vx = vaccineRepository.findOne(cvx+"");
//			if(vx == null){
//				throw new VaccineNotFoundException(cvx+"");
//			}
//			ve.setAdministred(vx);
//			evalt.setRelatedTo(vx);
//		}
		
		ve.setEvaluations(new HashSet<ExpectedEvaluation>(Arrays.asList(evalt)));
		tc.getEvents().add(ve);
	}

	public List<Integer> importTC(ImportConfig config){
		List<Integer> include = new ArrayList<Integer>();
		if(config.isAll()){
			return include;
		}
		else {
			String[] parts = config.getLines().split(";");
			for(String p : parts){
				if(p.contains("-")){
					String[] range = p.split("-");
					int s = Integer.parseInt(range[0]) - 1;
					int e = Integer.parseInt(range[1]) - 1;
					if(s < e){
						for(int x = s; x <= e; x++){
							include.add(x);
						}
					}
				}
				else {
					int l = Integer.parseInt(p)-1;
					include.add(l);
				}
			}
			return include;
		}
	}
	
	@Override
	public TransformResult importFromFile(InputStream in, ImportConfig config) throws ConfigurationException {
		
		TransformResult ret = new TransformResult();
		transform = new Hashtable<String, String>();
		transform.put("POL", "POLIO");
		transform.put("PCV", "PneumoPCV");
		transform.put("ROTA", "ROTAVIRUS");
		transform.put("MCV", "MeningB");
		transform.put("VAR", "VARICELLA");
		int total = 0;
		List<Integer> includes;
		
		try {
			includes = importTC(config);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new ConfigurationException();
		}
		System.out.println("[HT-INCLUDE]");
		System.out.println(includes);
		
		try {
			
			//-- OPEN EXCEL SPREADSHEET - SHEET 0
			Workbook workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheetAt(config.getPosition()-1);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			
			// INIT COUNTERS - GO TO START LINE
			int i = 1;
//			if(!config.isAll()){
//				for(int j = 1; j < config.getFrom(); j++){
//					if(rowIterator.hasNext())
//						rowIterator.next();
//				}
//				i = config.getFrom();
//			}
			
			// LOOP ON ROWS
			while(rowIterator.hasNext()){
				Row r = rowIterator.next();
				if(this.emptyLine(r)){
					System.out.println("EMPTY "+this.emptyLine(r));
					System.out.println("[CDC SPR] FINISHED AT LINE "+i);
					break;
				}
				else if (config.isAll() || includes.contains(i-1)) {
					TestCase tc = new TestCase();
					total++;
					try {
						this.fillTestCaseInfo(r, tc);
						Vaccine targetV = this.fillForecast(r, tc);
						tc.setEvents(new ArrayList<Event>());
						int position = 0;
						for(int event = START_EVENTS; event <= END_EVENTS; event = event + 6){
							if(r.getCell(event) == null || r.getCell(event).getCellType() == Cell.CELL_TYPE_BLANK){
								break;
							}
							this.fillEvent(r, tc, event, position++, config.isIgnore(), targetV);
						}
						
						try {
							tc.setGroupTag(getTarget(targetV.getCvx()));
						}
						catch(Exception e){
							e.printStackTrace();
						}
						ret.add(tc);
					}
					catch(NoDataInCell e){
						e.printStackTrace();
						ret.add(new ErrorModel(i,e.getCell(),"TestCase Data","Mandatory data is missing from line"));
					}
					catch(VaccineNotFoundException v){
						System.out.println("Vaccine Not Found : "+v.getCvx());
						v.printStackTrace();
						ret.add(new ErrorModel(i,0,"Vaccine","Vaccine Not Found : "+v.getCvx()));
					}
					catch(ProductNotFoundException p){
						System.out.println("Product Not Found ("+p.getCvx()+" ,"+p.getMvx()+")");
						p.printStackTrace();
						ret.add(new ErrorModel(i,0,"Product","Product Not Found ("+p.getCvx()+" ,"+p.getMvx()+")"));
					}
				}
				i++;
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			return ret;
		} 
		catch (EncryptedDocumentException e) {
			e.printStackTrace();
			return ret;
		} 
		catch (InvalidFormatException e) {
			e.printStackTrace();
			return ret;
		}
		catch (Exception e) {
			e.printStackTrace();
			return ret;
		}
		ret.setTotalTC(total);
		return ret;
	}
	
	
	public TestCaseGroup getGroup(List<TestCaseGroup> groups, String name){
		for(TestCaseGroup gr : groups){
			if(gr.getName().equals(name)){
				return gr;
			}
		}
		TestCaseGroup tcg = new TestCaseGroup();
		tcg.setId(UUID.randomUUID().toString());
		tcg.setName(name);
		groups.add(tcg);
		return tcg;
	}

	@Override
	public List<ErrorModel> preImport(InputStream file) {
		return new ArrayList<ErrorModel>();
	}
	
	@Override
	public List<ErrorModel> preExport(TestCase tc) {
		ArrayList<ErrorModel> list = new ArrayList<ErrorModel>();
		if(!tc.isRunnable()){
			for(ModelError r : tc.getErrors()){
				list.add(new ErrorModel(r.getPath(),r.getMessage()));
			}
		}
		
		return list;
	}

	@Override
	public String formatName() {
		return "cdc";
	}
	
	public void createHeader(Row r){
		r.createCell(UID).setCellValue("CDC_Test_ID");
		r.createCell(NAME).setCellValue("Test_Case_Name");
		r.createCell(DOB).setCellValue("DOB");
		r.createCell(GENDER).setCellValue("gender");
		r.createCell(GENDER+1).setCellValue("Med_History_Text");
		r.createCell(GENDER+2).setCellValue("Med_History_Code");
		r.createCell(GENDER+3).setCellValue("Med_History_Code_Sys");
		r.createCell(SERIESTATUS).setCellValue("Series_Status");
		int i = 1;
		for(int event = START_EVENTS; event <= END_EVENTS; event = event + 6){
			r.createCell(event).setCellValue("Date_Administred_"+i);
			r.createCell(event+1).setCellValue("Vaccine_Name_"+i);
			r.createCell(event+2).setCellValue("CVX_"+i);
			r.createCell(event+3).setCellValue("MVX_"+i);
			r.createCell(event+4).setCellValue("Evaluation_Status_"+i);
			r.createCell(event+5).setCellValue("Evaluation_Reason_"+i);
			i++;
		}
		r.createCell(DOSE_N).setCellValue("Forecast_#");
		r.createCell(EARLIEST).setCellValue("Earliest_Date");
		r.createCell(RECOMMENDED).setCellValue("Recommended_Date");
		r.createCell(PAST_DUE).setCellValue("Past_Due_Date");
		r.createCell(TARGET).setCellValue("Vaccine_Group");
		r.createCell(EVAL_DATE).setCellValue("Assessment_Date");
		r.createCell(EVAL_TYPE).setCellValue("Evaluation_Test_Type");
		r.createCell(DATE_ADD).setCellValue("Date_Added");
		r.createCell(DATE_UP).setCellValue("Date_Updated");
		r.createCell(FORECAST_TYPE).setCellValue("Forecast_Test_Type");
		r.createCell(CHANGE).setCellValue("Reason_For_Change");
		r.createCell(VERSION).setCellValue("Changed_In_Version");
		r.createCell(DESCRIPTION).setCellValue("General_Description");

	}

	@Override
	public ExportResult exportToFile(List<TestCase> tcs, ExportConfig config) throws ConfigurationException {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("FITS Exported TestCases");
		
		XSSFDataFormat df = workbook.createDataFormat();
		csDate = workbook.createCellStyle();
		csDate.setDataFormat(df.getFormat("mm/dd/yyyy"));
		
		
		this.createHeader(sheet.createRow(0));
		int i = 1;
		java.util.Date today = new java.util.Date();
		for(TestCase tc : tcs){
			
			if(tc.getDateType().equals(DateType.RELATIVE)){
				this.dateService.toFixed(tc, today);
			}
			boolean postfix = tc.getForecast().size() > 0;
			int nfcast = 0;
			for(ExpectedForecast fcast : tc.getForecast()){
				Row r = sheet.createRow(i++);
				nfcast++;
				//-- INFO --
				this.exportTestCaseInfo(r, tc, postfix ? "-"+nfcast+"" : "");
				
				//-- EVENTS --
				int position = START_EVENTS;
				for(Event e : tc.getEvents()){
					if(e instanceof VaccinationEvent && position <= END_EVENTS){
						VaccinationEvent ve = (VaccinationEvent) e;
						this.exportEvent(r, ve, position);
						position += 6;
					}
				}
				
				//-- FORECAST
				this.exportForecast(r , fcast);
			}
//			Row r = sheet.createRow(i++);
//			this.exportTestCaseInfo(r, tc);
//			if(tc.getForecast().size() == 1)
//				this.exportForecast(r , tc.getForecast().get(0));
//			
//			int position = START_EVENTS;
//			for(Event e : tc.getEvents()){
//				if(e instanceof VaccinationEvent && position <= END_EVENTS){
//					VaccinationEvent ve = (VaccinationEvent) e;
//					this.exportEvent(r, ve, position);
//					position += 6;
//				}
//			}
		}
		for(int j = 0; j < 63; j++){
			sheet.autoSizeColumn(j);
		}
		
		ExportResult export = new ExportResult();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			workbook.write(baos);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		export.add("cds-spreadsheet.xlsx", new ByteArrayInputStream(baos.toByteArray()));
		return export;
	}
	
	
	private java.util.Date asFixed(Date d){
		return ((FixedDate) d).getDate();
	}



}
