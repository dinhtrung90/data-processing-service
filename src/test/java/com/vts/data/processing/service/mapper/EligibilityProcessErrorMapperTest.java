package com.vts.data.processing.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class EligibilityProcessErrorMapperTest {

    private EligibilityProcessErrorMapper eligibilityProcessErrorMapper;

    @BeforeEach
    public void setUp() {
        eligibilityProcessErrorMapper = new EligibilityProcessErrorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(eligibilityProcessErrorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(eligibilityProcessErrorMapper.fromId(null)).isNull();
    }
}
