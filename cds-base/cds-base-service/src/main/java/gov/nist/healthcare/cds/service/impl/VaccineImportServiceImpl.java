package gov.nist.healthcare.cds.service.impl;


import gov.nist.healthcare.cds.domain.Manufacturer;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.repositories.ManufacturerRepository;
import gov.nist.healthcare.cds.repositories.ProductRepository;
import gov.nist.healthcare.cds.repositories.VaccineGroupRepository;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.repositories.VaccineRepository;
import gov.nist.healthcare.cds.service.VaccineImportService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineImportServiceImpl implements VaccineImportService {

	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	@Autowired
	private VaccineRepository vaccineRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private VaccineGroupRepository vxGroupRepository;
	
	
	@Override
	public Set<VaccineMapping> _import(InputStream _vaccines, InputStream _groups, InputStream _manufacturer, InputStream _products) throws IOException {
		
		byte[] vaccines = IOUtils.toByteArray(_vaccines);
		byte[] groups = IOUtils.toByteArray(_groups);
		byte[] products = IOUtils.toByteArray(_products);
		byte[] manufacturer = IOUtils.toByteArray(_manufacturer);
		ByteArrayInputStream ba_vaccines = new ByteArrayInputStream(vaccines);
		ByteArrayInputStream ba_groups = new ByteArrayInputStream(groups);
		ByteArrayInputStream ba_products = new ByteArrayInputStream(products);
		ByteArrayInputStream ba_manufacturer = new ByteArrayInputStream(manufacturer);
		
		ba_manufacturer.reset();
		manufacturer(ba_manufacturer);
		
		Set<VaccineMapping> l_vxm = new HashSet<VaccineMapping>();
//		Set<VaccineGroup> l_vg = new HashSet<VaccineGroup>();
//		Set<Vaccine> l_vx = new HashSet<Vaccine>();
//		Set<Product> l_pr = new HashSet<Product>();
		
		try {
			Workbook workbook = WorkbookFactory.create(ba_vaccines);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext()){
				
				VaccineMapping vxm = new VaccineMapping();
				
				Row r = rowIterator.next();
				String CVX = r.getCell(0).getStringCellValue();
				String NAME = r.getCell(1).getStringCellValue();
				String DTS = r.getCell(2).getStringCellValue();
				
				Vaccine vx = new Vaccine();
				vx.setCvx(CVX);
				vx.setName(NAME);
				vx.setDetails(DTS);
				Vaccine vx_s = vaccineRepository.save(vx);
				
				ba_groups.reset();
				Set<VaccineGroup> s_vg = groups(CVX,ba_groups);
				if(s_vg.size() == 1 && getGroup(0, s_vg).getCvx().equals(CVX)){
					vxm.setGroup(true);
				}
				else
					vxm.setGroup(false);
				List<VaccineGroup> s_vg_s = vxGroupRepository.save(s_vg);

				ba_products.reset();
				Set<Product> s_pr = products(CVX, ba_products);
				for(Product p : s_pr){
					p.setVx(vx_s);
					UUID uuid = UUID.randomUUID();
					System.out.println("[UUID]"+uuid);
					p.setCode(uuid.toString());
				}
				List<Product> s_pr_s = productRepository.save(s_pr);
				
				vxm.setGroups(new HashSet<VaccineGroup>(s_vg_s));
				vxm.setProducts(new HashSet<Product>(s_pr_s));
				vxm.setVx(vx_s);
				vxm.setId(vx_s.getCvx());
				
				l_vxm.add(vxm);
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
		return l_vxm;
	}
	
	private Set<VaccineGroup> groups(String cvx, InputStream groups){
		Set<VaccineGroup> vgs = new HashSet<VaccineGroup>();
		try {
			Workbook workbook = WorkbookFactory.create(groups);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext()){
				Row r = rowIterator.next();
				String CVX = r.getCell(1).getStringCellValue();
				if(CVX.equals(cvx)){
					VaccineGroup vg = new VaccineGroup();
					String vgName = r.getCell(3).getStringCellValue();
					String vgCVX = r.getCell(4).getStringCellValue();
					vg.setCvx(vgCVX);
					vg.setName(vgName);
					vgs.add(vg);
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
		return vgs;
	}
	
	private Set<Product> products(String cvx, InputStream products){
		Set<Product> pds = new HashSet<Product>();
		try {
			Workbook workbook = WorkbookFactory.create(products);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext()){
				Row r = rowIterator.next();
				String CVX = r.getCell(2).getStringCellValue();
				if(CVX.equals(cvx)){
					String MVX = r.getCell(4).getStringCellValue();
					String name = r.getCell(0).getStringCellValue();
					if(!MVX.isEmpty()){
						Manufacturer m = manufacturerRepository.findByMvx(MVX);
						if(m != null){
							Product p = new Product();
							p.setMx(m);
							p.setName(name);
							pds.add(p);
						}
					}
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
		return pds;
	}
	
	private void manufacturer(InputStream man){
		try {
			Workbook workbook = WorkbookFactory.create(man);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while(rowIterator.hasNext()){
				Row r = rowIterator.next();
				String mvx = r.getCell(0).getStringCellValue();
				String name = r.getCell(1).getStringCellValue();
				Manufacturer m = new Manufacturer();
				m.setMvx(mvx);
				m.setName(name);
				manufacturerRepository.save(m);
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
	}
	
	private <T> T getGroup(int i, Set<T> s){
		List<T> l = new ArrayList<T>(s);
		return l.get(i);
	}
}
