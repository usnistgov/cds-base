package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.Vaccine;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String>{
	
    @Cacheable("vaccine")
    Vaccine findOne(String name);

    @Override
    @Cacheable("vaccine")
    @CacheEvict(value = "testcase", allEntries = true)
    List<Vaccine> findAll();

    @Override
    @CacheEvict("vaccine")
    void delete(String id);
    
    @Override
    @CachePut("vaccine")
    @CacheEvict(value = {"vaccine-name", "vaccine-name-case"})
    <S extends Vaccine> S save(S entity);

    @Override
    @CachePut("vaccine")
    <S extends Vaccine> List<S> save(Iterable<S> entities);
    
    @Cacheable("vaccine-name")
	public Vaccine findByName(String name);
    
    @Cacheable("vaccine-name-case")
	public Vaccine findByNameIgnoreCase(String name);
}
