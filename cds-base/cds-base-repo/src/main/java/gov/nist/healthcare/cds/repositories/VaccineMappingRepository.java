package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineMappingRepository extends JpaRepository<VaccineMapping, Long>{
//	public VaccineMapping findByName(String name);
//	public VaccineMapping findByCvx(String cvx);
}
