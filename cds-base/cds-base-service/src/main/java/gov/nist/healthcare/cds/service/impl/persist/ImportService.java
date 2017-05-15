package gov.nist.healthcare.cds.service.impl.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.exception.UnsupportedFormat;
import gov.nist.healthcare.cds.domain.wrapper.EntityResult;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ImportSummary;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.FormatService;
import gov.nist.healthcare.cds.service.impl.transformation.CSSFormatServiceImpl;
import gov.nist.healthcare.cds.service.impl.transformation.NISTFormatServiceImpl;

@Service
public class ImportService {

	@Autowired
	private TestCaseRepository testCaseRepo;
	@Autowired
	private TestPlanRepository testPlanRepo;
	
	@Autowired
	private NISTFormatServiceImpl nistFormatProvider;
	@Autowired
	private CSSFormatServiceImpl cdcFormatProvider;
	
	public ImportService(){
		
	}

	public FormatService getFormatterFor(String format) throws UnsupportedFormat{
		switch(format){
		case "nist" : return nistFormatProvider;
		case "cdc"  : return cdcFormatProvider;
		}
		throw new UnsupportedFormat(format);
	}
	
	public ImportSummary importTestCases(List<MultipartFile> files, String format, String tpId, ImportConfig config) throws IOException, UnsupportedFormat, ConfigurationException {
		ImportSummary results = new ImportSummary();
		List<TestCase> tcs = new ArrayList<TestCase>();
		FormatService formatter = this.getFormatterFor(format);
		
		for(MultipartFile file : files){
			if(!file.isEmpty()){
				List<ErrorModel> validationErrors = formatter.preImport(file.getInputStream());
				if(validationErrors == null || validationErrors.isEmpty()){
					TransformResult transformed = formatter.importFromFile(file.getInputStream(), config);
					tcs.addAll(transformed.getTestCases());
					this.concatResults(transformed, results, file.getOriginalFilename());
				}
				else {
					results.setAll(results.getAll()+1);
					results.setWerrors(results.getWerrors()+1);
					for(ErrorModel err : validationErrors){
						err.setLocation("XML");
						results.resultFor(file.getOriginalFilename()).getErrors().add(err);
					}
				}
			}
			else {
				results.resultFor(file.getOriginalFilename()).getErrors().add(new ErrorModel(0,0,"XML"," File can't be empty"));
			}
		}
		
		if(tcs.size() > 0){
			TestPlan tp = testPlanRepo.findOne(tpId);
			for(TestCase tc : tcs){
				if(tc.getGroupTag() != null && !tc.getGroupTag().isEmpty()){
					TestCaseGroup tcg = tp.getByNameOrCreateGroup(tc.getGroupTag());
					tc.setGroupTag(tcg.getId());
					tc.setTestPlan(tpId);
					tcg.setTestPlan(tpId);
					tcg.getTestCases().add(tc);
				}
				else {
					tp.addTestCase(tc);
				}
			}
			testCaseRepo.save(tcs);
			testPlanRepo.save(tp);
			results.setTestPlan(tp);
		}
		else {
			results.setTestPlan(null);
		}
		
		return results;
	}
	
	public void concatResults(TransformResult res, ImportSummary sum, String file){
		sum.setImported(sum.getImported() + res.getTestCases().size());
		sum.setAll(sum.getAll() + res.getTotalTC());
		sum.setWerrors(sum.getWerrors() + (res.getTotalTC() - res.getTestCases().size()));
		EntityResult fir = sum.resultFor(file);
		fir.getErrors().addAll(res.getErrors());
	}
	
	

}
