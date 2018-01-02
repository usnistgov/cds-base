package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TestPlanRepository extends MongoRepository<TestPlan, String>{ 
	
	public List<TestPlan> findByUser(String user);
	
	@Query(value="{ $or : [ {'testCases.id' : ?0}, { 'testCaseGroups.testCases.id' : ?0 } ] }",fields="{ 'isPublic' : 1, 'user' : 1, 'viewers' : 1 }")
	public TestPlan tcUser(String testId);
	
	@Query(value="{ $and : [ { 'user' : { $ne : ?0 } },{ $or : [ {'viewers' : ?0} , {'isPublic' : true } ] } ] }")
	public List<TestPlan> sharedWithUser(String user);
	
	@Query(value="{ $or : [ {'viewers' : ?0} , {'user' : ?0 } ] }")
	public List<TestPlan> filtred(String user);
	
	@Query(value="{'testCaseGroups' : { $elemMatch: { 'id' :  ?0 } } }")
	public TestPlan testCaseGroup(String groupId);

}
