package gov.nist.healthcare.cds.service.impl.persist;


import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.service.ErrorPathCleaner;

@Service
public class SimpleErrorPathCleaner implements ErrorPathCleaner {

	@Override
	public String errorPath(String x) {
		String[] elements = x.split("\\.");
		String first = elements.length > 0  ? elements[0] : x;
		if(first.startsWith("events") || first.startsWith("forecast")){
			String sub = first.replace("[", ";");
			sub = sub.replace("]", "");
			String[] parts = sub.split(";");
			int id = Integer.parseInt(parts[1])+1;
			String txt = first.startsWith("events") ? "Vaccination Event ID - "+id+" " : "Forecast ID - "+id;
			return txt;
		}
		else if(first.startsWith("patient") || first.startsWith("evalDate")){
			String txt = first.startsWith("patient") ? "Patient Information" : "Assessment Date";
			return txt;
		}
		else if(first.equals("name") || first.equals("dateType")){
			String txt = "Test Case Information";
			return txt;
		}
		else {
			return "Data Error";
		}
		
	}
	 
}
