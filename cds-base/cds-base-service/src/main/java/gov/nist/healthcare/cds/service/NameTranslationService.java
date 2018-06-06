package gov.nist.healthcare.cds.service;

public interface NameTranslationService {
	
	String nameToRep(String name);
	String repToName(String rep);

}
