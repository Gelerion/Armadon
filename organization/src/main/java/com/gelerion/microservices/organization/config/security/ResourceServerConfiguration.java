package com.gelerion.microservices.organization.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
         .authorizeRequests()
           .antMatchers(DELETE, "/v1/organizations/**")
                .hasRole("ADMIN")
           .antMatchers(GET,"/mappings/**")
                .permitAll()
           .anyRequest()
                .authenticated();
        // @formatter:on
    }
}


//    @Override
//    void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            // allow anonymous access to health check endpoint
//            .antMatchers("/manage/health").permitAll()
//            // everything else requires authentication
//            .anyRequest().authenticated()
//    }