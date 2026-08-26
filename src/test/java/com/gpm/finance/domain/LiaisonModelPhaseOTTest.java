package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LiaisonModelPhaseOTTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LiaisonModelPhaseOT.class);
        LiaisonModelPhaseOT liaisonModelPhaseOT1 = new LiaisonModelPhaseOT();
        liaisonModelPhaseOT1.setId(1L);
        LiaisonModelPhaseOT liaisonModelPhaseOT2 = new LiaisonModelPhaseOT();
        liaisonModelPhaseOT2.setId(liaisonModelPhaseOT1.getId());
        assertThat(liaisonModelPhaseOT1).isEqualTo(liaisonModelPhaseOT2);
        liaisonModelPhaseOT2.setId(2L);
        assertThat(liaisonModelPhaseOT1).isNotEqualTo(liaisonModelPhaseOT2);
        liaisonModelPhaseOT1.setId(null);
        assertThat(liaisonModelPhaseOT1).isNotEqualTo(liaisonModelPhaseOT2);
    }
}
