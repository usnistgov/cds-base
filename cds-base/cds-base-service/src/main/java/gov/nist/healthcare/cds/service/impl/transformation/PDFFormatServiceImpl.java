package gov.nist.healthcare.cds.service.impl.transformation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.ConfigurationException;
import gov.nist.healthcare.cds.domain.wrapper.ExportConfig;
import gov.nist.healthcare.cds.domain.wrapper.ExportResult;
import gov.nist.healthcare.cds.domain.wrapper.ImportConfig;
import gov.nist.healthcare.cds.domain.wrapper.TransformResult;
import gov.nist.healthcare.cds.domain.xml.ErrorModel;
import gov.nist.healthcare.cds.service.FormatService;

@Service
public class PDFFormatServiceImpl implements FormatService {

	@Override
	public String formatName() {
		return "pdf";
	}

	@Override
	public List<ErrorModel> preImport(InputStream stream) {
		return new ArrayList<ErrorModel>();
	}

	@Override
	public TransformResult importFromFile(InputStream stream, ImportConfig config) throws ConfigurationException {
		throw new ConfigurationException();
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
			try {
				exportResult.add(tc.getName().replaceAll(" ","-")+".pdf",this.export(tc, config));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return exportResult;
	}
	
	private InputStream export(TestCase tc, ExportConfig config) throws TransformerException, IOException, com.lowagie.text.DocumentException {
		NISTFormatServiceImpl xml = new NISTFormatServiceImpl();
		InputStream tcXml = xml.export(tc, config);
		//System.out.println(IOUtils.toString(tcXml, StandardCharsets.UTF_8));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		
		transformer = tFactory.newTransformer(new StreamSource(this
				.getClass().getResourceAsStream("/stylesheets/testCase.xsl")));

		transformer.transform(new StreamSource(tcXml), new StreamResult(os));
		String str = os.toString();
		System.out.println(str);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString( str );
	
		renderer.layout();
		ByteArrayOutputStream _os = new ByteArrayOutputStream();
		renderer.createPDF( _os );
		_os.close();
		return new ByteArrayInputStream(_os.toByteArray());
	}

}
