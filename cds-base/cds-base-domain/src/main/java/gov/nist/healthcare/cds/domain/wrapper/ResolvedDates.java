package gov.nist.healthcare.cds.domain.wrapper;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResolvedDates {
	private LocalDate dob;
	private LocalDate eval;
	private Map<Integer, LocalDate> events;
	
	
	public ResolvedDates() {
		super();
		events = new HashMap<Integer, LocalDate>();
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public LocalDate getEval() {
		return eval;
	}
	public void setEval(LocalDate eval) {
		this.eval = eval;
	}
	public Map<Integer, LocalDate> getEvents() {
		return events;
	}
	public void setEvents(Map<Integer, LocalDate> events) {
		this.events = events;
	}
	
}
