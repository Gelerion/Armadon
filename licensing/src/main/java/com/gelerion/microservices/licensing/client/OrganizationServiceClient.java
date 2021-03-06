package com.gelerion.microservices.licensing.client;

import com.gelerion.microservices.licensing.domain.model.Organization;
import com.gelerion.microservices.licensing.monitor.filter.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrganizationServiceClient {

    private final OAuth2RestTemplate rest;

    public OrganizationServiceClient(OAuth2RestTemplate rest) {
        this.rest = rest;
    }

    public Organization getOrganization(String organizationId) {
        log.debug(">>> In Licensing Service.getOrganization: {}. Thread Id: {}",
                UserContextHolder.getContext().correlationId(), Thread.currentThread().getId());

        ResponseEntity<Organization> response = rest.getForEntity(
                "http://organization/v1/organizations/{organizationId}",
                Organization.class,
                organizationId);

        return response.getBody();
    }

}
