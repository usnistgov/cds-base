package gov.nist.healthcare.cds.service.impl.validation.simulation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.RelativeDate;
import gov.nist.healthcare.cds.domain.RelativeDateRule;
import gov.nist.healthcare.cds.domain.StaticDateReference;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.VaccinationEvent;
import gov.nist.healthcare.cds.domain.VaccineDateReference;
import gov.nist.healthcare.cds.domain.wrapper.ResolvedDates;
import gov.nist.healthcare.cds.enumeration.DatePosition;
import gov.nist.healthcare.cds.enumeration.DateType;
import gov.nist.healthcare.cds.enumeration.RelativeTo;
import gov.nist.healthcare.cds.service.DateService;

@Service
public class SimpleDateService implements DateService {

	@Override
	public ResolvedDates resolveDates(TestCase tc, java.util.Date today){
		ResolvedDates rds = new ResolvedDates();
		if(tc.getDateType().equals(DateType.RELATIVE)){
			RelativeDate dob = (RelativeDate) tc.getPatient().getDob();
			rds.setEval(today);
			rds.setDob(this.from(dob.getRules().get(0).getYear(), dob.getRules().get(0).getMonth(), dob.getRules().get(0).getWeek(), dob.getRules().get(0).getDay(), DatePosition.BEFORE, today));
		
			// Fix Events 
			List<VaccinationEvent> veL = new ArrayList<VaccinationEvent>();
			for(Event e : tc.getEvents()){
				if(e instanceof VaccinationEvent){
					veL.add((VaccinationEvent) e);
				}
			}
			this.fixEvents(rds, veL);
			return rds;
		}
		else {
			rds.setEval(asFixed(tc.getEvalDate()));
			rds.setDob(asFixed(tc.getPatient().getDob()));
			for(Event e : tc.getEvents()){
				if(e instanceof VaccinationEvent){
					VaccinationEvent ve = (VaccinationEvent) e;
					rds.getEvents().put(ve.getPosition(), asFixed(ve.getDate()));
				}
			}
			return rds;
		}	
	}
	
	public java.util.Date asFixed(Date d){
		if(d instanceof FixedDate)
			return ((FixedDate) d).getDate();
		return null;
	}
	
	@Override
	public java.util.Date fix(ResolvedDates rds, Date dt) {
		if(dt instanceof RelativeDate){
			RelativeDate d = (RelativeDate) dt;
			List<java.util.Date> rules = this.resolve(d.getRules(), rds, null, false);
			return fit(rules);
		}
		else {
			return asFixed(dt);
		}
	}
	
	public void fixEvents(ResolvedDates rds, List<VaccinationEvent> veL) {
		for(VaccinationEvent ve : veL){
			resolveEvent(rds, ve, veL);
		}
	}
	
	public List<java.util.Date> resolve(List<RelativeDateRule> rdrs, ResolvedDates rdates,List<VaccinationEvent> events, boolean recursive){
		List<java.util.Date> dates = new ArrayList<java.util.Date>();
		java.util.Date d;
		for(RelativeDateRule rule : rdrs){
			if(rule.getRelativeTo() instanceof StaticDateReference){
				StaticDateReference ref = (StaticDateReference) rule.getRelativeTo();
				d = this.resolveStaticReference(ref.getId(), rule, rdates);
			}
			else {
				VaccineDateReference ref = (VaccineDateReference) rule.getRelativeTo();
				d = this.resolveDynamicReference(ref.getId(), rule, rdates);
				if(d == null && recursive){
					this.resolveEvent(rdates, this.getEventAtPosition(events, ref.getId()), events);
					d = this.resolveDynamicReference(ref.getId(), rule, rdates);
				}
			}
			
			if(d != null){
				dates.add(d);
			}
		}
		return dates;
	}
	
	public java.util.Date resolveEvent(ResolvedDates dates, VaccinationEvent e, List<VaccinationEvent> events){
		RelativeDate dateRef = (RelativeDate) e.getDate();
		List<java.util.Date> rules = this.resolve(dateRef.getRules(), dates, events, true);
		java.util.Date result = fit(rules);
		dates.getEvents().put(e.getPosition(), result);
		return result;
	}
	
	public java.util.Date fit(List<java.util.Date> dates){
		Collections.sort(dates);
		if(dates.isEmpty()){
			return null;
		}
		else {
			return dates.get(dates.size()-1);
		}
	}
	
	public boolean is(java.util.Date d, DatePosition p, java.util.Date d1){
		if(p.equals(DatePosition.AFTER))
			return d.after(d1);
		else 
			return d.before(d1);
	}
	
	public java.util.Date resolveStaticReference(RelativeTo to, RelativeDateRule rule, ResolvedDates dates){
		java.util.Date ref = to.equals(RelativeTo.EVALDATE) ? dates.getEval() : dates.getDob();
		java.util.Date date = this.from(rule.getYear(), rule.getMonth(), rule.getWeek(), rule.getDay(), rule.getPosition(), ref);
		return date;
	}
	
	public java.util.Date resolveDynamicReference(Integer id, RelativeDateRule rule, ResolvedDates dates){
		if(dates.getEvents().containsKey(id)){
			return this.from(rule.getYear(), rule.getMonth(), rule.getWeek(), rule.getDay(), rule.getPosition(), dates.getEvents().get(id));
		}
		return null;
	}
	
	public VaccinationEvent getEventAtPosition(List<VaccinationEvent> list, int position){
		for(VaccinationEvent e : list){
			if(e.getPosition() == position)
				return e;
		}
		return null;
	}

//	@Override
	public java.util.Date from_(int years, int months, int days, DatePosition p, java.util.Date ref) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
	    calendar.setTime(ref);
	    int multiple = 1;
	    if(p.equals(DatePosition.BEFORE)){
	    	multiple = multiple * -1;
	    }
	    int nbMonths = ( months + years * 12) * multiple;
	    int nbDays = days * multiple;
	    
	    calendar.add(Calendar.MONTH, nbMonths);
	    calendar.add(Calendar.DAY_OF_MONTH, nbDays);
		return calendar.getTime();
	}
	
	@Override
	public java.util.Date from(int years, int months, int weeks, int days, DatePosition p, java.util.Date ref) {
		return this.from_(years, months, days + weeks * 7, p, ref);
	}

	@Override
	public boolean same(java.util.Date d1, java.util.Date d2) {
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		return dt1.toLocalDate().compareTo(dt2.toLocalDate()) == 0;
	}

	@Override
	public void toFixed(TestCase tc, java.util.Date today) {
		
		ResolvedDates dates = this.resolveDates(tc, today);
		tc.getPatient().setDob(fixed(dates.getDob()));
		tc.setEvalDate(fixed(dates.getEval()));
		
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				java.util.Date dA = dates.getEvents().get(ve.getPosition());
				ve.setDate(fixed(dA));
			}
		}
		
		for(ExpectedForecast fc : tc.getForecast()){
		
			if(fc.getEarliest() != null)
				fc.setEarliest(fixed(fix(dates, fc.getEarliest())));
			if(fc.getRecommended() != null)
				fc.setRecommended(fixed(fix(dates, fc.getRecommended())));
			if(fc.getPastDue() != null)
				fc.setPastDue(fixed(fix(dates, fc.getPastDue())));
			if(fc.getComplete() != null)
				fc.setComplete(fixed(fix(dates, fc.getComplete())));
			
		}
	}
	
	public FixedDate fixed(java.util.Date date){
		return new FixedDate(date);
	}

}
