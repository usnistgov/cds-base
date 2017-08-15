package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.wrapper.Report;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends MongoRepository<Report, String>{ 
	
	@Query("{ 'tc' : ?0 }")
	public List<Report> reportsForTestCase(String tcId);
	
	@Query("{ 'tc' : ?0 , 'user' : ?1 }")
	public List<Report> reportsForTestCase(String tcId, String user);
	
	public List<Report> findByUser(String user);
}
