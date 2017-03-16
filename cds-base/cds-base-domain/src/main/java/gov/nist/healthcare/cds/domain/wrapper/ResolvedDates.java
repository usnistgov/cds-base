package gov.nist.healthcare.cds.domain.wrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResolvedDates {
	private Date dob;
	private Date eval;
	private Map<Integer, Date> events;
	
	
	public ResolvedDates() {
		super();
		events = new HashMap<Integer, Date>();
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public Date getEval() {
		return eval;
	}
	public void setEval(Date eval) {
		this.eval = eval;
	}
	public Map<Integer, Date> getEvents() {
		return events;
	}
	public void setEvents(Map<Integer, Date> events) {
		this.events = events;
	}
	
}
