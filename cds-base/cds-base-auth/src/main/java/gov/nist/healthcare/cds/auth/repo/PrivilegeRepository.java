package gov.nist.healthcare.cds.auth.repo;

import gov.nist.healthcare.cds.auth.domain.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege, String> {
	
	public Privilege findByRole(String role);
}
