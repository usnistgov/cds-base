package gov.nist.healthcare.cds.auth.service;

import gov.nist.healthcare.cds.auth.domain.Account;
import gov.nist.healthcare.cds.auth.domain.PasswordChange;
import gov.nist.healthcare.cds.auth.domain.PasswordChangeException;
import gov.nist.healthcare.cds.auth.domain.Privilege;

public interface AccountService {

	public Account getCurrentUser();
	public Account getAccountByUsername(String username);
	public Account createAdmin(Account account);
	public Account createTester(Account account);
	public Account createUser(Account account,Privilege p);
	public Account changePassword(Account account,PasswordChange pChange) throws PasswordChangeException;
	public Account changePassword(String newPass,String id) throws PasswordChangeException;
	public void deleteAll();
	public Account changePasswordForUser(Account onRecordAccount, PasswordChange acc) throws PasswordChangeException;
	
}
