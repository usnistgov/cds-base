package gov.nist.healthcare.cds.service.impl;


import gov.nist.healthcare.cds.domain.TestCase;

import gov.nist.healthcare.cds.domain.Vaccine;

import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.VaccineImportService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

@Service
public class VaccineImportServiceImpl implements VaccineImportService {

	@Autowired
	private VaccineRepository vaccineRepository;
	
	@Override
	public List<TestCase> _import(InputStream in) {
		List<TestCase> tcs = new ArrayList<TestCase>();
		try {
			Workbook workbook = WorkbookFactory.create(in);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext()){
				Row r = rowIterator.next();
				String CVX = r.getCell(0).getStringCellValue();
				String NAME = r.getCell(1).getStringCellValue();
				String DTS = r.getCell(2).getStringCellValue();
				
				Vaccine v = new Vaccine();
				v.setCvx(CVX);
				v.setName(NAME);
				v.setDetails(DTS);
				
				vaccineRepository.saveAndFlush(v);
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
}
