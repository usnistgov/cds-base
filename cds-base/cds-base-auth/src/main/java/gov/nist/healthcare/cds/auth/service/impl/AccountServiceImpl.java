package gov.nist.healthcare.cds.auth.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.auth.domain.Account;
import gov.nist.healthcare.cds.auth.domain.PasswordChange;
import gov.nist.healthcare.cds.auth.domain.Privilege;
import gov.nist.healthcare.cds.auth.repo.AccountRepository;
import gov.nist.healthcare.cds.auth.repo.PrivilegeRepository;
import gov.nist.healthcare.cds.auth.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Override
	public Account getAccountByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public Account createAdmin(Account account) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!account.getUsername().isEmpty()
				&& !account.getPassword().isEmpty()) {
			account.setPassword(encoder.encode(account.getPassword()));
			Set<Privilege> roles = new HashSet<Privilege>(
					privilegeRepository.findAll());
			account.setPrivileges(roles);
			accountRepository.save(account);
			return account;
		}
		return null;
	}

	@Override
	public Account createTester(Account account) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (!account.getUsername().isEmpty()
				&& !account.getPassword().isEmpty()) {
			account.setPassword(encoder.encode(account.getPassword()));
			Set<Privilege> roles = new HashSet<Privilege>();
			roles.add(privilegeRepository.findByRole("TESTER"));
			account.setPrivileges(roles);
			accountRepository.save(account);
			return account;
		}
		return null;
	}

	@Override
	public void deleteAll() {

		accountRepository.deleteAll();
	}

	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication == null
				|| !(authentication.getPrincipal() instanceof UserDetails)) {
			return null;
		}

		return (User) authentication.getPrincipal();
	}

	@Override
	public Account createUser(Account account, Privilege p) {
		if(p.getRole().equals("ADMIN")){
			this.createAdmin(account);
		}
		else {
			this.createTester(account);
		}
		return null;
	}

	@Override
	public Account changePassword(Account account, PasswordChange pChange) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String newP = encoder.encode(pChange.getNewPassword());

		if(!account.getPassword().equals(newP)){
			account.setPassword(encoder.encode(pChange.getNewPassword()));
			return this.accountRepository.save(account);
		}
		else {
			throw new BadCredentialsException("New password must be different from previous password");
		}
		
	}

	@Override
	public Account changePassword(String newPass, String id) {
		Account a = accountRepository.findOne(id);
		PasswordChange pChange = new PasswordChange();
		pChange.setNewPassword(newPass);
		pChange.setPassword(a.getPassword());
		pChange.setUsername(a.getUsername());
		return this.changePassword(a, pChange);
	}

}
