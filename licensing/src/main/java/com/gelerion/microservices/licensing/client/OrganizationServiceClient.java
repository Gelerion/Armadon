package com.gelerion.microservices.licensing.client;

import com.gelerion.microservices.licensing.domain.model.Organization;
import com.gelerion.microservices.licensing.monitor.filter.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OrganizationServiceClient {

    private final RestTemplate rest;

    public OrganizationServiceClient(RestTemplate rest) {
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
