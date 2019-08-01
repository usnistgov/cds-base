package gov.nist.healthcare.cds.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import gov.nist.healthcare.cds.domain.CommentHolder;

public interface CommentRepository extends MongoRepository<CommentHolder, String> {

}
