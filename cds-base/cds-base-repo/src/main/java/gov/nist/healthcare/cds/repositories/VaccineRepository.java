package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String>{
	public Vaccine findByName(String name);
	public Vaccine findByNameIgnoreCase(String name);
}
