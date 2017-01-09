package gov.nist.healthcare.cds.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.CDCImport;
import gov.nist.healthcare.cds.domain.CDCImportConfig;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.MetaData;
import gov.nist.healthcare.cds.domain.Patient;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.exception.ProductNotFoundException;
import gov.nist.healthcare.cds.domain.exception.VaccineNotFoundException;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.repositories.ProductRepository;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.VaccineGroupRepository;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.CDCSpreadSheetFormatService;

@Service
public class CSSFormatServiceImpl implements CDCSpreadSheetFormatService {

	@Autowired
	private TestCaseRepository testCaseRepository;
	@Autowired
	private VaccineRepository vaccineRepository;
	@Autowired
	private VaccineGroupRepository vaccineGrRepository;
	@Autowired
	private VaccineMappingRepository vaccineMpRepository;
	@Autowired
	private ProductRepository productRepository;
	
	private Hashtable<String,String> transform;
	
	@Override
	public CDCImport _import(InputStream in, CDCImportConfig config) {
		CDCImport ret = new CDCImport();
		transform = new Hashtable<String, String>();
		transform.put("POL", "POLIO");
		transform.put("PCV", "PneumoPCV");
		transform.put("ROTA", "ROTAVIRUS");
		transform.put("MCV", "MeningB");
		transform.put("VAR", "VARICELLA");
		
		List<TestCase> tcs = new ArrayList<TestCase>();
		try {
			Workbook workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			int i = 1;
			int col = 0;
			if(!config.isAll()){
				for(int j = 1; j < config.getFrom(); j++){
					rowIterator.next();
				}
				i = config.getFrom();
			}
			while(rowIterator.hasNext()){
				if(!config.isAll() && config.getTo() == i)
					break;
				
				try {
					Row r = rowIterator.next();
					String tcName = r.getCell(1).getStringCellValue();
					Date dob = r.getCell(2).getDateCellValue();
					String gender = r.getCell(3).getStringCellValue();
//					int doseNumber  = (int) r.getCell(50).getNumericCellValue();
					Date earliest    = r.getCell(51).getDateCellValue();
					Date recommended = r.getCell(52).getDateCellValue();
					Date pastDue     = r.getCell(53).getDateCellValue();
					String target = r.getCell(54).getStringCellValue();
					Date evalDate   = r.getCell(55).getDateCellValue();
					Date dateAdded  = r.getCell(57).getDateCellValue();
					Date dateUpdate = r.getCell(58).getDateCellValue();
					TestCase tc = new TestCase();
					tc.setName(tcName);
					tc.setDescription(tcName);
					tc.setEvalDate(new FixedDate(evalDate));
					
					Patient p = new Patient();
					p.setDob(new FixedDate(dob));
					p.setGender(Gender.valueOf(gender));
					
					MetaData md = new MetaData();
					md.setImported(true);
					md.setVersion("1");
					md.setDateCreated(new FixedDate(dateAdded));
					md.setDateLastUpdated(new FixedDate(dateUpdate));
					
					ExpectedForecast fc = new ExpectedForecast();
					fc.setDoseNumber(0);
					fc.setEarliest(new FixedDate(earliest));
					fc.setRecommended(new FixedDate(recommended));
					fc.setPastDue(new FixedDate(pastDue));
					Vaccine v = vaccineRepository.findByNameIgnoreCase(target);
					if(v == null){
						col = 54;
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
					
					tc.setPatient(p);
					tc.setMetaData(md);
					tc.setForecast(new HashSet<ExpectedForecast>(Arrays.asList(fc)));
					tc.setEvents(new HashSet<Event>());
					
					int d = 0;
					for(int j = 8; j < 8 + (6 * 7); j += 6){
						col = j;
						VaccinationEvent ve = new VaccinationEvent();
						
						Date dateA = r.getCell(j).getDateCellValue();
						if(dateA == null)
							break;
						String cvx;
						if(r.getCell(j+2).getCellType() == Cell.CELL_TYPE_STRING){
							cvx = r.getCell(j+2).getStringCellValue();
						}
						else {
							int val = (int) r.getCell(j+2).getNumericCellValue();
							cvx = val+"";
						}
						
						String mvx = r.getCell(j+3).getStringCellValue();
						String eval = r.getCell(j+4).getStringCellValue();
						String reason = r.getCell(j+5).getStringCellValue();
						
						ve.setDate(new FixedDate(dateA));
						ve.setDoseNumber(d);
						
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
						
						evalt.setEvaluationReason(reason);
						
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
									if(config.isIgnore()){
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
					i++;
					tcs.add(tc);
				}
				catch(VaccineNotFoundException v){
					ret.getExceptions().add(new ErrorModel(i,col,"Vaccine Not Found CVX="+v.getCvx()));
				}
				catch(ProductNotFoundException p){
					ret.getExceptions().add(new ErrorModel(i,col,"Product Not Found CVX="+p.getCvx()+" MVX="+p.getMvx()));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ret.setTestcases(tcs);
		
		return ret;
	}

	@Override
	public OutputStream _export(List<TestCase> tcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ErrorModel> _validate(InputStream file) {
		// TODO Auto-generated method stub
		return null;
	}

}
