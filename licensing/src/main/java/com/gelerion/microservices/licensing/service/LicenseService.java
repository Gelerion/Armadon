package com.gelerion.microservices.licensing.service;

import com.gelerion.microservices.licensing.client.OrganizationServiceClient;
import com.gelerion.microservices.licensing.config.ServiceConfig;
import com.gelerion.microservices.licensing.domain.model.License;
import com.gelerion.microservices.licensing.domain.model.Organization;
import com.gelerion.microservices.licensing.domain.repository.LicenseRepository;
import com.gelerion.microservices.licensing.monitor.filter.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LicenseService {

    private final OrganizationServiceClient organizationClient;
    private final LicenseRepository repository;
    private final ServiceConfig config;

    public LicenseService(OrganizationServiceClient organizationClient,
                          LicenseRepository repository, ServiceConfig config) {
        this.organizationClient = organizationClient;
        this.repository = repository;
        this.config = config;
    }

    public License getLicense(String licenseId, String organizationId) {
        License license = repository.findByOrganizationIdAndId(organizationId, licenseId)
                .orElse(License.EMPTY);

        Organization organization = getOrganization(organizationId);

        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    @HystrixCommand
    private Organization getOrganization(String organizationId) {
        return Optional.ofNullable(organizationClient.getOrganization(organizationId))
                .orElse(Organization.EMPTY);
    }

    //thread pool size
    //(requests per second at peak when the service is healthy * 99th percentile latency in seconds) + small amount of extra threads for overhead

    //! Note new thread is going to be created, so parent ThreadLocal variable won't be propagated
    //Fortunately, Hystrix and Spring Cloud offer a mechanism to propagate the parent thread’s context to
    //threads managed by the Hystrix Thread pool. This mechanism is called a HystrixConcurrencyStrategy
    @HystrixCommand(
            fallbackMethod = "buildFallbackLicenseList",
            threadPoolKey = "licenseByOrgThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            },
            commandProperties = {
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
            }
    )
    public List<License> getLicensesByOrg(String organizationId) {
        log.debug("LicenseService.getLicensesByOrg() Correlation id: {}", UserContextHolder.getContext().correlationId());
        randomlyRunLong();
        return repository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackList = new ArrayList<>();
        License license = License.builder()
                .id("0000000-00-00000")
                .organizationId(organizationId)
                .productName("Sorry no licensing information currently available")
                .build();
        fallbackList.add(license);
        return fallbackList;
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveLicense(License license) {
        license.withId(UUID.randomUUID().toString());

        repository.save(license);
    }

    public void updateLicense(License license) {
        repository.save(license);
    }

    public void deleteLicense(License license) {
        repository.delete(license.getId());
    }
}
