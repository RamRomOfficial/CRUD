package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.model.TestingModel;
import com.example.demo.service.TestingService;

@SpringBootTest
class TestingApplicationTests {

	@Test
	void contextLoads() {
	}
 
	@SpyBean
	private MongoTemplate mongotemplate;	
    
	@Autowired
	TestingService service;
	
	@Test
	public void getUsersTemplateTest() {

		when(mongotemplate.findAll(TestingModel.class)).thenReturn(Stream
				.of(new TestingModel("horse",10))
				.collect(Collectors.toList()));
		
		assertEquals(2,service.getAllTest().size());
		
		
		
	}
}
