package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FactureWOTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FactureWO.class);
        FactureWO factureWO1 = new FactureWO();
        factureWO1.setId(1L);
        FactureWO factureWO2 = new FactureWO();
        factureWO2.setId(factureWO1.getId());
        assertThat(factureWO1).isEqualTo(factureWO2);
        factureWO2.setId(2L);
        assertThat(factureWO1).isNotEqualTo(factureWO2);
        factureWO1.setId(null);
        assertThat(factureWO1).isNotEqualTo(factureWO2);
    }
}
