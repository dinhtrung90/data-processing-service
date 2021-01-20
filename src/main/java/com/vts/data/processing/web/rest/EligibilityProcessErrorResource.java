package com.vts.data.processing.web.rest;

import com.vts.data.processing.service.EligibilityProcessErrorService;
import com.vts.data.processing.web.rest.errors.BadRequestAlertException;
import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;
import com.vts.data.processing.service.dto.EligibilityProcessErrorCriteria;
import com.vts.data.processing.service.EligibilityProcessErrorQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vts.data.processing.domain.EligibilityProcessError}.
 */
@RestController
@RequestMapping("/api")
public class EligibilityProcessErrorResource {

    private final Logger log = LoggerFactory.getLogger(EligibilityProcessErrorResource.class);

    private final EligibilityProcessErrorService eligibilityProcessErrorService;

    private final EligibilityProcessErrorQueryService eligibilityProcessErrorQueryService;

    public EligibilityProcessErrorResource(EligibilityProcessErrorService eligibilityProcessErrorService, EligibilityProcessErrorQueryService eligibilityProcessErrorQueryService) {
        this.eligibilityProcessErrorService = eligibilityProcessErrorService;
        this.eligibilityProcessErrorQueryService = eligibilityProcessErrorQueryService;
    }

    /**
     * {@code GET  /eligibility-process-errors} : get all the eligibilityProcessErrors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilityProcessErrors in body.
     */
    @GetMapping("/eligibility-process-errors")
    public ResponseEntity<List<EligibilityProcessErrorDTO>> getAllEligibilityProcessErrors(EligibilityProcessErrorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EligibilityProcessErrors by criteria: {}", criteria);
        Page<EligibilityProcessErrorDTO> page = eligibilityProcessErrorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eligibility-process-errors/count} : count all the eligibilityProcessErrors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/eligibility-process-errors/count")
    public ResponseEntity<Long> countEligibilityProcessErrors(EligibilityProcessErrorCriteria criteria) {
        log.debug("REST request to count EligibilityProcessErrors by criteria: {}", criteria);
        return ResponseEntity.ok().body(eligibilityProcessErrorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /eligibility-process-errors/:id} : get the "id" eligibilityProcessError.
     *
     * @param id the id of the eligibilityProcessErrorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilityProcessErrorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibility-process-errors/{id}")
    public ResponseEntity<EligibilityProcessErrorDTO> getEligibilityProcessError(@PathVariable Long id) {
        log.debug("REST request to get EligibilityProcessError : {}", id);
        Optional<EligibilityProcessErrorDTO> eligibilityProcessErrorDTO = eligibilityProcessErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilityProcessErrorDTO);
    }
}
