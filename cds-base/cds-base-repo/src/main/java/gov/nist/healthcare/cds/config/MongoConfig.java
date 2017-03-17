package gov.nist.healthcare.cds.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
@EnableMongoRepositories(value="gov.nist.healthcare.cds")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "cdsi-db";
	}

	@Override
	public Mongo mongo() throws Exception {
		//MongoClientOptions o = MongoClientOptions.builder().socketFactory().build();
		return new MongoClient(new ServerAddress("127.0.0.1",27017));
	}

	@Override
	protected String getMappingBasePackage() {
		return "gov.nist.healthcare.cds";
	}

}