package gov.nist.healthcare.cds.service.impl.persist;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.exception.UnsupportedFormat;
import gov.nist.healthcare.cds.domain.wrapper.ExportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ExportResult;
import gov.nist.healthcare.cds.domain.wrapper.ZipExportSummary;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.service.FormatService;
import gov.nist.healthcare.cds.service.impl.transformation.CSSFormatServiceImpl;
import gov.nist.healthcare.cds.service.impl.transformation.NISTFormatServiceImpl;
import gov.nist.healthcare.cds.service.impl.transformation.PDFFormatServiceImpl;

@Service
public class ExportService {
	
	@Autowired
	private NISTFormatServiceImpl nistFormatProvider;
	@Autowired
	private CSSFormatServiceImpl cdcFormatProvider;
	@Autowired
	private PDFFormatServiceImpl pdfFormatProvider;
	
	public ExportService(){
		
	}

	public FormatService getFormatterFor(String format) throws UnsupportedFormat{
		switch(format){
		case "nist" : return nistFormatProvider;
		case "cdc"  : return cdcFormatProvider;
		case "pdf"  : return pdfFormatProvider;
		}
		throw new UnsupportedFormat(format);
	}
	
	public ZipExportSummary exportTestCases(List<TestCase> tcs, String format, ExportConfig config) throws IOException, UnsupportedFormat, ConfigurationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipExportSummary results = new ZipExportSummary(baos);
		FormatService formatter = this.getFormatterFor(format);
		List<String> names = new ArrayList<>();
		List<TestCase> valid = new ArrayList<>();
		for(TestCase tc : tcs){
			List<ErrorModel> validationErrors = formatter.preExport(tc);
			if(validationErrors == null || validationErrors.isEmpty()){
				valid.add(tc);
			}
			else {
				results.invalid(tc.getName(), validationErrors);
			}
		}
		if(valid.size() > 0){
			ExportResult exported = formatter.exportToFile(valid, config);
			results.add(exported,names);
		}
		return results;
	}
}
