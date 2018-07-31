package gov.nist.healthcare.cds.service.validation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.TestRunnerService;
import gov.nist.healthcare.cds.service.VaccineMatcherService;
import gov.nist.healthcare.cds.service.impl.validation.ConfigurableVaccineMatcher;
import gov.nist.healthcare.cds.service.vaccine.VaccineMatcherConfiguration;

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
	
	@Bean
	public VaccineMatcherService vms(){
		return new ConfigurableVaccineMatcher();
	}
	
	@Bean
	public PasswordEncoder passEncode(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public VaccineMatcherConfiguration matcherConfig() throws JAXBException{
		return new VaccineMatcherConfiguration();
	}
	
	@Bean
	public AuthenticationEntryPoint auth(){
		return new AuthenticationEntryPoint() {
			
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException authException) throws IOException, ServletException {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
}
