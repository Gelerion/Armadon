package com.gelerion.microservices.licensing.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import static org.springframework.http.HttpMethod.DELETE;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
         .authorizeRequests()
           .antMatchers(DELETE, "/v1/organizations/**")
                .hasRole("ADMIN")
           .anyRequest()
                .authenticated();
        // @formatter:on
    }

}
