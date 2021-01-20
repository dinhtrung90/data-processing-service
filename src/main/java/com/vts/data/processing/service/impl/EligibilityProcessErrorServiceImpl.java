package com.vts.data.processing.service.impl;

import com.vts.data.processing.service.EligibilityProcessErrorService;
import com.vts.data.processing.domain.EligibilityProcessError;
import com.vts.data.processing.repository.EligibilityProcessErrorRepository;
import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;
import com.vts.data.processing.service.mapper.EligibilityProcessErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EligibilityProcessError}.
 */
@Service
@Transactional
public class EligibilityProcessErrorServiceImpl implements EligibilityProcessErrorService {

    private final Logger log = LoggerFactory.getLogger(EligibilityProcessErrorServiceImpl.class);

    private final EligibilityProcessErrorRepository eligibilityProcessErrorRepository;

    private final EligibilityProcessErrorMapper eligibilityProcessErrorMapper;

    public EligibilityProcessErrorServiceImpl(EligibilityProcessErrorRepository eligibilityProcessErrorRepository, EligibilityProcessErrorMapper eligibilityProcessErrorMapper) {
        this.eligibilityProcessErrorRepository = eligibilityProcessErrorRepository;
        this.eligibilityProcessErrorMapper = eligibilityProcessErrorMapper;
    }

    /**
     * Save a eligibilityProcessError.
     *
     * @param eligibilityProcessErrorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EligibilityProcessErrorDTO save(EligibilityProcessErrorDTO eligibilityProcessErrorDTO) {
        log.debug("Request to save EligibilityProcessError : {}", eligibilityProcessErrorDTO);
        EligibilityProcessError eligibilityProcessError = eligibilityProcessErrorMapper.toEntity(eligibilityProcessErrorDTO);
        eligibilityProcessError = eligibilityProcessErrorRepository.save(eligibilityProcessError);
        return eligibilityProcessErrorMapper.toDto(eligibilityProcessError);
    }

    /**
     * Get all the eligibilityProcessErrors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EligibilityProcessErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EligibilityProcessErrors");
        return eligibilityProcessErrorRepository.findAll(pageable)
            .map(eligibilityProcessErrorMapper::toDto);
    }


    /**
     * Get one eligibilityProcessError by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EligibilityProcessErrorDTO> findOne(Long id) {
        log.debug("Request to get EligibilityProcessError : {}", id);
        return eligibilityProcessErrorRepository.findById(id)
            .map(eligibilityProcessErrorMapper::toDto);
    }

    /**
     * Delete the eligibilityProcessError by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EligibilityProcessError : {}", id);
        eligibilityProcessErrorRepository.deleteById(id);
    }
}
