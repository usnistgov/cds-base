package gov.nist.healthcare.cds.service.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.Assert;

import gov.nist.healthcare.cds.service.TestRunnerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class ValidationTest {

    @Configuration
    @ComponentScan({ "gov.nist.healthcare.cds"})
    static class ContextConfiguration {
    	
    	@Bean
    	public TestRunnerService runner(){
    		return Mockito.mock(TestRunnerService.class);
    	}
    }
    
	@Test
	public void validate(){
		Assert.isTrue(true,"message dfdf");
	}

}
