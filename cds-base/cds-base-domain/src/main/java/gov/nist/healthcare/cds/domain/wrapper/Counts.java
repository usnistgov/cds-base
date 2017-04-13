package gov.nist.healthcare.cds.domain.wrapper;

public class Counts {

	private ResultCounts events;
	private ResultCounts forecasts;
	
	
	public Counts(ResultCounts events, ResultCounts forecasts) {
		super();
		this.events = events;
		this.forecasts = forecasts;
	}
	
	
	public Counts() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ResultCounts getEvents() {
		return events;
	}
	public void setEvents(ResultCounts events) {
		this.events = events;
	}
	public ResultCounts getForecasts() {
		return forecasts;
	}
	public void setForecasts(ResultCounts forecasts) {
		this.forecasts = forecasts;
	}
	
	
}
