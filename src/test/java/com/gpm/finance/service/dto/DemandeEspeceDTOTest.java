package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeEspeceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeEspeceDTO.class);
        DemandeEspeceDTO demandeEspeceDTO1 = new DemandeEspeceDTO();
        demandeEspeceDTO1.setId(1L);
        DemandeEspeceDTO demandeEspeceDTO2 = new DemandeEspeceDTO();
        assertThat(demandeEspeceDTO1).isNotEqualTo(demandeEspeceDTO2);
        demandeEspeceDTO2.setId(demandeEspeceDTO1.getId());
        assertThat(demandeEspeceDTO1).isEqualTo(demandeEspeceDTO2);
        demandeEspeceDTO2.setId(2L);
        assertThat(demandeEspeceDTO1).isNotEqualTo(demandeEspeceDTO2);
        demandeEspeceDTO1.setId(null);
        assertThat(demandeEspeceDTO1).isNotEqualTo(demandeEspeceDTO2);
    }
}
