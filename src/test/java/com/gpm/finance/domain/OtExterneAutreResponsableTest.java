package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtExterneAutreResponsableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtExterneAutreResponsable.class);
        OtExterneAutreResponsable otExterneAutreResponsable1 = new OtExterneAutreResponsable();
        otExterneAutreResponsable1.setId(1L);
        OtExterneAutreResponsable otExterneAutreResponsable2 = new OtExterneAutreResponsable();
        otExterneAutreResponsable2.setId(otExterneAutreResponsable1.getId());
        assertThat(otExterneAutreResponsable1).isEqualTo(otExterneAutreResponsable2);
        otExterneAutreResponsable2.setId(2L);
        assertThat(otExterneAutreResponsable1).isNotEqualTo(otExterneAutreResponsable2);
        otExterneAutreResponsable1.setId(null);
        assertThat(otExterneAutreResponsable1).isNotEqualTo(otExterneAutreResponsable2);
    }
}
