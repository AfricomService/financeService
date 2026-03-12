package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleDemandeAchatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleDemandeAchat.class);
        ArticleDemandeAchat articleDemandeAchat1 = new ArticleDemandeAchat();
        articleDemandeAchat1.setId(1L);
        ArticleDemandeAchat articleDemandeAchat2 = new ArticleDemandeAchat();
        articleDemandeAchat2.setId(articleDemandeAchat1.getId());
        assertThat(articleDemandeAchat1).isEqualTo(articleDemandeAchat2);
        articleDemandeAchat2.setId(2L);
        assertThat(articleDemandeAchat1).isNotEqualTo(articleDemandeAchat2);
        articleDemandeAchat1.setId(null);
        assertThat(articleDemandeAchat1).isNotEqualTo(articleDemandeAchat2);
    }
}
