package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestPlan;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestPlanRepository extends JpaRepository<TestPlan, Long>{ 
	
    @Cacheable("testplans")
    <S extends TestPlan> S findOne(Long id);

    @Override
    @Cacheable("testplans")
    @CacheEvict(value = "testplans", allEntries = true)
    List<TestPlan> findAll();

    @Override
    @CachePut(value="testplans")
    @CacheEvict("testplans-user")
    <S extends TestPlan> S save(S entity);

    @Override
    @CacheEvict("testplans")
    void delete(Long id);
    
    
    @Cacheable("testplans-user")
	public List<TestPlan> findByUser(String user);
}
