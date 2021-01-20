package com.vts.data.processing.service.impl;

import com.vts.data.processing.service.EligibilityService;
import com.vts.data.processing.domain.Eligibility;
import com.vts.data.processing.repository.EligibilityRepository;
import com.vts.data.processing.service.dto.EligibilityDTO;
import com.vts.data.processing.service.mapper.EligibilityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Eligibility}.
 */
@Service
@Transactional
public class EligibilityServiceImpl implements EligibilityService {

    private final Logger log = LoggerFactory.getLogger(EligibilityServiceImpl.class);

    private final EligibilityRepository eligibilityRepository;

    private final EligibilityMapper eligibilityMapper;

    public EligibilityServiceImpl(EligibilityRepository eligibilityRepository, EligibilityMapper eligibilityMapper) {
        this.eligibilityRepository = eligibilityRepository;
        this.eligibilityMapper = eligibilityMapper;
    }

    @Override
    public EligibilityDTO save(EligibilityDTO eligibilityDTO) {
        log.debug("Request to save Eligibility : {}", eligibilityDTO);
        Eligibility eligibility = eligibilityMapper.toEntity(eligibilityDTO);
        eligibility = eligibilityRepository.save(eligibility);
        return eligibilityMapper.toDto(eligibility);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EligibilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eligibilities");
        return eligibilityRepository.findAll(pageable)
            .map(eligibilityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EligibilityDTO> findOne(Long id) {
        log.debug("Request to get Eligibility : {}", id);
        return eligibilityRepository.findById(id)
            .map(eligibilityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eligibility : {}", id);
        eligibilityRepository.deleteById(id);
    }
}
