package com.sushant.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
//VC0YgIw9KSHETubmUhmcORRML3WOVYfx
//vimp for circuit breaker
//	There is library available for circuit breaker resilience4j
//spring cloud circuitbreaker helps to implement this resilience4j library
//we need to add two dependency in pom.xml
//1.spring-cloud-starter-circuitbreaker-resilience4j - circuit breaker
//2.spring-boot-starter-actuator - to monitor services
//after adding dependecies we need to add properties in application.properties file

//There are two ways to configure cirucit breaker
//1. if the service is unavailable then we can wait for the number of attempts to call this service and after certain number of calls
//if service is still unavailable then we can open the circuit
//2. if the service is taking long time than what we configured and expected
//then the circuit will again open
