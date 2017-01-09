package gov.nist.healthcare.cds.auth.service;

import org.springframework.security.core.userdetails.User;

import gov.nist.healthcare.cds.auth.domain.Account;

public interface AccountService {

	public User getCurrentUser();
	public Account getAccountByUsername(String username);
	public Account createAdmin(Account account);
	public Account createTester(Account account);
	public void deleteAll();
	
}
