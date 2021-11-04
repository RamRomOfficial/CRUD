package com.example.demo.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "studentProfile")
public class TestingModel {

	public TestingModel(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	private String name;
    private int  id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TestingModel() {
		super();
		System.out.println("Object created");
	}
	

}
