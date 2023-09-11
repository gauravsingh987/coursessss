package com.rahulshettyacademy.Courses;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.rahulshettyacademy.controller.AllCourseData;
import com.rahulshettyacademy.repository.CoursesRepository;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;


//to identify unit test case in springboot and to start the provider server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("CoursesCatalogue")
//path of pact file
//@PactFolder("pacts")
@PactBroker(url="https://om.pactflow.io/",
authentication= @PactBrokerAuth(token="kBCDD1BsNBwmz4u5v2SAiA"))


public class PactProviderDemo {
	//the provider server port will be binded to this port 
	@LocalServerPort
	public int port;
	
	@Autowired
	  CoursesRepository repository;

	
	//each interaction will be a test case from provider side , for every test ,test template will be used
	
	@TestTemplate
	//to give pact knowledge to the method
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	public void pactVerificationTest(PactVerificationContext context)
	{
		//to open pact and pull out the interactions in it
		context.verifyInteraction();
		
	}
	
	//port information is provided to the context variable	
	@BeforeEach
	public void setup(PactVerificationContext context)
	{
		
		context.setTarget(new HttpTestTarget("localhost",port));
	}
	
	//to check if the data is present in provider, if not then the test will fail
	//because of data, to avoid this an extra record is inserted in provider temporarily
	@State(value= "courses exist",action= StateChangeAction.SETUP)
	public void coursesExist()
	
	{
		
	}
	//if any record is added in setup , it will be deleted 
	@State(value= "courses exist",action= StateChangeAction.TEARDOWN)
	public void coursesExistTearDown()
	
	{
		
	}

	
}
