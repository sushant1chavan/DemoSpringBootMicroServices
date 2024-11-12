package com.sushant.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

//we are using Testcontainers for testing , goto website for more info
//in short provides support for docker , kafka , mongodb etc vimp
//below import was added automatically as we have added the testcontainer dependency while creating this project
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	//here we are initializing mongo db container , using docker compose file data here
	@ServiceConnection 	//to assign the mongodb uri and other details for monogdb form properties file dynamically we are adding @ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

	@LocalServerPort
	private Integer port;

	//to make rest calls from this integration test we have added rest-assured dependency manually in pom.xml file.
	@BeforeEach
	void setup(){
		RestAssured.baseURI ="http://localhost";//base uri of our application
		//as we are using dynamic port we are using LocalServerPort
		//but this is not working , so that's why added (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) at class level with @SpringBootTest annotation
		RestAssured.port =port;
	}

	//to start our mongo db container before running test we are writing this static block
	static{
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct(){
		//below we are using multiline String feature
		//also below json was copied from the postman request which we sent earlier
		String requestBody = """
				{
				    "name":"iPhone 16",
				    "description":"Latest Iphone",
				    "price":20000
				}
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name",Matchers.equalTo("iPhone 16"))
				.body("description",Matchers.equalTo("Latest Iphone"))
				.body("price",Matchers.equalTo(20000));
	}
}
