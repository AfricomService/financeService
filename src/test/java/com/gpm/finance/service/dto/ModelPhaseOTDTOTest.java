package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModelPhaseOTDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelPhaseOTDTO.class);
        ModelPhaseOTDTO modelPhaseOTDTO1 = new ModelPhaseOTDTO();
        modelPhaseOTDTO1.setId(1L);
        ModelPhaseOTDTO modelPhaseOTDTO2 = new ModelPhaseOTDTO();
        assertThat(modelPhaseOTDTO1).isNotEqualTo(modelPhaseOTDTO2);
        modelPhaseOTDTO2.setId(modelPhaseOTDTO1.getId());
        assertThat(modelPhaseOTDTO1).isEqualTo(modelPhaseOTDTO2);
        modelPhaseOTDTO2.setId(2L);
        assertThat(modelPhaseOTDTO1).isNotEqualTo(modelPhaseOTDTO2);
        modelPhaseOTDTO1.setId(null);
        assertThat(modelPhaseOTDTO1).isNotEqualTo(modelPhaseOTDTO2);
    }
}
