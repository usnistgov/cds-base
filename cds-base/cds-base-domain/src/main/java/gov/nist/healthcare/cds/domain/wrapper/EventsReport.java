package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.domain.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsReport {

	private List<EventPair> events;
	private List<Event> unmatched;
	
	public List<EventPair> getEvents() {
		if(events == null){
			events = new ArrayList<EventPair>();
		}
		return events;
	}
	public void setEvents(List<EventPair> events) {
		this.events = events;
	}
	public List<Event> getUnmatched() {
		if(unmatched == null){
			unmatched = new ArrayList<Event>();
		}
		return unmatched;
	}
	public void setUnmatched(List<Event> unmatched) {
		this.unmatched = unmatched;
	}
	
	
}
