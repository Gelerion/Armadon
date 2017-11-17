package com.gelerion.microservices.licensing;

import com.gelerion.microservices.licensing.monitor.filter.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class LicensingApplication {
/*	Note a couple of things about the @RefreshScope annotation. First, the annotation will only reload
	the custom Spring properties you have in your application configuration. Items such as your database
	configuration that are used by Spring Data won’t be reloaded by the @RefreshScope annotation.
	To perform the refresh*/

	public static void main(String[] args) {
		SpringApplication.run(LicensingApplication.class, args);
	}

	@LoadBalanced //indicates that this RestTemplate is going to use Ribbon
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate result = new RestTemplate();
		result.getInterceptors().add(new UserContextInterceptor());
		return result;
	}


}
