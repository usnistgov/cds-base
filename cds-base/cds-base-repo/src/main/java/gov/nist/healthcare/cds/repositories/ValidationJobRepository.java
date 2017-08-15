package gov.nist.healthcare.cds.repositories;


import gov.nist.healthcare.cds.domain.ValidationJob;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationJobRepository extends MongoRepository<ValidationJob, String>{
	
}
