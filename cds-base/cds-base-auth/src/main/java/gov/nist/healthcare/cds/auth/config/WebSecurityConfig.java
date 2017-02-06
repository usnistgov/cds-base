package gov.nist.healthcare.cds.auth.config;

import gov.nist.healthcare.cds.auth.security.AccountUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@ComponentScan("gov.nist.healthcare.cds.auth")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AccountUserDetailService authenticationService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            auth.userDetailsService(authenticationService).passwordEncoder(encoder);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	     web.ignoring()
	        .antMatchers("/fonts/**/*","/lang/**/*","/lib/**/*","/resources/**/*","/scripts/**/*","/styles/**/*","/views/**/*");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/api/logout")
				.permitAll()
		.antMatchers("/api/**")
				.fullyAuthenticated()
			.and()
				.httpBasic()
					.authenticationEntryPoint(authenticationEntryPoint)
			.and()
				.sessionManagement()
					.maximumSessions(1);
		http
			.csrf()
				.disable();
	}
	
	
	
}
