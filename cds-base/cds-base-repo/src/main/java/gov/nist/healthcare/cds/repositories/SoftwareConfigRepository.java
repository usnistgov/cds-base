package gov.nist.healthcare.cds.repositories;

import java.util.List;

import gov.nist.healthcare.cds.domain.SoftwareConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareConfigRepository extends JpaRepository<SoftwareConfig, Long>{

	public List<SoftwareConfig> findByUser(String user);
    
}
