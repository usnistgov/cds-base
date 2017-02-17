package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineMappingRepository extends JpaRepository<VaccineMapping, Long>{
//	public VaccineMapping findByName(String name);
//	public VaccineMapping findByCvx(String cvx);
	
	@Query("select m from VaccineMapping m where m.vx.id = :cvx")
	public VaccineMapping findMapping(@Param("cvx") String cvx);
}
