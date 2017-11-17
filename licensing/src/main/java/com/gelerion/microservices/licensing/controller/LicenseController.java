package com.gelerion.microservices.licensing.controller;

import com.gelerion.microservices.licensing.domain.model.License;
import com.gelerion.microservices.licensing.monitor.filter.UserContextHolder;
import com.gelerion.microservices.licensing.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/v1/organizations/{organizationId}/licenses")
@Slf4j
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable String organizationId,
                               @PathVariable String licenseId) {
        log.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().correlationId());
        return licenseService.getLicense(licenseId, organizationId);
    }

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicensesByOrg(organizationId);
    }

    @PutMapping("{licenseId}")
    public void updateLicenses(@PathVariable("licenseId") String licenseId,
                               @RequestBody License license) {
        licenseService.updateLicense(license);
    }

    @DeleteMapping("{licenseId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteLicenses(@PathVariable("licenseId") String licenseId,
                               @RequestBody License license) {
        licenseService.deleteLicense(license);
    }
}
