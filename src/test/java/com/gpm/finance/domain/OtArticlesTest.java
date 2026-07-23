package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtArticlesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtArticles.class);
        OtArticles otArticles1 = new OtArticles();
        otArticles1.setId(1L);
        OtArticles otArticles2 = new OtArticles();
        otArticles2.setId(otArticles1.getId());
        assertThat(otArticles1).isEqualTo(otArticles2);
        otArticles2.setId(2L);
        assertThat(otArticles1).isNotEqualTo(otArticles2);
        otArticles1.setId(null);
        assertThat(otArticles1).isNotEqualTo(otArticles2);
    }
}
