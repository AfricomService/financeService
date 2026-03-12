package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureWODTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureWODTO.class);
        FactureWODTO factureWODTO1 = new FactureWODTO();
        factureWODTO1.setId(1L);
        FactureWODTO factureWODTO2 = new FactureWODTO();
        assertThat(factureWODTO1).isNotEqualTo(factureWODTO2);
        factureWODTO2.setId(factureWODTO1.getId());
        assertThat(factureWODTO1).isEqualTo(factureWODTO2);
        factureWODTO2.setId(2L);
        assertThat(factureWODTO1).isNotEqualTo(factureWODTO2);
        factureWODTO1.setId(null);
        assertThat(factureWODTO1).isNotEqualTo(factureWODTO2);
    }
}
