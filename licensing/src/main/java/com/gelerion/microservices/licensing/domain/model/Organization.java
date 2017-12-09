package com.gelerion.microservices.licensing.domain.model;

import lombok.Data;

@Data
public class Organization {
    public static Organization EMPTY = new Organization();

    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
