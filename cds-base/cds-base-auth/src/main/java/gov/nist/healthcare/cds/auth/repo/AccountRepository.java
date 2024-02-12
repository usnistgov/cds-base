package gov.nist.healthcare.cds.auth.repo;

import gov.nist.healthcare.cds.auth.domain.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	
	public Account findByUsername(String username);
	
	public Account findByEmailIgnoreCase(String email);

	@Query("{'privileges.$id' : ?0 }")
	public List<Account> findByRole(String roleId);
}
