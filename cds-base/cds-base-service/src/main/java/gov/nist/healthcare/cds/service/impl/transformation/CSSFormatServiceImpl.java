package gov.nist.healthcare.cds.service.impl.transformation;

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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
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
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.MetaData;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.enumeration.DateType;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.SerieStatus;
import gov.nist.healthcare.cds.repositories.VaccineGroupRepository;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.FormatService;
import gov.nist.healthcare.cds.service.MetaDataService;

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
	
	private Hashtable<String,String> transform;
	
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
		md.setVersion(this.getStringOpt(r.getCell(VERSION),"1.0"));
		md.setDateCreated(this.getDate(r.getCell(DATE_ADD),DATE_ADD));
		md.setDateLastUpdated(this.getDate(r.getCell(DATE_UP),DATE_UP));
		
		tc.setEvaluationType(this.getStringOpt(r.getCell(EVAL_TYPE),""));
		tc.setForecastType(this.getStringOpt(r.getCell(FORECAST_TYPE),""));
		
		tc.setPatient(p);
		tc.setMetaData(md);
	}
	
	public void exportTestCaseInfo(Row r, TestCase tc) {
		
		Cell _UID = r.createCell(UID);
		_UID.setCellValue(tc.getUid());
		
		Cell _NAME = r.createCell(NAME);
		_NAME.setCellValue(tc.getName());
		
		Cell _DESCRIPTION = r.createCell(DESCRIPTION);
		_DESCRIPTION.setCellValue(tc.getDescription());
		
		Cell _EVAL_DATE = r.createCell(EVAL_DATE);
		_EVAL_DATE.setCellValue(asFixed(tc.getEvalDate()));
		
		Cell _DOB = r.createCell(DOB);
		_DOB.setCellValue(asFixed(tc.getPatient().getDob()));
		
		Cell _GENDER = r.createCell(GENDER);
		_GENDER.setCellValue(tc.getPatient().getGender().toString());
		
		Cell _CHANGE = r.createCell(CHANGE);
		_CHANGE.setCellValue(tc.getMetaData().getChangeLog());
		
		Cell _VERSION = r.createCell(VERSION);
		_VERSION.setCellValue(tc.getMetaData().getVersion());
		
		Cell _DATE_ADD = r.createCell(DATE_ADD);
		_DATE_ADD.setCellValue(tc.getMetaData().getDateCreated());
		
		Cell _DATE_UP = r.createCell(DATE_UP);
		_DATE_UP.setCellValue(tc.getMetaData().getDateLastUpdated());
		
		Cell _EVAL_TYPE = r.createCell(EVAL_TYPE);
		_EVAL_TYPE.setCellValue(tc.getEvaluationType());
		
		Cell _FORECAST_TYPE = r.createCell(FORECAST_TYPE);
		_FORECAST_TYPE.setCellValue(tc.getForecastType());
		
	}
	
	public void exportForecast(Row r, ExpectedForecast ef) {
		
		Cell _SERIESTATUS = r.createCell(SERIESTATUS);
		_SERIESTATUS.setCellValue(ef.getSerieStatus().toString());
		
		Cell _DOSE_N = r.createCell(DOSE_N);
		_DOSE_N.setCellValue(ef.getDoseNumber());
		
		Cell _EARLIEST = r.createCell(EARLIEST);
		_EARLIEST.setCellValue(asFixed(ef.getEarliest()));
		
		Cell _RECOMMENDED = r.createCell(RECOMMENDED);
		_RECOMMENDED.setCellValue(asFixed(ef.getRecommended()));
		
		Cell _PAST_DUE = r.createCell(PAST_DUE);
		_PAST_DUE.setCellValue(asFixed(ef.getPastDue()));
		
		Cell _TARGET = r.createCell(TARGET);
		_TARGET.setCellValue(ef.getTarget().getName());
		
	}
	
	public void fillForecast(Row r, TestCase tc) throws NoDataInCell, VaccineNotFoundException{
		ExpectedForecast fc = new ExpectedForecast();
		fc.setSerieStatus(SerieStatus.getSerieStatus(this.getString(r.getCell(SERIESTATUS),SERIESTATUS)));
		if(r.getCell(50).getCellType() == Cell.CELL_TYPE_STRING){
			fc.setDoseNumber(r.getCell(DOSE_N).getStringCellValue());
		}
		else {
			fc.setDoseNumber((int) r.getCell(DOSE_N).getNumericCellValue() + "");
		}
		
		if(this.getDate(r.getCell(EARLIEST),EARLIEST) != null){
			fc.setEarliest(new FixedDate(this.getDate(r.getCell(EARLIEST),EARLIEST)));
		}
		if(this.getDate(r.getCell(RECOMMENDED),RECOMMENDED) != null){
			fc.setRecommended(new FixedDate(this.getDate(r.getCell(RECOMMENDED),RECOMMENDED)));
		}
		if(this.getDate(r.getCell(PAST_DUE),PAST_DUE) != null){
			fc.setPastDue(new FixedDate(this.getDate(r.getCell(PAST_DUE),PAST_DUE)));
		}
		
		String target = this.getString(r.getCell(TARGET),TARGET);
		Vaccine v = vaccineRepository.findByNameIgnoreCase(target);
		if(v == null){
			String tr = transform.get(target.toUpperCase());
			VaccineGroup vg;
			if(tr != null && !tr.isEmpty()){
				vg = vaccineGrRepository.findByNameIgnoreCase(tr);
			}
			else {
				vg = vaccineGrRepository.findByNameIgnoreCase(target);
			}
			
			if(vg != null){
				v = vaccineRepository.findOne(vg.getCvx());
			}
			else {
				throw new VaccineNotFoundException(target);
			}
		}
		
		fc.setTarget(v);
		tc.setForecast(Arrays.asList(fc));
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
	
	public void exportEvent(Row r, VaccinationEvent ve, ExpectedEvaluation ev, int start, int id, boolean ignore) throws VaccineNotFoundException, NoDataInCell, ProductNotFoundException{
		
		Cell _ADMIN_DATE = r.createCell(start+ADMIN_DATE);
		_ADMIN_DATE.setCellValue(asFixed(ve.getDate()));
		
		VaccineRef ref = getRef(ve.getAdministred());
		
		Cell _CVX = r.createCell(start+CVX);
		_CVX.setCellValue(ref.getCvx());
		
		Cell _MVX = r.createCell(start+MVX);
		_MVX.setCellValue(ref.getMvx());
		
		Cell _EVAL = r.createCell(start+EVAL);
		_EVAL.setCellValue(ev.getStatus().toString());
		
	}
	
	public void fillEvent(Row r, TestCase tc, int start, int id, boolean ignore) throws VaccineNotFoundException, NoDataInCell, ProductNotFoundException{
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

		if(mvx != null && !mvx.isEmpty()){
			VaccineMapping vm = vaccineMpRepository.findMapping(cvx);
			if(vm == null){
				throw new ProductNotFoundException(cvx+"", mvx);
			}
			else {
				Product pr = null;
				for(Product cp : vm.getProducts()){
					if(cp.getMx().getMvx().equals(mvx)){
						pr = cp;
						break;
					}
				}
				if(pr == null){
					if(ignore){
						throw new ProductNotFoundException(cvx+"", mvx);
					}
					else {
						ve.setAdministred(vm.getVx());
						evalt.setRelatedTo(vm.getVx());
					}
				}
				else {
					ve.setAdministred(pr);
					evalt.setRelatedTo(pr.getVx());
				}
			}
		}
		else {
			Vaccine vx = vaccineRepository.findOne(cvx+"");
			if(vx == null){
				throw new VaccineNotFoundException(cvx+"");
			}
			ve.setAdministred(vx);
			evalt.setRelatedTo(vx);
		}
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
	public TransformResult _import(InputStream in, ImportConfig config) throws ConfigurationException {
		
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
						this.fillForecast(r, tc);
						tc.setEvents(new ArrayList<Event>());
						int position = 0;
						for(int event = START_EVENTS; event <= END_EVENTS; event = event + 6){
							if(r.getCell(event) == null || r.getCell(event).getCellType() == Cell.CELL_TYPE_BLANK){
								break;
							}
							this.fillEvent(r, tc, event, position++, config.isIgnore());
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
	public List<ErrorModel> _validate(InputStream file) {
		return new ArrayList<ErrorModel>();
	}

	@Override
	public String formatName() {
		return "cdc";
	}

	@Override
	public InputStream _exportOne(TestCase tc) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("FITS Exported TestCases");
		Row r = sheet.createRow(0);
		
		this.exportTestCaseInfo(r, tc);
		if(tc.getForecast().size() > 0){
			this.exportForecast(r , tc.getForecast().get(0));
		}
		return null;
	}
	
	
	private java.util.Date asFixed(Date d){
		return ((FixedDate) d).getDate();
	}

	@Override
	public InputStream _exportAll(List<TestCase> tc) {
		// TODO Auto-generated method stub
		return null;
	}


}
