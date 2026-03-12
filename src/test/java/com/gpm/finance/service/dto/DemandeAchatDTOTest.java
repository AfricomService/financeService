package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeAchatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeAchatDTO.class);
        DemandeAchatDTO demandeAchatDTO1 = new DemandeAchatDTO();
        demandeAchatDTO1.setId(1L);
        DemandeAchatDTO demandeAchatDTO2 = new DemandeAchatDTO();
        assertThat(demandeAchatDTO1).isNotEqualTo(demandeAchatDTO2);
        demandeAchatDTO2.setId(demandeAchatDTO1.getId());
        assertThat(demandeAchatDTO1).isEqualTo(demandeAchatDTO2);
        demandeAchatDTO2.setId(2L);
        assertThat(demandeAchatDTO1).isNotEqualTo(demandeAchatDTO2);
        demandeAchatDTO1.setId(null);
        assertThat(demandeAchatDTO1).isNotEqualTo(demandeAchatDTO2);
    }
}
