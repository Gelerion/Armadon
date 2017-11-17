package com.gelerion.microservices.organization.service;

import com.gelerion.microservices.organization.domain.model.Organization;
import com.gelerion.microservices.organization.domain.repository.OrganizationRepository;
import com.gelerion.microservices.organization.monitor.filter.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrganizationService {

    private final OrganizationRepository repository;

    @Autowired
    public OrganizationService(OrganizationRepository repository) {
        this.repository = repository;
    }

    public Iterable<Organization> getAll() {
        return repository.findAll();
    }

    public Organization getOrganization(String organizationId) {
        log.debug("OrganizationService.getOrganization() Correlation id: {}",
                UserContextHolder.getContext().correlationId());
        return repository.findById(organizationId);
    }

    public void saveOrganization(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        log.info("Saving organization {}", organization);
        repository.save(organization);
    }

    public void updateOrganization(Organization organization) {
        repository.save(organization);
    }

    public void deleteOrganization(Organization organization) {
        repository.delete(organization.getId());
    }

}
