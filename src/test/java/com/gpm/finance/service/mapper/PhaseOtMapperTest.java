package com.gpm.finance.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhaseOtMapperTest {

    private PhaseOtMapper phaseOtMapper;

    @BeforeEach
    public void setUp() {
        phaseOtMapper = new PhaseOtMapperImpl();
    }
}
