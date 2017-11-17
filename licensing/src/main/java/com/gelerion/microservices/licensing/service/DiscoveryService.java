package com.gelerion.microservices.licensing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate rest;

    @Autowired
    public DiscoveryService(DiscoveryClient discoveryClient, RestTemplate rest) {
        this.discoveryClient = discoveryClient;
        this.rest = rest;
    }

    public List<String> getEurekaServices() {
        List<String> services = new ArrayList<>();

        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(instance -> {
                services.add(String.format("%s:%s", serviceName, instance.getUri()));
            });
        });

        return services;
    }

}
