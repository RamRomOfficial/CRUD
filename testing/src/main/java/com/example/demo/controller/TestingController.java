package com.example.demo.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.*;
import com.example.demo.model.*;

@RequestMapping("/api/v1")
@RestController
public class TestingController {
	  private TestingService testingService;

		public TestingController(TestingService testingService) {
			System.out.println("controller class is created");
			this.testingService = testingService;
		}
		
		@PostMapping("/test")	
		public String testCreater(@RequestBody TestingModel testModel)
		{
			System.out.println("test created");
			testModel.setId(testingService.uniqueValue(123));
			return testingService.addTest(testModel);
		}
		
//		@RequestMapping(value = "/test",method=RequestMethod.POST,
//		        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},consumes={MediaType.APPLICATION_XML_VALUE})
//		public String testCreater(@RequestBody TestingModel testModel)
//		{
//			System.out.println("test created");
//			testModel.setId(testingService.uniqueValue(123));
//			return testingService.addTest(testModel);
//		}
		
		@GetMapping("/test")	
		public List<TestingModel> allTestCases()
		{
			System.out.println("all Test cases are");
			return testingService.getAllTest();
		}
		
		@PutMapping("/test")	
		public String updateTest(@RequestBody TestingModel testModel)
		{
			System.out.println("test updated");
			return testingService.updateTest(testModel);
		}
		
		@DeleteMapping("/test/{_id}")
		public String deleteTest(@PathVariable("_id") int id)
		{
			System.out.println("test deleted");
			return testingService.deleteTest(id);
		}
		
		
		
		
		
		
		
		
}
