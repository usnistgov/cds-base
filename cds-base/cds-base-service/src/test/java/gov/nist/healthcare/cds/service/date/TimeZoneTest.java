package gov.nist.healthcare.cds.service.date;

import gov.nist.healthcare.cds.domain.*;
import gov.nist.healthcare.cds.domain.wrapper.ActualForecast;
import gov.nist.healthcare.cds.domain.wrapper.ForecastValidation;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.enumeration.DateType;
import gov.nist.healthcare.cds.enumeration.Gender;
import gov.nist.healthcare.cds.enumeration.SerieStatus;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.impl.validation.ExecutionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader= AnnotationConfigContextLoader.class, classes=gov.nist.healthcare.cds.service.validation.ContextConfiguration.class)
public class TimeZoneTest {

	@Autowired
	private ExecutionService executionService;
	@Mock
	@Autowired
	private VaccineMappingRepository vaccineMappingRepository;

	static LocalDate getLocalDate(TimeZone tz, java.util.Date date) {
		return date.toInstant().atZone(tz.toZoneId()).toLocalDate();
	}

	Vaccine getHepAVaccine() {
		Vaccine vaccine = new Vaccine();
		vaccine.setCvx("95");
		vaccine.setDetails("Hep A");
		vaccine.setName("Hep A");
		vaccine.setType("generic");
		vaccine.setId("95");
		return vaccine;
	}

	TestCase createTestCase() {
		// Assessment date is Tue Apr 11 2022
		// Forecast for 85
		// Earliest 20211011
		// Recommended 20211011
		// Latest 20221011
		ExpectedForecast expectedForecast = new ExpectedForecast();
		expectedForecast.setEarliest(new FixedDate("10/11/2021"));
		expectedForecast.setRecommended(new FixedDate("10/11/2021"));
		expectedForecast.setPastDue(new FixedDate("10/11/2022"));
		expectedForecast.setId(0L);
		expectedForecast.setSerieStatus(SerieStatus.N);
		expectedForecast.setTarget(getHepAVaccine());

		TestCase testCase = new TestCase();
		testCase.setId("TEST");
		testCase.setUid("TEST");
		testCase.setDateType(DateType.FIXED);
		testCase.setEvalDate(new FixedDate("04/11/2023"));
		Patient patient = new Patient();
		patient.setGender(Gender.F);
		patient.setDob(new FixedDate("10/11/2023"));
		testCase.setPatient(patient);
		testCase.setEvents(new ArrayList<>());
		testCase.setForecast(Collections.singletonList(expectedForecast));
		return testCase;
	}

	@Before
	public void prepare() {
		VaccineMapping mapping = new VaccineMapping();
		mapping.setGroup(false);
		mapping.setId("95");
		mapping.setVx(getHepAVaccine());
		mapping.setGroups(new HashSet<>());
		mapping.setProducts(new HashSet<>());
		when(vaccineMappingRepository.findMapping("95")).thenReturn(mapping);
	}

	@Test
	public void testUTC() {
		test(TimeZone.getTimeZone("Etc/UTC"));
	}

	@Test
	public void testGUAM() {
		test(TimeZone.getTimeZone("Pacific/Guam"));
	}

	public void test(TimeZone tz) {
		TestCase testCase = createTestCase();
		Date earliest = Date.from(LocalDateTime.of(2021, 10, 11, 0, 0).
				atZone(tz.toZoneId())
				.toInstant()
		);
		Date recommended = Date.from(LocalDateTime.of(2021, 10, 11, 0, 0).
				atZone(tz.toZoneId())
				.toInstant()
		);

		Date latest = Date.from(LocalDateTime.of(2022, 10, 11, 0, 0).
				atZone(tz.toZoneId())
				.toInstant()
		);

		ActualForecast actualForecast = new ActualForecast();
		actualForecast.setVaccine(new VaccineRef("95"));
		actualForecast.setSerieStatus(SerieStatus.N);
		actualForecast.setEarliest(getLocalDate(tz, earliest));
		actualForecast.setRecommended(getLocalDate(tz, recommended));
		actualForecast.setPastDue(getLocalDate(tz, latest));


		Report report = executionService.validateResponse(
				Collections.singletonList(actualForecast),
				new ArrayList<>(),
				testCase,
				LocalDate.now()
		);


		ForecastValidation forecastValidation = report.getFcValidation().get(0);

		System.out.println("-- TimeZone : "+ tz.getDisplayName());
		System.out.println("expected earliest date = "
				+ forecastValidation.getForecastRequirement().getEarliest().asDate());
		System.out.println("expected earliest date string = "
				+ forecastValidation.getForecastRequirement().getEarliest().getDateString());
		System.out.println("-");
		System.out.println("expected recommended date = "
				+ forecastValidation.getForecastRequirement().getRecommended().asDate());
		System.out.println("expected recommended date string = "
				+ forecastValidation.getForecastRequirement().getRecommended().getDateString());
		System.out.println("-");
		System.out.println("expected past due date = "
				+ forecastValidation.getForecastRequirement().getPastDue().asDate());
		System.out.println("expected past due date string = "
				+ forecastValidation.getForecastRequirement().getPastDue().getDateString());

		System.out.println("--");
		System.out
				.println("actual earliest date = " + forecastValidation.getEarliest().getValue().asDate());
		System.out.println("actual earliest date string = "
				+ forecastValidation.getEarliest().getValue().getDateString());
		System.out.println("-");
		System.out.println(
				"actual recommended date = " + forecastValidation.getRecommended().getValue().asDate());
		System.out.println("actual recommended date string = "
				+ forecastValidation.getRecommended().getValue().getDateString());
		System.out.println("-");
		System.out
				.println("actual past due date = " + forecastValidation.getPastDue().getValue().asDate());
		System.out.println("actual past due date string = "
				+ forecastValidation.getPastDue().getValue().getDateString());

		assertEquals("10/11/2021", actualForecast.getEarliest().format(FixedDate.formatter));
		assertEquals("10/11/2021", actualForecast.getRecommended().format(FixedDate.formatter));
		assertEquals("10/11/2022", actualForecast.getPastDue().format(FixedDate.formatter));

		assertEquals("10/11/2021",
				forecastValidation.getForecastRequirement().getEarliest().getDateString());
		assertEquals("10/11/2021",
				forecastValidation.getForecastRequirement().getRecommended().getDateString());
		assertEquals("10/11/2022",
				forecastValidation.getForecastRequirement().getPastDue().getDateString());

		assertEquals("10/11/2021", forecastValidation.getEarliest().getValue().getDateString());
		assertEquals("10/11/2021", forecastValidation.getRecommended().getValue().getDateString());
		assertEquals("10/11/2022", forecastValidation.getPastDue().getValue().getDateString());
	}
}
