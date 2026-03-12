package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtExterneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtExterne.class);
        OtExterne otExterne1 = new OtExterne();
        otExterne1.setId(1L);
        OtExterne otExterne2 = new OtExterne();
        otExterne2.setId(otExterne1.getId());
        assertThat(otExterne1).isEqualTo(otExterne2);
        otExterne2.setId(2L);
        assertThat(otExterne1).isNotEqualTo(otExterne2);
        otExterne1.setId(null);
        assertThat(otExterne1).isNotEqualTo(otExterne2);
    }
}
