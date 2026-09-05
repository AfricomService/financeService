package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeAutreResponsableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommandeAutreResponsableDTO.class);
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO1 = new BonCommandeAutreResponsableDTO();
        bonCommandeAutreResponsableDTO1.setId(1L);
        BonCommandeAutreResponsableDTO bonCommandeAutreResponsableDTO2 = new BonCommandeAutreResponsableDTO();
        assertThat(bonCommandeAutreResponsableDTO1).isNotEqualTo(bonCommandeAutreResponsableDTO2);
        bonCommandeAutreResponsableDTO2.setId(bonCommandeAutreResponsableDTO1.getId());
        assertThat(bonCommandeAutreResponsableDTO1).isEqualTo(bonCommandeAutreResponsableDTO2);
        bonCommandeAutreResponsableDTO2.setId(2L);
        assertThat(bonCommandeAutreResponsableDTO1).isNotEqualTo(bonCommandeAutreResponsableDTO2);
        bonCommandeAutreResponsableDTO1.setId(null);
        assertThat(bonCommandeAutreResponsableDTO1).isNotEqualTo(bonCommandeAutreResponsableDTO2);
    }
}
