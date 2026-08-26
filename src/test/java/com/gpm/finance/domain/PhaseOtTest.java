package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhaseOtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhaseOt.class);
        PhaseOt phaseOt1 = new PhaseOt();
        phaseOt1.setId(1L);
        PhaseOt phaseOt2 = new PhaseOt();
        phaseOt2.setId(phaseOt1.getId());
        assertThat(phaseOt1).isEqualTo(phaseOt2);
        phaseOt2.setId(2L);
        assertThat(phaseOt1).isNotEqualTo(phaseOt2);
        phaseOt1.setId(null);
        assertThat(phaseOt1).isNotEqualTo(phaseOt2);
    }
}
