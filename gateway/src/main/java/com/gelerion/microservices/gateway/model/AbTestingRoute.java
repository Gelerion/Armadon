package com.gelerion.microservices.gateway.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AbTestingRoute {
    private String serviceName;
    private String active;
    private String endpoint;
    private Integer weight;
}
