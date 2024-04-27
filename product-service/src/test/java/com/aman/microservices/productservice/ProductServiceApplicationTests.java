package com.aman.microservices.productservice;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	@ServiceConnection
	static  MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port = port;
	}
	static {
		mongoDBContainer.start();
	}
	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				    "name": "iphone 14" ,
				    "description" :  "this is test for iphone 14" ,
				    "price" : 1000
				}""";

		RestAssured.given().
				contentType("application/json").body(requestBody)
				.when().post("/api/product")
				.then()
				.statusCode(201)
				.body("id", org.hamcrest.Matchers.notNullValue())
				.body("name",org.hamcrest.Matchers.equalTo("iphone 14"))
				.body("description",org.hamcrest.Matchers.equalTo("this is test for iphone 14"))
				.body("price",org.hamcrest.Matchers.equalTo(1000));
	}

}
