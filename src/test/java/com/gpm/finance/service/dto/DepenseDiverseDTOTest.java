package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepenseDiverseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepenseDiverseDTO.class);
        DepenseDiverseDTO depenseDiverseDTO1 = new DepenseDiverseDTO();
        depenseDiverseDTO1.setId(1L);
        DepenseDiverseDTO depenseDiverseDTO2 = new DepenseDiverseDTO();
        assertThat(depenseDiverseDTO1).isNotEqualTo(depenseDiverseDTO2);
        depenseDiverseDTO2.setId(depenseDiverseDTO1.getId());
        assertThat(depenseDiverseDTO1).isEqualTo(depenseDiverseDTO2);
        depenseDiverseDTO2.setId(2L);
        assertThat(depenseDiverseDTO1).isNotEqualTo(depenseDiverseDTO2);
        depenseDiverseDTO1.setId(null);
        assertThat(depenseDiverseDTO1).isNotEqualTo(depenseDiverseDTO2);
    }
}
