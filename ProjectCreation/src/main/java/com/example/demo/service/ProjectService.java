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

import com.example.demo.model.Counter;
import com.example.demo.model.ProjectModel;
import com.example.demo.model.RequirementModel;
import com.example.demo.resource.ProjectResource;

@Service
public class ProjectService {
private MongoTemplate mongoTemplate;
	
	@Autowired
	private MongoOperations mongoOperation;

	@Autowired
	public ProjectService(MongoTemplate mongoTemplate) {
		System.out.println("service class is created");
		this.mongoTemplate = mongoTemplate;
	}
	
	
	public String addProject(ProjectModel projectModel)
	{
		if(mongoTemplate.insert(projectModel)!=null)
		{
			return "Project Created with ID "+projectModel.getId();
		}
		else {
			return "Project Not Created";
		}
		
	}
	
	public List<ProjectModel> getAllProjects()
	{
		return   mongoTemplate.findAll(ProjectModel.class);
	}
	public ProjectModel getByProjectId(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, ProjectModel.class);
	}
	
	public String updateProject(ProjectModel projectModel,String id) {
		ProjectModel requestedProject=getByProjectId(id);
		if(projectModel.getName()!=null)
		{
			requestedProject.setName(projectModel.getName());
		}
		if(projectModel.getDescription()!=null)
		{
			requestedProject.setDescription(projectModel.getDescription());
		}
		if(projectModel.getStartDate()!=null)
		{
			requestedProject.setStartDate(projectModel.getStartDate());
		}
		if(projectModel.getEndDate()!=null)
		{
			requestedProject.setEndDate(projectModel.getEndDate());
		}
		if(projectModel.getTargetedRelease()!=null)
		{
			requestedProject.setTargetedRelease(projectModel.getTargetedRelease());
		}
		requestedProject.addUpdateDate();
		 
		mongoTemplate.save(requestedProject);
		
		return "Project "+requestedProject.getId()+" Updated";
			
		
	}

	
	public Counter uniqueValue(String key)
	{
		 Query query = new Query(Criteria.where("_id").is(key));	
		 Update update = new Update();
		  update.inc(ProjectResource.COUNTER_DOCUMENT_SEQUENCE_COLUMN, 1);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true).upsert(true);
		  Counter counter= mongoOperation.findAndModify(query, update, options, Counter.class);
		  counter.addRequirementCount();
		  
		  mongoTemplate.save(counter);

		return counter;
		
	}
	public ProjectModel setRequirementCount(ProjectModel projectModel,Counter counter)
	{
		 List<RequirementModel> requirements =projectModel.getRequirements();
		for(int reqIndex=0 ;reqIndex<projectModel.getRequirements().size();reqIndex++)
		{
			RequirementModel tempRequirement=requirements.get(reqIndex);
			tempRequirement.setId(ProjectResource.REQUIREMENT_PREFIX+String.valueOf(reqIndex+1));
			requirements.set(reqIndex,tempRequirement);
		}
		counter.setRequirementCountByIndex(counter.getSeq()-1,counter.getRequirementCounter().get(counter.getSeq()-1)+projectModel.getRequirements().size() );
		
		mongoTemplate.save(counter);

		return projectModel;
	}

	public String addRequirement(RequirementModel requirementModel, String id) {
		
		ProjectModel projectModel=getByProjectId(id);
		
		 Query query = new Query(Criteria.where("_id").is(ProjectResource.COUNTER_DOCUMENT_ID));
		 Counter counter=mongoTemplate.findOne(query, Counter.class);
		 int requirementCounterIndex=Integer.valueOf(id.substring(4));
		 int value=counter.getRequirementCounter().get(requirementCounterIndex-1);
		 value+=1;
		 counter.setRequirementCountByIndex(requirementCounterIndex-1, value);
		 requirementModel.setId(ProjectResource.REQUIREMENT_PREFIX+String.valueOf(value));
		 projectModel.addRequirements(requirementModel);
		 mongoTemplate.save(projectModel);
		 mongoTemplate.save(counter);
		 
		return "requirement added";
	}

	public String updateRequirement(RequirementModel requirementModel, String id, String rid, boolean remove) {

		ProjectModel projectModel = getByProjectId(id);
		int requirementIndex = Integer.valueOf(rid.substring(4));
		RequirementModel requestedRequirement = projectModel.getRequirements().get(requirementIndex - 1);
		if (remove) {
			requestedRequirement.setStatus("Not Required");
		} 

		if (requirementModel.getDescription() != null) {
			requestedRequirement.setDescription(requirementModel.getDescription());
		}
		projectModel.updateRequirementbyIndex(requirementIndex - 1, requestedRequirement);

		mongoTemplate.save(projectModel);

		return "Requirement Updated";
	}
	
	
}
