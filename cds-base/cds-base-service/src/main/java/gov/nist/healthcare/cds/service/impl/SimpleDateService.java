package gov.nist.healthcare.cds.service.impl;

import java.util.Calendar;

import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.domain.Date;
import gov.nist.healthcare.cds.domain.Event;
import gov.nist.healthcare.cds.domain.ExpectedForecast;
import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.domain.RelativeDate;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.exception.UnresolvableDate;
import gov.nist.healthcare.cds.enumeration.RelativeTo;
import gov.nist.healthcare.cds.service.DateService;

@Service
public class SimpleDateService implements DateService {

	public TestCase fixDates(TestCase tc, java.util.Date snap) {

		// DOB
		if(this.isRelative(tc.getPatient().getDob())){
			RelativeDate rtED = (RelativeDate) tc.getPatient().getDob();
			if(rtED.getRelativeTo().equals(RelativeTo.TODAY)){
				tc.getPatient().setDob(this.fix(rtED, snap));
			}
			else {
				FixedDate fd = (FixedDate) tc.getEvalDate();
				tc.getPatient().setDob(this.fix(rtED, fd.getDate()));
			}
		}
		
		FixedDate dob = (FixedDate) tc.getPatient().getDob();
		FixedDate eval = (FixedDate) tc.getEvalDate();
		
		// Events
		for(Event ev : tc.getEvents()){
			ev.setDate(this.fix(ev.getDate(), dob, eval, snap));
		}
		// Forecasts
		for(ExpectedForecast fc : tc.getForecast()){
			fc.setEarliest(this.fix(fc.getEarliest(), dob, eval, snap));
			fc.setRecommended(this.fix(fc.getRecommended(), dob, eval, snap));
			fc.setPastDue(this.fix(fc.getPastDue(), dob, eval, snap));
			fc.setComplete(this.fix(fc.getComplete(), dob, eval, snap));
		}
		return tc;
	}

	@Override
	public FixedDate fix(Date d, FixedDate dob, FixedDate eval, java.util.Date today){
		if(!this.isRelative(d)){
			return (FixedDate) d;
		}
		else {
			RelativeDate rd = (RelativeDate) d;
			if(rd.getRelativeTo().equals(RelativeTo.DOB)){
				return this.fix(rd, dob.getDate());
			}
			else if(rd.getRelativeTo().equals(RelativeTo.EVALDATE)){
				return this.fix(rd, eval.getDate());
			}
			else {
				return this.fix(rd, today);
			}
			
		}
	}

	

	@Override
	public FixedDate fix(Date d1, java.util.Date from) {
		if(d1 instanceof FixedDate){
			return (FixedDate) d1;
		}
		else {
			RelativeDate rd = (RelativeDate) d1;
			Calendar calendar = Calendar.getInstance();
		    calendar.setTime(from);
		    int nbMonths = rd.getMonth() + rd.getYear() * 12;
		    calendar.add(Calendar.MONTH, nbMonths);
		    calendar.add(Calendar.DAY_OF_MONTH, rd.getDay());
			return new FixedDate(calendar.getTime());
		}
	}
	
	public boolean isRelative(Date d){
		return d instanceof RelativeDate;
	}

	@Override
	public FixedDate evaluationDate(TestCase tc, java.util.Date today) throws UnresolvableDate {
		if(this.isRelative(tc.getEvalDate())){
			RelativeDate rtED = (RelativeDate) tc.getEvalDate();
			if(rtED.getRelativeTo().equals(RelativeTo.TODAY)){
				return this.fix(rtED, today);
			}
			else {	
				if(this.isRelative(tc.getPatient().getDob())){
					RelativeDate rd = (RelativeDate) tc.getPatient().getDob();
					if(rd.getRelativeTo().equals(RelativeTo.TODAY)){
						FixedDate fd = this.fix(rd, today);
						return this.fix(rtED, fd.getDate());
					}
					else {
						throw new UnresolvableDate();
					}
				}
				else {
					FixedDate fd = (FixedDate) tc.getPatient().getDob();
					return this.fix(rtED, fd.getDate());
				}
			}
		}
		else
			return (FixedDate) tc.getEvalDate();
	}
	
	public static void main(String[] args) {
		Date a = null;
		FixedDate fd = (FixedDate) a;
		SimpleDateService sds = new SimpleDateService();
		RelativeDate rd = new RelativeDate();
		rd.setMonth(1);
		rd.setDay(-1);
		System.out.println(sds.fix(rd,Calendar.getInstance().getTime()));
	}

	@Override
	public boolean same(java.util.Date d1, java.util.Date d2) {
		return d1.compareTo(d2) == 0;
	}
	

}
