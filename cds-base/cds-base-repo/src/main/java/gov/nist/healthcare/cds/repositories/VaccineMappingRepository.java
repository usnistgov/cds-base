package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineMappingRepository extends MongoRepository<VaccineMapping, String>{
//	public VaccineMapping findByName(String name);
//	public VaccineMapping findByCvx(String cvx);
	
	
	@Query("{ 'vx.$id' : ?0 }")
	public VaccineMapping findMapping(String cvx);
}
