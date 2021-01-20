package com.vts.data.processing.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vts.data.processing.web.rest.TestUtil;

public class EligibilityProcessErrorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibilityProcessErrorDTO.class);
        EligibilityProcessErrorDTO eligibilityProcessErrorDTO1 = new EligibilityProcessErrorDTO();
        eligibilityProcessErrorDTO1.setId(1L);
        EligibilityProcessErrorDTO eligibilityProcessErrorDTO2 = new EligibilityProcessErrorDTO();
        assertThat(eligibilityProcessErrorDTO1).isNotEqualTo(eligibilityProcessErrorDTO2);
        eligibilityProcessErrorDTO2.setId(eligibilityProcessErrorDTO1.getId());
        assertThat(eligibilityProcessErrorDTO1).isEqualTo(eligibilityProcessErrorDTO2);
        eligibilityProcessErrorDTO2.setId(2L);
        assertThat(eligibilityProcessErrorDTO1).isNotEqualTo(eligibilityProcessErrorDTO2);
        eligibilityProcessErrorDTO1.setId(null);
        assertThat(eligibilityProcessErrorDTO1).isNotEqualTo(eligibilityProcessErrorDTO2);
    }
}
