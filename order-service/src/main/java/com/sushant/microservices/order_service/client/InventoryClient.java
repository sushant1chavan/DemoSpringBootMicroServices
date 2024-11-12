package com.sushant.microservices.order_service.client;

import groovy.util.logging.Slf4j;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;


//For this FeignClient we have added open feignclient dependency manually in pom.xml
//also we added property 	<spring-cloud.version>2023.0.3</spring-cloud.version> below java version property section
//then we also added dependency management section in pom.xml from spring initializer when we add feign client dependency

//since 2022, FeignClient is not used now for inter service communication ie inter service REST calls
//instead new way is implemented spring interface client (ie REST clients)
// more info at http://spring.io/projects/spring-cloud-openfeign
//@FeignClient(value="inventory", url="${inventory.url}") --- this is not required now

@Slf4j //here we are using groovy.util.logging.Slf4j
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    //@RequestMapping(method= RequestMethod.GET,value="/api/inventory") ---this is not required now
    @GetExchange("/api/inventory") //we need to create bean for this , ie for RestClient
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    default boolean fallbackMethod(String code, Integer quantitiy, Throwable throwable) {
        log.info("Cannot get inventory for skucode {}, failure reason: {}", code, throwable.getMessage());
        return false;
    }
}

//we can create RestClient by 3 ways more info at http://docs.spring.io/spring-framework/reference/integration/rest-clients.html
//1.Using RestClient - It is most preferred approach - provides much easy api for rest calls
//2.Using WebClient
//3.Using RestTemplate - Not favoured anymore -needs webflux dependency

//for RestClient to implemet we have created new Class RestClientConfig.java
