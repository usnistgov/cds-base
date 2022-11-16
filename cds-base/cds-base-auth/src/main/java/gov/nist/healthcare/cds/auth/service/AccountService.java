package gov.nist.healthcare.cds.auth.service;

import gov.nist.healthcare.cds.auth.domain.*;

import java.util.List;

public interface AccountService {

	public Account getCurrentUser();
	public Account getAccountByUsername(String username);
	public Account createAdmin(Account account);
	public Account createTester(Account account);
	public Account changePassword(Account account,PasswordChange pChange) throws PasswordChangeException;
	Account changePasswordWithoutOldCheck(AccountPasswordReset token, String URLToken, Account account, String password) throws PasswordChangeException;
	public Account changePasswordForUser(Account onRecordAccount, PasswordChange acc) throws PasswordChangeException;
	public List<String> checkPasswordPolicy(String password);
	public List<String> checkAccountInfo(Account account);
	public List<String> checkAccountUniqueness(Account account);
	
}
