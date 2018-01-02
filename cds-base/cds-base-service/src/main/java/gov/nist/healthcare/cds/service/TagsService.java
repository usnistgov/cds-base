package gov.nist.healthcare.cds.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.BSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DocumentCallbackHandler;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBObject;
import com.mongodb.MongoException;

import gov.nist.healthcare.cds.domain.TestCase;

public class TagsService {

//	@Autowired
//	MongoTemplate mTemplate;
//	
//	public List<String> tags(String tpId, String query){
//		Set<String> tags = new HashSet<>();
//		
//		BasicQuery mgQuery = new BasicQuery("{ testPlan : '"+tpId+"', tags : '.*"+query+".*' }");
//		List<TestCase> tcs = this.mTemplate.find(mgQuery, TestCase.class);
//		for(TestCase tc : tcs){
//			tags.addAll(tc.getTags());
//		}
//		this.mTemplate.executeQuery(mgQuery, "testCase", new DocumentCallbackHandler() {
//			
//			@Override
//			public void processDocument(DBObject dbObject) throws MongoException, DataAccessException {
//				// TODO Auto-generated method stub
//				BSONObject tags = dbObject.get("tags");
//				tags.
//			}
//		});
//		return null;
//	}
}
