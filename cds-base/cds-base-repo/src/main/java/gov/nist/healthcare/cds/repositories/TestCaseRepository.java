package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.TestCase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long>{

}
