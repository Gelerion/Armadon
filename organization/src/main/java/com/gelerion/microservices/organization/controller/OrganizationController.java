package com.gelerion.microservices.organization.controller;

import com.gelerion.microservices.organization.domain.model.Organization;
import com.gelerion.microservices.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("v1/organizations")
public class OrganizationController {

    private final OrganizationService orgService;

    @Autowired
    public OrganizationController(OrganizationService orgService) {
        this.orgService = orgService;
    }

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        log.debug("Looking up data for organization {}", organizationId);
        return orgService.getOrganization(organizationId);
    }

    @GetMapping("/")
    public Iterable<Organization> getAllOrganization() {
        return orgService.getAll();
    }

    @PutMapping("/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String organizationId,
                                   @RequestBody Organization organization) {
        orgService.updateOrganization(organization);
    }

    @PostMapping("/")
    public void saveOrganization(@RequestBody Organization organization) {
        orgService.saveOrganization(organization);
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteOrganization(@PathVariable String organizationId,
                                   @RequestBody  Organization organization) {
        orgService.deleteOrganization(organization);
    }
}
