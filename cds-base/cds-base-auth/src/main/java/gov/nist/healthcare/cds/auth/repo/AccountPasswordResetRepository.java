package gov.nist.healthcare.cds.auth.repo;

import gov.nist.healthcare.cds.auth.domain.AccountPasswordReset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountPasswordResetRepository extends MongoRepository<AccountPasswordReset, String> {
	
	@Query("{ 'username' : ?0 }")
	public AccountPasswordReset findByUsername(String username);
}
