package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraisDeMissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FraisDeMission.class);
        FraisDeMission fraisDeMission1 = new FraisDeMission();
        fraisDeMission1.setId(1L);
        FraisDeMission fraisDeMission2 = new FraisDeMission();
        fraisDeMission2.setId(fraisDeMission1.getId());
        assertThat(fraisDeMission1).isEqualTo(fraisDeMission2);
        fraisDeMission2.setId(2L);
        assertThat(fraisDeMission1).isNotEqualTo(fraisDeMission2);
        fraisDeMission1.setId(null);
        assertThat(fraisDeMission1).isNotEqualTo(fraisDeMission2);
    }
}
