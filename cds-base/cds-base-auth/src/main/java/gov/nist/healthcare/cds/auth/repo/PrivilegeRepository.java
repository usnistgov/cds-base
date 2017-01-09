package gov.nist.healthcare.cds.auth.repo;

import gov.nist.healthcare.cds.auth.domain.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	
	public Privilege findByRole(String role);
}
