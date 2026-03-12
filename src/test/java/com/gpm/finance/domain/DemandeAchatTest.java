package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeAchatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeAchat.class);
        DemandeAchat demandeAchat1 = new DemandeAchat();
        demandeAchat1.setId(1L);
        DemandeAchat demandeAchat2 = new DemandeAchat();
        demandeAchat2.setId(demandeAchat1.getId());
        assertThat(demandeAchat1).isEqualTo(demandeAchat2);
        demandeAchat2.setId(2L);
        assertThat(demandeAchat1).isNotEqualTo(demandeAchat2);
        demandeAchat1.setId(null);
        assertThat(demandeAchat1).isNotEqualTo(demandeAchat2);
    }
}
