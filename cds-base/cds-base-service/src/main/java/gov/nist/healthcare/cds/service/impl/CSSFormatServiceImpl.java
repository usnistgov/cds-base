package gov.nist.healthcare.cds.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedEvaluation;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.MetaData;
import gov.nist.healthcare.cds.domain.Patient;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.xml.XMLError;
import gov.nist.healthcare.cds.enumeration.EvaluationStatus;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.CDCSpreadSheetFormatService;

@Service
public class CSSFormatServiceImpl implements CDCSpreadSheetFormatService {

	@Autowired
	private TestCaseRepository testCaseRepository;
	@Autowired
	private VaccineRepository vaccineRepository;
	
	@Override
	public List<TestCase> _import(InputStream in) {
		List<TestCase> tcs = new ArrayList<TestCase>();
		try {
			Workbook workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			int i = 0;
			rowIterator.next();
			rowIterator.next();
			while(rowIterator.hasNext() && i < 3){
				i++;
				Row r = rowIterator.next();
				String tcName = r.getCell(1).getStringCellValue();
				Date dob = r.getCell(2).getDateCellValue();
				String gender = r.getCell(3).getStringCellValue();
				int doseNumber  = (int) r.getCell(50).getNumericCellValue();
				Date earliest    = r.getCell(51).getDateCellValue();
				Date recommended = r.getCell(52).getDateCellValue();
				Date pastDue     = r.getCell(53).getDateCellValue();
//				String vaccineGroup = r.getCell(54).getStringCellValue();
				Date evalDate   = r.getCell(55).getDateCellValue();
				String dateAdded  = r.getCell(56).getStringCellValue();
				Date dateUpdate = r.getCell(57).getDateCellValue();
				System.out.print("[HT]");
				System.out.println(gender);
				System.out.println(tcName);
				TestCase tc = new TestCase();
				tc.setName(tcName);
				tc.setDescription(tcName);
				tc.setEvalDate(new FixedDate(evalDate));
				
				Patient p = new Patient();
				p.setDob(new FixedDate(dob));
				p.setGender(Gender.valueOf(gender));
				
				MetaData md = new MetaData();
				md.setImported(true);
				md.setVersion(1);
				md.setDateCreated(new FixedDate(dateAdded));
				md.setDateLastUpdated(new FixedDate(dateUpdate));
				
				ExpectedForecast fc = new ExpectedForecast();
				fc.setDoseNumber(doseNumber);
				fc.setEarliest(new FixedDate(earliest));
				fc.setRecommended(new FixedDate(recommended));
				fc.setPastDue(new FixedDate(pastDue));
				
				tc.setPatient(p);
				tc.setMetaData(md);
				tc.setForecast(Arrays.asList(fc));
				tc.setEvents(new ArrayList<Event>());
				
				int d = 0;
				for(int j = 8; j < 8 + (6 * 7); j += 6){
					d++;
					Vaccine v = new Vaccine();
					VaccinationEvent ve = new VaccinationEvent();
					
					Date dateA = r.getCell(j).getDateCellValue();
					if(dateA == null)
						break;
					
					String vName = r.getCell(j+1).getStringCellValue();
					int cvx = (int) r.getCell(j+2).getNumericCellValue();
					String eval = r.getCell(j+4).getStringCellValue();
					String reason = r.getCell(j+5).getStringCellValue();
					
					ve.setDate(new FixedDate(dateA));
					ve.setDoseNumber(d);
					
					ExpectedEvaluation evalt = new ExpectedEvaluation();
					if(eval.toUpperCase().equals("VALID")){
						evalt.setStatus(EvaluationStatus.VALID);
					}
					else {
						evalt.setStatus(EvaluationStatus.INVALID);
					}
					
					evalt.setEvaluationReason(reason);
					
					v.setCvx(cvx+"");
					v.setName(vName);
					v.setDetails("#");
					
					Vaccine f = null;
					if((f = vaccineRepository.findByCvx(cvx+"")) != null){
						ve.setAdministred(f);
						evalt.setRelatedTo(f);
						fc.setTarget(f);
					}
					else {
						ve.setAdministred(v);
						evalt.setRelatedTo(v);
						fc.setTarget(v);
					}
					
					tc.getEvents().add(ve);
				}
				
				tcs.add(tc);
	
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
		return tcs;
	}

	@Override
	public OutputStream _export(List<TestCase> tcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<XMLError> _validate(InputStream file) {
		// TODO Auto-generated method stub
		return null;
	}

}
