package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtExterneDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtExterneDTO.class);
        OtExterneDTO otExterneDTO1 = new OtExterneDTO();
        otExterneDTO1.setId(1L);
        OtExterneDTO otExterneDTO2 = new OtExterneDTO();
        assertThat(otExterneDTO1).isNotEqualTo(otExterneDTO2);
        otExterneDTO2.setId(otExterneDTO1.getId());
        assertThat(otExterneDTO1).isEqualTo(otExterneDTO2);
        otExterneDTO2.setId(2L);
        assertThat(otExterneDTO1).isNotEqualTo(otExterneDTO2);
        otExterneDTO1.setId(null);
        assertThat(otExterneDTO1).isNotEqualTo(otExterneDTO2);
    }
}
