package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TestPlanRepository extends MongoRepository<TestPlan, String>{ 
	
	public List<TestPlan> findByUser(String user);
	
	@Query(value="{ 'testCases.id' : ?0 }",fields="{ 'user' : 1 }")
	public TestPlan tcUser(String testId);
}
