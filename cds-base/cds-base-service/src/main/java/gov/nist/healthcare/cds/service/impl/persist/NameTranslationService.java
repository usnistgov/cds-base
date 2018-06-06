package gov.nist.healthcare.cds.service.impl.persist;

import java.util.Hashtable;

import org.springframework.stereotype.Service;

@Service
public class NameTranslationService implements gov.nist.healthcare.cds.service.NameTranslationService {

	Hashtable<String, String> rep = new Hashtable<String, String>();
	Hashtable<String, String> name = new Hashtable<String, String>();
	
	public NameTranslationService() {
		rep.put("polio","POL");
		rep.put("pneumopcv", "PCV");
		rep.put("rotavirus", "ROTA");
		rep.put("mening", "MCV");
		rep.put("meningb", "MENB");
		rep.put("varicella", "VAR");

		name.put("POL", "POLIO");
		name.put("PCV", "PneumoPCV");
		name.put("ROTA", "ROTAVIRUS");
		name.put("MCV", "MENING");
		name.put("MENB", "MeningB");
		name.put("VAR", "VARICELLA");
	}
	

	
	@Override
	public String nameToRep(String name) {
		if(this.rep.containsKey(name.toLowerCase())) return this.rep.get(name.toLowerCase());
		else return name;
	}

	@Override
	public String repToName(String rep) {
		if(this.name.containsKey(rep.toUpperCase())) return this.name.get(rep.toUpperCase());
		else return rep;
	}

}
