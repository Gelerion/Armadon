package com.gelerion.microservices.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {

/*	Individual services registering will take up to 30 seconds to show up in the Eureka service
	because Eureka requires three consecutive heartbeat pings from the service spaced 10 seconds
	apart before it will say the service is ready for use. Keep this in mind as you’re deploying
	and testing your own services.*/

	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}
}
