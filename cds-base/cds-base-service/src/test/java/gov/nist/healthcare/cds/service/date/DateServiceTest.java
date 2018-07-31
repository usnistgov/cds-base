package gov.nist.healthcare.cds.service.date;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.Assert;

import gov.nist.healthcare.cds.domain.FixedDate;
import gov.nist.healthcare.cds.enumeration.DatePosition;
import gov.nist.healthcare.cds.service.DateService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=gov.nist.healthcare.cds.service.validation.ContextConfiguration.class)
public class DateServiceTest {
	
	@Autowired
	private DateService dateService;
	
	
	public boolean check(int y, int m, int d, DatePosition position, String start_date, String end_date) throws ParseException{
		Date startDate = FixedDate.DATE_FORMAT.parse(start_date);
		Date endDate = dateService.from(y, m, d, 0, position, startDate);
		return FixedDate.DATE_FORMAT.format(endDate).equals(end_date);
	}
	
	@Test
	public void simple18MonthsForward() throws ParseException{		
		Assert.isTrue(check(0,18,0, DatePosition.AFTER, "07/17/2017", "01/17/2019"));
	}
	
	@Test
	public void mustRollForward() throws ParseException{		
		Assert.isTrue(check(0,1,0, DatePosition.AFTER, "01/29/2018", "03/01/2018"));
	}
	
	@Test
	public void mustNotRollForward() throws ParseException{
		Assert.isTrue(check(0,1,0, DatePosition.AFTER, "01/29/2016", "02/29/2016"));
	}
	
	@Test
	public void jumpOverFebruary() throws ParseException{
		Assert.isTrue(check(0,2,0, DatePosition.AFTER, "01/31/2018", "03/31/2018"));
	}
	
	@Test
	public void jumpOverLeapYear() throws ParseException{
		Assert.isTrue(check(0,12,0, DatePosition.AFTER, "06/30/2015", "06/30/2016"));
	}
	
	@Test
	public void dateMathBijection() throws ParseException{
		Date A = FixedDate.DATE_FORMAT.parse("03/08/2018");
		Date B = dateService.from(6, 11, 0, 14, DatePosition.BEFORE, A);
		Date C = dateService.from(6, 11, 0, 14, DatePosition.AFTER, B);
		
		
		Assert.isTrue(FixedDate.DATE_FORMAT.format(A).equals(FixedDate.DATE_FORMAT.format(C)));
	}

}