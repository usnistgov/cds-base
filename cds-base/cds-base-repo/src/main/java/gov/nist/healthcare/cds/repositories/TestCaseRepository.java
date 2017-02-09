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
public interface TestCaseRepository extends JpaRepository<TestCase, Long>{
	
    @Cacheable("testcases")
    <S extends TestCase> S findOne(Long id);

    @Override
    @Cacheable("testcases")
    @CacheEvict(value = "testcases", allEntries = true)
    List<TestCase> findAll();

    @Override
    @CacheEvict("testcases")
    void delete(Long id);
    
    @Override
    @CachePut("testcases")
    <S extends TestCase> S save(S entity);

    @Override
    @CachePut("testcases")
    <S extends TestCase> List<S> save(Iterable<S> entities);
    
}
