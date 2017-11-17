package com.gelerion.microservices.licensing.domain.repository;

import com.gelerion.microservices.licensing.domain.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {

    List<License> findByOrganizationId(String organizationId);

    Optional<License> findByOrganizationIdAndId(String organizationId, String licenseId);

}
