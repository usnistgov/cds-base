package gov.nist.healthcare.cds.repositories;

import gov.nist.healthcare.cds.domain.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	@Query("{ 'mx.$id' : ?0 , 'vx.$id' : ?1 }")
	public Product getProduct(String mvx,String cvx);
}
