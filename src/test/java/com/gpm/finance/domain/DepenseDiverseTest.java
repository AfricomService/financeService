package com.gpm.finance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpm.finance.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepenseDiverseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepenseDiverse.class);
        DepenseDiverse depenseDiverse1 = new DepenseDiverse();
        depenseDiverse1.setId(1L);
        DepenseDiverse depenseDiverse2 = new DepenseDiverse();
        depenseDiverse2.setId(depenseDiverse1.getId());
        assertThat(depenseDiverse1).isEqualTo(depenseDiverse2);
        depenseDiverse2.setId(2L);
        assertThat(depenseDiverse1).isNotEqualTo(depenseDiverse2);
        depenseDiverse1.setId(null);
        assertThat(depenseDiverse1).isNotEqualTo(depenseDiverse2);
    }
}
