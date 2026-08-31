package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeAutreResponsableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommandeAutreResponsable.class);
        BonCommandeAutreResponsable bonCommandeAutreResponsable1 = new BonCommandeAutreResponsable();
        bonCommandeAutreResponsable1.setId(1L);
        BonCommandeAutreResponsable bonCommandeAutreResponsable2 = new BonCommandeAutreResponsable();
        bonCommandeAutreResponsable2.setId(bonCommandeAutreResponsable1.getId());
        assertThat(bonCommandeAutreResponsable1).isEqualTo(bonCommandeAutreResponsable2);
        bonCommandeAutreResponsable2.setId(2L);
        assertThat(bonCommandeAutreResponsable1).isNotEqualTo(bonCommandeAutreResponsable2);
        bonCommandeAutreResponsable1.setId(null);
        assertThat(bonCommandeAutreResponsable1).isNotEqualTo(bonCommandeAutreResponsable2);
    }
}
