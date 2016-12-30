package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Manufacturer;
import gov.nist.healthcare.cds.domain.VaccineMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>{
	public Manufacturer findByName(String name);
	public Manufacturer findByMvx(String mvx);
}
