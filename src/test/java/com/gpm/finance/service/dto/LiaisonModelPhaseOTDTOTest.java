package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiaisonModelPhaseOTDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiaisonModelPhaseOTDTO.class);
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO1 = new LiaisonModelPhaseOTDTO();
        liaisonModelPhaseOTDTO1.setId(1L);
        LiaisonModelPhaseOTDTO liaisonModelPhaseOTDTO2 = new LiaisonModelPhaseOTDTO();
        assertThat(liaisonModelPhaseOTDTO1).isNotEqualTo(liaisonModelPhaseOTDTO2);
        liaisonModelPhaseOTDTO2.setId(liaisonModelPhaseOTDTO1.getId());
        assertThat(liaisonModelPhaseOTDTO1).isEqualTo(liaisonModelPhaseOTDTO2);
        liaisonModelPhaseOTDTO2.setId(2L);
        assertThat(liaisonModelPhaseOTDTO1).isNotEqualTo(liaisonModelPhaseOTDTO2);
        liaisonModelPhaseOTDTO1.setId(null);
        assertThat(liaisonModelPhaseOTDTO1).isNotEqualTo(liaisonModelPhaseOTDTO2);
    }
}
