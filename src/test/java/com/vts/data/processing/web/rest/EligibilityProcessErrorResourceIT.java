package com.vts.data.processing.web.rest;

import com.vts.data.processing.RedisTestContainerExtension;
import com.vts.data.processing.DataProcessingApp;
import com.vts.data.processing.config.TestSecurityConfiguration;
import com.vts.data.processing.domain.EligibilityProcessError;
import com.vts.data.processing.repository.EligibilityProcessErrorRepository;
import com.vts.data.processing.service.EligibilityProcessErrorService;
import com.vts.data.processing.service.dto.EligibilityProcessErrorDTO;
import com.vts.data.processing.service.mapper.EligibilityProcessErrorMapper;
import com.vts.data.processing.service.dto.EligibilityProcessErrorCriteria;
import com.vts.data.processing.service.EligibilityProcessErrorQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EligibilityProcessErrorResource} REST controller.
 */
@SpringBootTest(classes = { DataProcessingApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class EligibilityProcessErrorResourceIT {

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REF_ID = "AAAAAAAAAA";
    private static final String UPDATED_REF_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ITEM_COUNT = 1;
    private static final Integer UPDATED_ITEM_COUNT = 2;
    private static final Integer SMALLER_ITEM_COUNT = 1 - 1;

    private static final String DEFAULT_VALIDATION_ERROR = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_ERROR = "BBBBBBBBBB";

    @Autowired
    private EligibilityProcessErrorRepository eligibilityProcessErrorRepository;

    @Autowired
    private EligibilityProcessErrorMapper eligibilityProcessErrorMapper;

    @Autowired
    private EligibilityProcessErrorService eligibilityProcessErrorService;

    @Autowired
    private EligibilityProcessErrorQueryService eligibilityProcessErrorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibilityProcessErrorMockMvc;

    private EligibilityProcessError eligibilityProcessError;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibilityProcessError createEntity(EntityManager em) {
        EligibilityProcessError eligibilityProcessError = new EligibilityProcessError()
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE)
            .sourceId(DEFAULT_SOURCE_ID)
            .refId(DEFAULT_REF_ID)
            .itemCount(DEFAULT_ITEM_COUNT)
            .validationError(DEFAULT_VALIDATION_ERROR);
        return eligibilityProcessError;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibilityProcessError createUpdatedEntity(EntityManager em) {
        EligibilityProcessError eligibilityProcessError = new EligibilityProcessError()
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE)
            .sourceId(UPDATED_SOURCE_ID)
            .refId(UPDATED_REF_ID)
            .itemCount(UPDATED_ITEM_COUNT)
            .validationError(UPDATED_VALIDATION_ERROR);
        return eligibilityProcessError;
    }

    @BeforeEach
    public void initTest() {
        eligibilityProcessError = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrors() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibilityProcessError.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].refId").value(hasItem(DEFAULT_REF_ID)))
            .andExpect(jsonPath("$.[*].itemCount").value(hasItem(DEFAULT_ITEM_COUNT)))
            .andExpect(jsonPath("$.[*].validationError").value(hasItem(DEFAULT_VALIDATION_ERROR)));
    }
    
    @Test
    @Transactional
    public void getEligibilityProcessError() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get the eligibilityProcessError
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors/{id}", eligibilityProcessError.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibilityProcessError.getId().intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.sourceId").value(DEFAULT_SOURCE_ID))
            .andExpect(jsonPath("$.refId").value(DEFAULT_REF_ID))
            .andExpect(jsonPath("$.itemCount").value(DEFAULT_ITEM_COUNT))
            .andExpect(jsonPath("$.validationError").value(DEFAULT_VALIDATION_ERROR));
    }


    @Test
    @Transactional
    public void getEligibilityProcessErrorsByIdFiltering() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        Long id = eligibilityProcessError.getId();

        defaultEligibilityProcessErrorShouldBeFound("id.equals=" + id);
        defaultEligibilityProcessErrorShouldNotBeFound("id.notEquals=" + id);

        defaultEligibilityProcessErrorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEligibilityProcessErrorShouldNotBeFound("id.greaterThan=" + id);

        defaultEligibilityProcessErrorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEligibilityProcessErrorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy equals to DEFAULT_CREATED_BY
        defaultEligibilityProcessErrorShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the eligibilityProcessErrorList where createdBy equals to UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy not equals to DEFAULT_CREATED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the eligibilityProcessErrorList where createdBy not equals to UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the eligibilityProcessErrorList where createdBy equals to UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy is not null
        defaultEligibilityProcessErrorShouldBeFound("createdBy.specified=true");

        // Get all the eligibilityProcessErrorList where createdBy is null
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy contains DEFAULT_CREATED_BY
        defaultEligibilityProcessErrorShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the eligibilityProcessErrorList where createdBy contains UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the eligibilityProcessErrorList where createdBy does not contain UPDATED_CREATED_BY
        defaultEligibilityProcessErrorShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdDate equals to DEFAULT_CREATED_DATE
        defaultEligibilityProcessErrorShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the eligibilityProcessErrorList where createdDate equals to UPDATED_CREATED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the eligibilityProcessErrorList where createdDate not equals to UPDATED_CREATED_DATE
        defaultEligibilityProcessErrorShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultEligibilityProcessErrorShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the eligibilityProcessErrorList where createdDate equals to UPDATED_CREATED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where createdDate is not null
        defaultEligibilityProcessErrorShouldBeFound("createdDate.specified=true");

        // Get all the eligibilityProcessErrorList where createdDate is null
        defaultEligibilityProcessErrorShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the eligibilityProcessErrorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the eligibilityProcessErrorList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the eligibilityProcessErrorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy is not null
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.specified=true");

        // Get all the eligibilityProcessErrorList where lastModifiedBy is null
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the eligibilityProcessErrorList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the eligibilityProcessErrorList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the eligibilityProcessErrorList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the eligibilityProcessErrorList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the eligibilityProcessErrorList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where lastModifiedDate is not null
        defaultEligibilityProcessErrorShouldBeFound("lastModifiedDate.specified=true");

        // Get all the eligibilityProcessErrorList where lastModifiedDate is null
        defaultEligibilityProcessErrorShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId equals to DEFAULT_SOURCE_ID
        defaultEligibilityProcessErrorShouldBeFound("sourceId.equals=" + DEFAULT_SOURCE_ID);

        // Get all the eligibilityProcessErrorList where sourceId equals to UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.equals=" + UPDATED_SOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId not equals to DEFAULT_SOURCE_ID
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.notEquals=" + DEFAULT_SOURCE_ID);

        // Get all the eligibilityProcessErrorList where sourceId not equals to UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldBeFound("sourceId.notEquals=" + UPDATED_SOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId in DEFAULT_SOURCE_ID or UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldBeFound("sourceId.in=" + DEFAULT_SOURCE_ID + "," + UPDATED_SOURCE_ID);

        // Get all the eligibilityProcessErrorList where sourceId equals to UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.in=" + UPDATED_SOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId is not null
        defaultEligibilityProcessErrorShouldBeFound("sourceId.specified=true");

        // Get all the eligibilityProcessErrorList where sourceId is null
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId contains DEFAULT_SOURCE_ID
        defaultEligibilityProcessErrorShouldBeFound("sourceId.contains=" + DEFAULT_SOURCE_ID);

        // Get all the eligibilityProcessErrorList where sourceId contains UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.contains=" + UPDATED_SOURCE_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsBySourceIdNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where sourceId does not contain DEFAULT_SOURCE_ID
        defaultEligibilityProcessErrorShouldNotBeFound("sourceId.doesNotContain=" + DEFAULT_SOURCE_ID);

        // Get all the eligibilityProcessErrorList where sourceId does not contain UPDATED_SOURCE_ID
        defaultEligibilityProcessErrorShouldBeFound("sourceId.doesNotContain=" + UPDATED_SOURCE_ID);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId equals to DEFAULT_REF_ID
        defaultEligibilityProcessErrorShouldBeFound("refId.equals=" + DEFAULT_REF_ID);

        // Get all the eligibilityProcessErrorList where refId equals to UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldNotBeFound("refId.equals=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId not equals to DEFAULT_REF_ID
        defaultEligibilityProcessErrorShouldNotBeFound("refId.notEquals=" + DEFAULT_REF_ID);

        // Get all the eligibilityProcessErrorList where refId not equals to UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldBeFound("refId.notEquals=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId in DEFAULT_REF_ID or UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldBeFound("refId.in=" + DEFAULT_REF_ID + "," + UPDATED_REF_ID);

        // Get all the eligibilityProcessErrorList where refId equals to UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldNotBeFound("refId.in=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId is not null
        defaultEligibilityProcessErrorShouldBeFound("refId.specified=true");

        // Get all the eligibilityProcessErrorList where refId is null
        defaultEligibilityProcessErrorShouldNotBeFound("refId.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId contains DEFAULT_REF_ID
        defaultEligibilityProcessErrorShouldBeFound("refId.contains=" + DEFAULT_REF_ID);

        // Get all the eligibilityProcessErrorList where refId contains UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldNotBeFound("refId.contains=" + UPDATED_REF_ID);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByRefIdNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where refId does not contain DEFAULT_REF_ID
        defaultEligibilityProcessErrorShouldNotBeFound("refId.doesNotContain=" + DEFAULT_REF_ID);

        // Get all the eligibilityProcessErrorList where refId does not contain UPDATED_REF_ID
        defaultEligibilityProcessErrorShouldBeFound("refId.doesNotContain=" + UPDATED_REF_ID);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount equals to DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.equals=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount equals to UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.equals=" + UPDATED_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount not equals to DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.notEquals=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount not equals to UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.notEquals=" + UPDATED_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount in DEFAULT_ITEM_COUNT or UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.in=" + DEFAULT_ITEM_COUNT + "," + UPDATED_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount equals to UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.in=" + UPDATED_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount is not null
        defaultEligibilityProcessErrorShouldBeFound("itemCount.specified=true");

        // Get all the eligibilityProcessErrorList where itemCount is null
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount is greater than or equal to DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.greaterThanOrEqual=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount is greater than or equal to UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.greaterThanOrEqual=" + UPDATED_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount is less than or equal to DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.lessThanOrEqual=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount is less than or equal to SMALLER_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.lessThanOrEqual=" + SMALLER_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsLessThanSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount is less than DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.lessThan=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount is less than UPDATED_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.lessThan=" + UPDATED_ITEM_COUNT);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByItemCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where itemCount is greater than DEFAULT_ITEM_COUNT
        defaultEligibilityProcessErrorShouldNotBeFound("itemCount.greaterThan=" + DEFAULT_ITEM_COUNT);

        // Get all the eligibilityProcessErrorList where itemCount is greater than SMALLER_ITEM_COUNT
        defaultEligibilityProcessErrorShouldBeFound("itemCount.greaterThan=" + SMALLER_ITEM_COUNT);
    }


    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError equals to DEFAULT_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldBeFound("validationError.equals=" + DEFAULT_VALIDATION_ERROR);

        // Get all the eligibilityProcessErrorList where validationError equals to UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.equals=" + UPDATED_VALIDATION_ERROR);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError not equals to DEFAULT_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.notEquals=" + DEFAULT_VALIDATION_ERROR);

        // Get all the eligibilityProcessErrorList where validationError not equals to UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldBeFound("validationError.notEquals=" + UPDATED_VALIDATION_ERROR);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError in DEFAULT_VALIDATION_ERROR or UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldBeFound("validationError.in=" + DEFAULT_VALIDATION_ERROR + "," + UPDATED_VALIDATION_ERROR);

        // Get all the eligibilityProcessErrorList where validationError equals to UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.in=" + UPDATED_VALIDATION_ERROR);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError is not null
        defaultEligibilityProcessErrorShouldBeFound("validationError.specified=true");

        // Get all the eligibilityProcessErrorList where validationError is null
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.specified=false");
    }
                @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError contains DEFAULT_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldBeFound("validationError.contains=" + DEFAULT_VALIDATION_ERROR);

        // Get all the eligibilityProcessErrorList where validationError contains UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.contains=" + UPDATED_VALIDATION_ERROR);
    }

    @Test
    @Transactional
    public void getAllEligibilityProcessErrorsByValidationErrorNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityProcessErrorRepository.saveAndFlush(eligibilityProcessError);

        // Get all the eligibilityProcessErrorList where validationError does not contain DEFAULT_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldNotBeFound("validationError.doesNotContain=" + DEFAULT_VALIDATION_ERROR);

        // Get all the eligibilityProcessErrorList where validationError does not contain UPDATED_VALIDATION_ERROR
        defaultEligibilityProcessErrorShouldBeFound("validationError.doesNotContain=" + UPDATED_VALIDATION_ERROR);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEligibilityProcessErrorShouldBeFound(String filter) throws Exception {
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibilityProcessError.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceId").value(hasItem(DEFAULT_SOURCE_ID)))
            .andExpect(jsonPath("$.[*].refId").value(hasItem(DEFAULT_REF_ID)))
            .andExpect(jsonPath("$.[*].itemCount").value(hasItem(DEFAULT_ITEM_COUNT)))
            .andExpect(jsonPath("$.[*].validationError").value(hasItem(DEFAULT_VALIDATION_ERROR)));

        // Check, that the count call also returns 1
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEligibilityProcessErrorShouldNotBeFound(String filter) throws Exception {
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEligibilityProcessError() throws Exception {
        // Get the eligibilityProcessError
        restEligibilityProcessErrorMockMvc.perform(get("/api/eligibility-process-errors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
