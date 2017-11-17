package com.gelerion.microservices.organization.domain.repository;

import com.gelerion.microservices.organization.domain.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, String> {

    Organization findById(String organizationId);

}
