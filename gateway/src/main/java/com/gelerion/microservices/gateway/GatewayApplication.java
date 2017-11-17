package com.gelerion.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {

    /*
    The Zuul proxy server is designed by default to work on the Spring products.
    As such, Zuul will automatically use Eureka to look up services by their service
    IDs and then use Netflix Ribbon to do client-side load balancing of requests
    from within Zuul
    */

    //use /routes to see available routes, discovered by eureka
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @LoadBalanced //indicates that this RestTemplate is going to use Ribbon
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate result = new RestTemplate();
        //result.getInterceptors().add(new UserContextInterceptor());
        return result;
    }
}
