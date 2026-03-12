package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeEspeceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeEspece.class);
        DemandeEspece demandeEspece1 = new DemandeEspece();
        demandeEspece1.setId(1L);
        DemandeEspece demandeEspece2 = new DemandeEspece();
        demandeEspece2.setId(demandeEspece1.getId());
        assertThat(demandeEspece1).isEqualTo(demandeEspece2);
        demandeEspece2.setId(2L);
        assertThat(demandeEspece1).isNotEqualTo(demandeEspece2);
        demandeEspece1.setId(null);
        assertThat(demandeEspece1).isNotEqualTo(demandeEspece2);
    }
}
