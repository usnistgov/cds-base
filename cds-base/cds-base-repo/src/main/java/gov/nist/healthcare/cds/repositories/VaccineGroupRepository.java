package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineGroupRepository extends JpaRepository<VaccineGroup, Long>{
	public VaccineGroup findByNameIgnoreCase(String name);
	public VaccineGroup findByCvx(String cvx);
}
