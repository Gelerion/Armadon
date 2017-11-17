package com.gelerion.microservices.licensing.domain.model;

import lombok.Data;

@Data
public class Organization {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
