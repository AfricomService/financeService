package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraisDeMissionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraisDeMissionDTO.class);
        FraisDeMissionDTO fraisDeMissionDTO1 = new FraisDeMissionDTO();
        fraisDeMissionDTO1.setId(1L);
        FraisDeMissionDTO fraisDeMissionDTO2 = new FraisDeMissionDTO();
        assertThat(fraisDeMissionDTO1).isNotEqualTo(fraisDeMissionDTO2);
        fraisDeMissionDTO2.setId(fraisDeMissionDTO1.getId());
        assertThat(fraisDeMissionDTO1).isEqualTo(fraisDeMissionDTO2);
        fraisDeMissionDTO2.setId(2L);
        assertThat(fraisDeMissionDTO1).isNotEqualTo(fraisDeMissionDTO2);
        fraisDeMissionDTO1.setId(null);
        assertThat(fraisDeMissionDTO1).isNotEqualTo(fraisDeMissionDTO2);
    }
}
