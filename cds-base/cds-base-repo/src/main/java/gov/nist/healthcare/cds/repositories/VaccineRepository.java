package gov.nist.healthcare.cds.repositories;


import gov.nist.healthcare.cds.domain.Vaccine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends MongoRepository<Vaccine, String>{
	

	public Vaccine findByName(String name);
    
	public Vaccine findByNameIgnoreCase(String name);
}
