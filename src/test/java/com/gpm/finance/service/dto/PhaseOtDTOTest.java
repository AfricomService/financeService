package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhaseOtDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhaseOtDTO.class);
        PhaseOtDTO phaseOtDTO1 = new PhaseOtDTO();
        phaseOtDTO1.setId(1L);
        PhaseOtDTO phaseOtDTO2 = new PhaseOtDTO();
        assertThat(phaseOtDTO1).isNotEqualTo(phaseOtDTO2);
        phaseOtDTO2.setId(phaseOtDTO1.getId());
        assertThat(phaseOtDTO1).isEqualTo(phaseOtDTO2);
        phaseOtDTO2.setId(2L);
        assertThat(phaseOtDTO1).isNotEqualTo(phaseOtDTO2);
        phaseOtDTO1.setId(null);
        assertThat(phaseOtDTO1).isNotEqualTo(phaseOtDTO2);
    }
}
