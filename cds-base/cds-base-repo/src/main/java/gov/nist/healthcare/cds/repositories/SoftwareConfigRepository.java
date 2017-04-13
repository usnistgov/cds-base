package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareConfigRepository extends MongoRepository<SoftwareConfig, String>{

	public List<SoftwareConfig> findByUser(String user);
    
}
