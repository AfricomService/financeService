package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BonCommandeArticlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonCommandeArticles.class);
        BonCommandeArticles bonCommandeArticles1 = new BonCommandeArticles();
        bonCommandeArticles1.setId(1L);
        BonCommandeArticles bonCommandeArticles2 = new BonCommandeArticles();
        bonCommandeArticles2.setId(bonCommandeArticles1.getId());
        assertThat(bonCommandeArticles1).isEqualTo(bonCommandeArticles2);
        bonCommandeArticles2.setId(2L);
        assertThat(bonCommandeArticles1).isNotEqualTo(bonCommandeArticles2);
        bonCommandeArticles1.setId(null);
        assertThat(bonCommandeArticles1).isNotEqualTo(bonCommandeArticles2);
    }
}
