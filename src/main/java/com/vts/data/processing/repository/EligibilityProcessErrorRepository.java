package com.vts.data.processing.repository;

import com.vts.data.processing.domain.EligibilityProcessError;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EligibilityProcessError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibilityProcessErrorRepository extends JpaRepository<EligibilityProcessError, Long>, JpaSpecificationExecutor<EligibilityProcessError> {

}
