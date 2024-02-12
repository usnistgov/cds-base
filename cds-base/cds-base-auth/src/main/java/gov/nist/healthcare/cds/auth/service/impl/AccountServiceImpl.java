package gov.nist.healthcare.cds.auth.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import gov.nist.healthcare.cds.auth.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.auth.repo.AccountRepository;
import gov.nist.healthcare.cds.auth.repo.PrivilegeRepository;
import gov.nist.healthcare.cds.auth.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Account getAccountByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public Account createAdmin(Account account) {
		if (!account.getUsername().isEmpty() && !account.getPassword().isEmpty()) {
			account.setPassword(encoder.encode(account.getPassword()));
			Set<Privilege> roles = new HashSet<Privilege>(privilegeRepository.findAll());
			account.setAccountType("ADMIN");
			account.setPrivileges(roles);
			accountRepository.save(account);
			return account;
		}
		return null;
	}

	@Override
	public Account createTester(Account account) {
		if (!account.getUsername().isEmpty() && !account.getPassword().isEmpty()) {
			account.setPassword(encoder.encode(account.getPassword()));
			Set<Privilege> roles = new HashSet<Privilege>();
			roles.add(privilegeRepository.findByRole("TESTER"));
			account.setPrivileges(roles);
			account.setAccountType("TESTER");
			accountRepository.save(account);
			return account;
		}
		return null;
	}

	public List<Account> getAdminUsers() {
		Privilege admin = privilegeRepository.findByRole("ADMIN");
		return this.accountRepository.findByRole(admin.getId());
	}

	@Override
	public Account getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication == null
				|| !(authentication.getPrincipal() instanceof Account)) {
			return null;
		}

		return (Account) authentication.getPrincipal();
	}

	@Override
	public Account changePassword(Account account, PasswordChange pChange) throws PasswordChangeException {
		if(encoder.matches(pChange.getPassword(), account.getPassword())){
			checkPassword(pChange.getNewPassword());
			account.setPassword(encoder.encode(pChange.getNewPassword()));
			return this.accountRepository.save(account);
		}
		else {
			throw new PasswordChangeException("Incorrect password");
		}
	}

	@Override
	public Account changePasswordWithoutOldCheck(AccountPasswordReset token, String URLToken, Account account, String password) throws PasswordChangeException {
		// If token is not for username
		if(!token.getUsername().equals(account.getUsername())) {
			throw new PasswordChangeException("Invalid Request");
		}
		// If token does not match
		if (!token.getCurrentToken().equals(URLToken)) {
			throw new PasswordChangeException("Invalid Request");
		}
		// If token is expired
		if (token.isTokenExpired()) {
			throw new PasswordChangeException("Request has expired");
		}

		// Check Password
		checkPassword(password);
		account.setPassword(encoder.encode(password));
		return this.accountRepository.save(account);
	}

	@Override
	public Account changePasswordForUser(Account account, PasswordChange pChange) throws PasswordChangeException {
		// Check Password
		checkPassword(pChange.getNewPassword());
		account.setPassword(encoder.encode(pChange.getNewPassword()));
		return this.accountRepository.save(account);
	}

	public void checkPassword(String password)  throws PasswordChangeException {
		List<String> issues = this.checkPasswordPolicy(password);
		if(issues != null && !issues.isEmpty()) {
			throw new PasswordChangeException(String.join(", ", issues));
		}
	}

	@Override
	public List<String> checkPasswordPolicy(String password) {
		List<String> passwordIssues = new ArrayList<>();
		if(password == null || password.isEmpty()) {
			passwordIssues.add("Password can not be empty");
		} else {
			if(!password.matches("(?=(.*[0-9]))(?=.*[a-z])(?=(.*[A-Z]))(?=(.*)).{7,20}")) {
				passwordIssues.add("Password must be at least 7 characters long, with at least one letter, one uppercase, and one number");
			}
		}
		return passwordIssues;
	}

	@Override
	public List<String> checkAccountInfo(Account account) {
		List<String> issues = new ArrayList<>();
		// Check username
		if(account.getUsername() == null || account.getUsername().isEmpty()) {
			issues.add("Username is required");
		} else if(account.getUsername().length() < 4 || account.getFullName().length() > 20) {
			issues.add("Username must be at least 4 characters and no more than 20 characters");
		} else if(!account.getUsername().matches("^[a-zA-Z0-9-_]+$")) {
			issues.add("Username can contain only letters and numbers and - _ characters");
		}
		// Check name
		if(account.getFullName() == null || account.getFullName().isEmpty()) {
			issues.add("Full Name is required");
		} else if(account.getFullName().length() < 4 || account.getFullName().length() > 50) {
			issues.add("Full Name must be at least 4 characters and no more than 50 characters");
		}
		// Check email
		if(account.getEmail() == null || account.getEmail().isEmpty()) {
			issues.add("Email is required");
		} else if(account.getEmail().length() < 5 || account.getEmail().length() > 200) {
			issues.add("Email must be at least 4 characters and no more than 200 characters");
		} else if(!account.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
			issues.add("Invalid email");
		}
		// Check organization
		if(account.getOrganization() != null && !account.getOrganization().isEmpty()) {
			if(account.getOrganization().length() > 200) {
				issues.add("Organization can't be more than 200 characters");
			}
		}
		return issues;
	}

	@Override
	public List<String> checkAccountUniqueness(Account account) {
		List<String> issues = new ArrayList<>();
		Account byEmail = accountRepository.findByEmailIgnoreCase(account.getEmail());
		Account byUsername = accountRepository.findByUsername(account.getUsername());
		if(byEmail != null) {
			issues.add("Email address already used");
		}
		if(byUsername != null) {
			issues.add("Username already used");
		}
		return issues;
	}

}
