package com.gpm.finance.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtExterneAutreResponsableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OtExterneAutreResponsableDTO.class);
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO1 = new OtExterneAutreResponsableDTO();
        otExterneAutreResponsableDTO1.setId(1L);
        OtExterneAutreResponsableDTO otExterneAutreResponsableDTO2 = new OtExterneAutreResponsableDTO();
        assertThat(otExterneAutreResponsableDTO1).isNotEqualTo(otExterneAutreResponsableDTO2);
        otExterneAutreResponsableDTO2.setId(otExterneAutreResponsableDTO1.getId());
        assertThat(otExterneAutreResponsableDTO1).isEqualTo(otExterneAutreResponsableDTO2);
        otExterneAutreResponsableDTO2.setId(2L);
        assertThat(otExterneAutreResponsableDTO1).isNotEqualTo(otExterneAutreResponsableDTO2);
        otExterneAutreResponsableDTO1.setId(null);
        assertThat(otExterneAutreResponsableDTO1).isNotEqualTo(otExterneAutreResponsableDTO2);
    }
}
