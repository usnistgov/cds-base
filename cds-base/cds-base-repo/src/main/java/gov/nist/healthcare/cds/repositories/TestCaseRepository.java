package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {

    public TestCase findByUser(String user);
    public long countByUser(String user);

}
