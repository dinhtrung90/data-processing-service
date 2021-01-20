package com.vts.data.processing.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.vts.data.processing.domain.EligibilityProcessError;
import com.vts.data.processing.domain.*; // for static metamodels
import com.vts.data.processing.repository.EligibilityProcessErrorRepository;
import com.vts.data.processing.service.dto.EligibilityProcessErrorCriteria;
import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;
import com.vts.data.processing.service.mapper.EligibilityProcessErrorMapper;

/**
 * Service for executing complex queries for {@link EligibilityProcessError} entities in the database.
 * The main input is a {@link EligibilityProcessErrorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EligibilityProcessErrorDTO} or a {@link Page} of {@link EligibilityProcessErrorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EligibilityProcessErrorQueryService extends QueryService<EligibilityProcessError> {

    private final Logger log = LoggerFactory.getLogger(EligibilityProcessErrorQueryService.class);

    private final EligibilityProcessErrorRepository eligibilityProcessErrorRepository;

    private final EligibilityProcessErrorMapper eligibilityProcessErrorMapper;

    public EligibilityProcessErrorQueryService(EligibilityProcessErrorRepository eligibilityProcessErrorRepository, EligibilityProcessErrorMapper eligibilityProcessErrorMapper) {
        this.eligibilityProcessErrorRepository = eligibilityProcessErrorRepository;
        this.eligibilityProcessErrorMapper = eligibilityProcessErrorMapper;
    }

    /**
     * Return a {@link List} of {@link EligibilityProcessErrorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EligibilityProcessErrorDTO> findByCriteria(EligibilityProcessErrorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EligibilityProcessError> specification = createSpecification(criteria);
        return eligibilityProcessErrorMapper.toDto(eligibilityProcessErrorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EligibilityProcessErrorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EligibilityProcessErrorDTO> findByCriteria(EligibilityProcessErrorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EligibilityProcessError> specification = createSpecification(criteria);
        return eligibilityProcessErrorRepository.findAll(specification, page)
            .map(eligibilityProcessErrorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EligibilityProcessErrorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EligibilityProcessError> specification = createSpecification(criteria);
        return eligibilityProcessErrorRepository.count(specification);
    }

    /**
     * Function to convert {@link EligibilityProcessErrorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EligibilityProcessError> createSpecification(EligibilityProcessErrorCriteria criteria) {
        Specification<EligibilityProcessError> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EligibilityProcessError_.id));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EligibilityProcessError_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), EligibilityProcessError_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EligibilityProcessError_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), EligibilityProcessError_.lastModifiedDate));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceId(), EligibilityProcessError_.sourceId));
            }
            if (criteria.getRefId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefId(), EligibilityProcessError_.refId));
            }
            if (criteria.getItemCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getItemCount(), EligibilityProcessError_.itemCount));
            }
            if (criteria.getValidationError() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidationError(), EligibilityProcessError_.validationError));
            }
        }
        return specification;
    }
}
