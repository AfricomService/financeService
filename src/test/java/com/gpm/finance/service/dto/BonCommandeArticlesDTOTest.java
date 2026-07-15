package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeArticlesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommandeArticlesDTO.class);
        BonCommandeArticlesDTO bonCommandeArticlesDTO1 = new BonCommandeArticlesDTO();
        bonCommandeArticlesDTO1.setId(1L);
        BonCommandeArticlesDTO bonCommandeArticlesDTO2 = new BonCommandeArticlesDTO();
        assertThat(bonCommandeArticlesDTO1).isNotEqualTo(bonCommandeArticlesDTO2);
        bonCommandeArticlesDTO2.setId(bonCommandeArticlesDTO1.getId());
        assertThat(bonCommandeArticlesDTO1).isEqualTo(bonCommandeArticlesDTO2);
        bonCommandeArticlesDTO2.setId(2L);
        assertThat(bonCommandeArticlesDTO1).isNotEqualTo(bonCommandeArticlesDTO2);
        bonCommandeArticlesDTO1.setId(null);
        assertThat(bonCommandeArticlesDTO1).isNotEqualTo(bonCommandeArticlesDTO2);
    }
}
