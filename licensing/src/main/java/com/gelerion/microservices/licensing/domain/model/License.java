package com.gelerion.microservices.licensing.domain.model;


import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "licenses")
public class License {
    public static final License EMPTY = new License();

    @Id
    @Column(name = "license_id", nullable = false)
    private String id;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Transient
    private String organizationName;

    @Transient
    private String contactPhone;

    @Transient
    private String contactName;

    @Transient
    private String contactEmail ;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;

    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;

    @Column(name="comment")
    private String comment;

    public License withId(String id){
        this.setId(id);
        return this;
    }

    public License withOrganizationId(String organizationId){
        this.setOrganizationId(organizationId);
        return this;
    }

    public License withProductName(String productName){
        this.setProductName(productName);
        return this;
    }

    public License withLicenseType(String licenseType){
        this.setLicenseType(licenseType);
        return this;
    }

    public License withLicenseMax(Integer licenseMax){
        this.setLicenseMax(licenseMax);
        return this;
    }

    public License withLicenseAllocated(Integer licenseAllocated){
        this.setLicenseAllocated(licenseAllocated);
        return this;
    }

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }

    public License withOrganizationName(String organizationName){
        this.setOrganizationName(organizationName);
        return this;
    }

    public License withContactName(String contactName){
        this.setContactName(contactName);
        return this;
    }

    public License withContactPhone(String contactPhone){
        this.setContactPhone(contactPhone);
        return this;
    }

    public License withContactEmail(String contactEmail){
        this.setContactEmail(contactEmail);
        return this;
    }

}
