package com.vts.data.processing.repository;

import com.vts.data.processing.domain.Eligibility;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Eligibility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityRepository extends JpaRepository<Eligibility, Long>, JpaSpecificationExecutor<Eligibility> {
    Optional<Eligibility> findByRefId(String refId);
}
