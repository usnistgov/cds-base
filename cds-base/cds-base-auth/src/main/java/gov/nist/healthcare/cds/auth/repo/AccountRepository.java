package gov.nist.healthcare.cds.auth.repo;

import gov.nist.healthcare.cds.auth.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	public Account findByUsername(String username);
}
