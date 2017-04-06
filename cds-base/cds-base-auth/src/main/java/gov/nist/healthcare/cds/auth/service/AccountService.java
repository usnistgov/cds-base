package gov.nist.healthcare.cds.auth.service;

import org.springframework.security.core.userdetails.User;

import gov.nist.healthcare.cds.auth.domain.Account;
import gov.nist.healthcare.cds.auth.domain.PasswordChange;
import gov.nist.healthcare.cds.auth.domain.Privilege;

public interface AccountService {

	public User getCurrentUser();
	public Account getAccountByUsername(String username);
	public Account createAdmin(Account account);
	public Account createTester(Account account);
	public Account createUser(Account account,Privilege p);
	public Account changePassword(Account account,PasswordChange pChange);
	public Account changePassword(String newPass,String id);
	public void deleteAll();
	
}
