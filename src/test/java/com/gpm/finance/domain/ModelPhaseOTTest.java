package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModelPhaseOTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelPhaseOT.class);
        ModelPhaseOT modelPhaseOT1 = new ModelPhaseOT();
        modelPhaseOT1.setId(1L);
        ModelPhaseOT modelPhaseOT2 = new ModelPhaseOT();
        modelPhaseOT2.setId(modelPhaseOT1.getId());
        assertThat(modelPhaseOT1).isEqualTo(modelPhaseOT2);
        modelPhaseOT2.setId(2L);
        assertThat(modelPhaseOT1).isNotEqualTo(modelPhaseOT2);
        modelPhaseOT1.setId(null);
        assertThat(modelPhaseOT1).isNotEqualTo(modelPhaseOT2);
    }
}
