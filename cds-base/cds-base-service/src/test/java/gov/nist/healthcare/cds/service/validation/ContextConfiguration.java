package gov.nist.healthcare.cds.service.validation;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.TestRunnerService;

@Configuration
@ComponentScan({ "gov.nist.healthcare.cds"})
public class ContextConfiguration {

	@Bean
	public TestRunnerService runner(){
		return Mockito.mock(TestRunnerService.class);
	}
	
	@Bean
	@Primary
	public VaccineMappingRepository vmr(){
		return Mockito.mock(VaccineMappingRepository.class);
	}
	
}
