package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.UserMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMetadataRepository extends MongoRepository<UserMetadata, String> {

}
