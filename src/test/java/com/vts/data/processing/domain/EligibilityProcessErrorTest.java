package com.vts.data.processing.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vts.data.processing.web.rest.TestUtil;

public class EligibilityProcessErrorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibilityProcessError.class);
        EligibilityProcessError eligibilityProcessError1 = new EligibilityProcessError();
        eligibilityProcessError1.setId(1L);
        EligibilityProcessError eligibilityProcessError2 = new EligibilityProcessError();
        eligibilityProcessError2.setId(eligibilityProcessError1.getId());
        assertThat(eligibilityProcessError1).isEqualTo(eligibilityProcessError2);
        eligibilityProcessError2.setId(2L);
        assertThat(eligibilityProcessError1).isNotEqualTo(eligibilityProcessError2);
        eligibilityProcessError1.setId(null);
        assertThat(eligibilityProcessError1).isNotEqualTo(eligibilityProcessError2);
    }
}
