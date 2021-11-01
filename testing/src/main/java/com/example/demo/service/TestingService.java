package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.model.*;

@Service
public class TestingService {
     
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	public TestingService(MongoTemplate mongoTemplate) {
		System.out.println("service class is created");
		this.mongoTemplate = mongoTemplate;
	}
	
	public String addTest(TestingModel testModel)
	{
		TestingModel insertedTest=mongoTemplate.insert(testModel);
		System.out.println("Inserted");
		return " Inserted";
		
	}
	public List<TestingModel> getAllTest()
	{
		return  mongoTemplate.findAll(TestingModel.class);
	}
	
	public String deleteTest(double id)
	{
		Query q=new Query();
		q.addCriteria(Criteria.where("_id").is(id));
		 mongoTemplate.remove(q, TestingModel.class);
		 return "deleted";
		
	}
	public String updateTest(TestingModel testModel)
	{
		Query q=new Query();
		q.addCriteria(Criteria.where("_id").is(testModel.getId()));
		TestingModel testTemp=mongoTemplate.findOne(q, TestingModel.class);
		testTemp.setName(testModel.getName());
		mongoTemplate.save(testTemp);
		return "updated";
		
	}
	
	public int uniqueValue(int key)
	{
		 Query query = new Query(Criteria.where("_id").is(key));	
		 Update update = new Update();
		  update.inc("seq", 1);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true).upsert(true);
		  Counter counter= mongoOperation.findAndModify(query, update, options, Counter.class);
		  


		
		return counter.getSeq();
	}
	
}
