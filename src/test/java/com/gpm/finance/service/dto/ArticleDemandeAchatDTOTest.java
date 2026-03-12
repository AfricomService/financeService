package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArticleDemandeAchatDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleDemandeAchatDTO.class);
        ArticleDemandeAchatDTO articleDemandeAchatDTO1 = new ArticleDemandeAchatDTO();
        articleDemandeAchatDTO1.setId(1L);
        ArticleDemandeAchatDTO articleDemandeAchatDTO2 = new ArticleDemandeAchatDTO();
        assertThat(articleDemandeAchatDTO1).isNotEqualTo(articleDemandeAchatDTO2);
        articleDemandeAchatDTO2.setId(articleDemandeAchatDTO1.getId());
        assertThat(articleDemandeAchatDTO1).isEqualTo(articleDemandeAchatDTO2);
        articleDemandeAchatDTO2.setId(2L);
        assertThat(articleDemandeAchatDTO1).isNotEqualTo(articleDemandeAchatDTO2);
        articleDemandeAchatDTO1.setId(null);
        assertThat(articleDemandeAchatDTO1).isNotEqualTo(articleDemandeAchatDTO2);
    }
}
