package gov.nist.healthcare.cds.service.impl;

import java.text.ParseException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDateTime;
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
	public ResolvedDates resolveDates(TestCase tc, LocalDate today){
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
	
	public LocalDate asFixed(Date d){
		if(d instanceof FixedDate)
			return ((FixedDate) d).asDate();
		return null;
	}
	
	@Override
	public LocalDate fix(ResolvedDates rds, Date dt) {
		if(dt instanceof RelativeDate){
			RelativeDate d = (RelativeDate) dt;
			List<LocalDate> rules = this.resolve(d.getRules(), rds, null, false);
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
	
	public List<LocalDate> resolve(List<RelativeDateRule> rdrs, ResolvedDates rdates,List<VaccinationEvent> events, boolean recursive){
		List<LocalDate> dates = new ArrayList<>();
		LocalDate d;
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
	
	public LocalDate resolveEvent(ResolvedDates dates, VaccinationEvent e, List<VaccinationEvent> events){
		RelativeDate dateRef = (RelativeDate) e.getDate();
		List<LocalDate> rules = this.resolve(dateRef.getRules(), dates, events, true);
		LocalDate result = fit(rules);
		dates.getEvents().put(e.getPosition(), result);
		return result;
	}
	
	public LocalDate fit(List<LocalDate> dates){
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
	
	public LocalDate resolveStaticReference(RelativeTo to, RelativeDateRule rule, ResolvedDates dates){
		LocalDate ref = to.equals(RelativeTo.EVALDATE) ? dates.getEval() : dates.getDob();
		LocalDate date = this.from(rule.getYear(), rule.getMonth(), rule.getWeek(), rule.getDay(), rule.getPosition(), ref);
		return date;
	}
	
	public LocalDate resolveDynamicReference(Integer id, RelativeDateRule rule, ResolvedDates dates){
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
	
	public LocalDate from_(int years, int months, int days, DatePosition p, LocalDate ref) {
	    if(!p.equals(DatePosition.BEFORE)){
	    	return plus(Period.ofDays(days), plus(Period.ofMonths(months), plus(Period.ofYears(years), ref, false), false), true);
	    }
	    else {
	    	return ref.minusDays(days).minusMonths(months).minusYears(years);
	    }
	}
	
	
	private LocalDate rollForwardIfApplicable(LocalDate localDateStart, LocalDate localDateEnd){
		org.joda.time.LocalDateTime start = new org.joda.time.LocalDateTime(
				localDateStart.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
		);
		org.joda.time.LocalDateTime end = new org.joda.time.LocalDateTime(
				localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
		);

		boolean start_month_has_more_days_than_end_month = start.dayOfMonth().getMaximumValue() > end.dayOfMonth().getMaximumValue();
		boolean end_day_of_month_is_last_day_of_month = end.dayOfMonth().getMaximumValue() == end.getDayOfMonth();
		boolean start_day_of_month_greater_than_end_day_of_month = start.getDayOfMonth() > end.getDayOfMonth();
		if(start_month_has_more_days_than_end_month && end_day_of_month_is_last_day_of_month && start_day_of_month_greater_than_end_day_of_month){
			return localDateEnd.plusDays(1);
		}
		else {
			return localDateEnd;
		}
	}
	
	private LocalDate plus(Period duration, LocalDate reference, boolean days){
		if(!days){
			LocalDate intermediate = reference.plus(duration);
			return rollForwardIfApplicable(reference, intermediate);
		}
		else {
			return reference.plus(duration);
		}
	}
	
    private static boolean mustRollForward(Calendar calOrig, Calendar cal, int calField) {
        return ((calField == Calendar.YEAR || calField == Calendar.MONTH ) &&
        calOrig.getActualMaximum(Calendar.DAY_OF_MONTH) > cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
        cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH) &&
        calOrig.get(Calendar.DAY_OF_MONTH) > cal.get(Calendar.DAY_OF_MONTH));
    }
	
	@Override
	public LocalDate from(int years, int months, int weeks, int days, DatePosition p, LocalDate ref) {
		return this.from_(years, months, days + weeks * 7, p, ref);
	}

	@Override
	public boolean same(LocalDate d1, LocalDate d2) {
		return d1.isEqual(d2);
	}

	@Override
	public void toFixed(TestCase tc, LocalDate today) {
		
		ResolvedDates dates = this.resolveDates(tc, today);
		tc.getPatient().setDob(fixed(dates.getDob()));
		tc.setEvalDate(fixed(dates.getEval()));
		
		for(Event e : tc.getEvents()){
			if(e instanceof VaccinationEvent){
				VaccinationEvent ve = (VaccinationEvent) e;
				LocalDate dA = dates.getEvents().get(ve.getPosition());
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
	
	public FixedDate fixed(LocalDate date){
		return new FixedDate(FixedDate.formatter.format(date));
	}

	protected static SimpleDateService getInstance() {
		return new SimpleDateService();
	}

}
