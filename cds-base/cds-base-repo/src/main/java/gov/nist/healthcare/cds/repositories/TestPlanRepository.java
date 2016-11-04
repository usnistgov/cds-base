package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.TestPlan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPlanRepository extends JpaRepository<TestPlan, Long>{

}
