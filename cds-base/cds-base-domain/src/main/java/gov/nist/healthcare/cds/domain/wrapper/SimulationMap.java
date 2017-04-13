package gov.nist.healthcare.cds.domain.wrapper;

import java.util.HashMap;

public class SimulationMap {

	private HashMap<String,String> map;
	
	
	public SimulationMap() {
		super();
		map = new HashMap<String,String>();
		map.put("not_found", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <fhir:Bundle xmlns:fhir=\"http://hl7.org/fhir\" xmlns=\"http://hl7.org/fhir\">"
  + "<id value=\"92a4997c-f94c-4f72-9af8-b0c947dcc121\"/>"
				+ "<type value=\"searchset\"/>"
				+ "</fhir:Bundle>");
	}


	public void put(String id,String xml){
		map.put(id, xml);
	}
	
	public String getResult(String id){
		if(map.containsKey(id)){
			return map.get(id);
		}
		else {
			return map.get("not_found");
		}
	}
}
