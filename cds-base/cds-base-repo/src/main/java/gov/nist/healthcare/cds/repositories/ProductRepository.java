package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String>{

	@Query("select p from Product p where p.mx.mvx = :mvx and p.vx.id = :cvx")
	public Product getProduct(@Param("mvx") String mvx,@Param("cvx") String cvx);
}
