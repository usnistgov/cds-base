package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Vaccine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long>{
	public Vaccine findByName(String name);
}
