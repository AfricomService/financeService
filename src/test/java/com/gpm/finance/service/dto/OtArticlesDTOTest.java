package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtArticlesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtArticlesDTO.class);
        OtArticlesDTO otArticlesDTO1 = new OtArticlesDTO();
        otArticlesDTO1.setId(1L);
        OtArticlesDTO otArticlesDTO2 = new OtArticlesDTO();
        assertThat(otArticlesDTO1).isNotEqualTo(otArticlesDTO2);
        otArticlesDTO2.setId(otArticlesDTO1.getId());
        assertThat(otArticlesDTO1).isEqualTo(otArticlesDTO2);
        otArticlesDTO2.setId(2L);
        assertThat(otArticlesDTO1).isNotEqualTo(otArticlesDTO2);
        otArticlesDTO1.setId(null);
        assertThat(otArticlesDTO1).isNotEqualTo(otArticlesDTO2);
    }
}
