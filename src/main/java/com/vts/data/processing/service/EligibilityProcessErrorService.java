package com.vts.data.processing.service;

import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.vts.data.processing.domain.EligibilityProcessError}.
 */
public interface EligibilityProcessErrorService {

    /**
     * Save a eligibilityProcessError.
     *
     * @param eligibilityProcessErrorDTO the entity to save.
     * @return the persisted entity.
     */
    EligibilityProcessErrorDTO save(EligibilityProcessErrorDTO eligibilityProcessErrorDTO);

    /**
     * Get all the eligibilityProcessErrors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EligibilityProcessErrorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" eligibilityProcessError.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EligibilityProcessErrorDTO> findOne(Long id);

    /**
     * Delete the "id" eligibilityProcessError.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
