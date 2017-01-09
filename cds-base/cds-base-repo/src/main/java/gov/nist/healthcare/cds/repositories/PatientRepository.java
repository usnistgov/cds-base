package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{
	
}
